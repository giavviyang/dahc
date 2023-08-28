package com.fudian.dahc.service.fileInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.business.DahcDataTemplateMapper;
import com.fudian.dahc.mapper.fileInfo.UploadFileInfoMapper;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.dto.DahcFilePhotoDto;
import com.fudian.dahc.pojo.dto.DahcMetadataVo;
import com.fudian.dahc.pojo.dto.DataTemplateDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import com.fudian.dahc.pojo.entity.fileInfo.QueryFileInfo;
import com.fudian.dahc.pojo.entity.fileInfo.UploadFileInfo;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.fudian.dahc.service.fileInfo.ExprotService;
import com.fudian.dahc.service.fileInfo.FileInfoService;
import com.fudian.dahc.service.project.ProjectTableService;
import com.fudian.dahc.util.common.*;
import com.fudian.dahc.util.fileUpload.SnowflakeIdWorker;
import com.fudian.dahc.util.mybatispuls.CurrencyResultHandler;
import com.github.yulichang.method.SqlMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.ResultContext;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.github.yulichang.method.SqlMethod.collect;

/**
 * 文件处理类
 *
 * @author 洋葱骑士
 */
@Service
@Slf4j
public class ExprotServiceImpl implements ExprotService {
    private static Map<String, Future<?>> futures = new HashMap<>();
    @Autowired
    private ArchiveTypeService archiveTypeService;
    @Autowired
    private DahcDataTemplateMapper dahcDataTemplateMapper;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private DahcFilePhotoService dahcFilePhotoService;

    @Autowired
    private ProjectTableService projectTableService;

    @Resource
    UploadFileInfoMapper tFileInfoMapper;

    private String dateFormat(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date).replaceAll("[[\\s-:punct:]]", "");
    }

    @Override
    @Transactional
    public CommonResult<Object> exportExcelAccordingToFileType(DataTemplateDto dto) {
        List<DahcDataTemplate> dahcDataTemplateListBigData = null;
        try {
            List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeAndMetadataVos = archiveTypeService.selectListAndMetadataById(1, dto.getArchiveTypeId());
            DahcArchiveTypeAndMetadataVo dahcArchiveTypeAndMetadataVo = dahcArchiveTypeAndMetadataVos.get(0);
            //获取元数据
            List<DahcMetadataVo> dahcMetadataList = dahcArchiveTypeAndMetadataVo.getDahcMetadataList();
            //获取元数据序号和名字
            Map<Long, String> metadataNameList = dahcMetadataList.stream().collect(Collectors.toMap(DahcMetadataVo::getAttrOrder, DahcMetadata::getMetadataName));
            //创建excel 设置格式和表头
            String fileName = "案卷数据.xlsx";
            String tableLevel = "";
            /*案卷数据*/
            DahcProjectTable projectTable = projectTableService.getOne(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getId, dto.getProjectId()));
            String projectName = projectTable.getProjectName() + IdWorker.getIdStr();
            /*创建文件夹*/
            writeToTheFolder(FileUtil.getPath() + "\\" + projectName);
            tableLevel = dahcArchiveTypeAndMetadataVo.getTableLevel1En();
            ExcelUtil.exportToExcel(metadataNameList, fileName, 1, projectName);
            //获取需要查询的字段
            String collect = dahcMetadataList.stream().map(r -> "attr" + r.getAttrOrder()).collect(Collectors.joining(","));
            CurrencyUtil.dynamicSelectionTableByTableName(tableLevel);
            /*写入案卷*/
            dahcDataTemplateListBigData = getDahcDataTemplateListBigData(dto, collect, null, projectName);
            /*写入文件*/
            exportCaseData(dto, projectName);
            /*压缩文件夹*/
            String zipPath = FileUtil.getPath() + "\\" + projectTable.getProjectName() + "条目信息" + dateFormat(new Date()) + ".zip";
            FileCompressUtils.toZip(zipPath, FileUtil.getPath() + projectName, true);
            FileCompressUtils.deleteDir(new File(FileUtil.getPath() + projectName));
            /*添加数据库数据，用于大文件下载*/
            /*向数据库添加一条数据*/
            String fileId = SnowflakeIdWorker.getUUID() + SnowflakeIdWorker.getUUID();
            UploadFileInfo uploadFileInfo = new UploadFileInfo();
            uploadFileInfo.setId(fileId);
            uploadFileInfo.setIdentifier(fileId);
            uploadFileInfo.setFilename(projectTable.getProjectName() + "条目信息" + dateFormat(new Date()) + ".zip");
            uploadFileInfo.setType("zip");
            uploadFileInfo.setLocation(zipPath);
            uploadFileInfo.setDelFlag("0");
            uploadFileInfo.setTotalSize(getFileSize3(new File(zipPath)));
            uploadFileInfo.setRefProjectId(dto.getProjectId());
            tFileInfoMapper.insertSelective(uploadFileInfo);
            return CommonResult.success(fileId);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("导出失败");
        }
        //return CommonResult.success(dahcDataTemplateListBigData);
    }

    /*导出案件数据*/
    private Boolean exportCaseData(DataTemplateDto dto, String projectName) {
        /*案件元数据*/
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeAndMetadataVos = archiveTypeService.selectListAndMetadataById(2, dto.getArchiveTypeId());
        DahcArchiveTypeAndMetadataVo dahcArchiveTypeAndMetadataVo = dahcArchiveTypeAndMetadataVos.get(0);
        //获取元数据
        List<DahcMetadataVo> dahcMetadataList = dahcArchiveTypeAndMetadataVo.getDahcMetadataList();
        //获取元数据序号和名字
        Map<Long, String> metadataNameList = dahcMetadataList.stream().collect(Collectors.toMap(DahcMetadataVo::getAttrOrder, DahcMetadata::getMetadataName));
        /*案件数据*/
        String tableLevel = dahcArchiveTypeAndMetadataVo.getTableLevel2En();
        /*查询所有数据案件数据*/
        CurrencyUtil.dynamicSelectionTableByTableName(tableLevel);
        //获取需要查询的字段
        String collect = dahcMetadataList.stream().map(r -> "attr" + r.getAttrOrder()).collect(Collectors.joining(","));
        String attr = archiveTypeService.queryProfileDefaultFieldBindingAttr(String.valueOf(dto.getArchiveTypeId()), tableLevel, "档号");
        /*项目所有的案件数据*/
        List<Map<String, String>> list = dahcDataTemplateMapper.queryDataThatHasNotBeenCheckedAll(tableLevel, "attr" + attr, dto.getProjectId(), dto.getFileIds());
        /*分组*/
        //ArrayList<Map<String, String>> caseList = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(a -> a.get("pid")))), ArrayList::new));
        getDahcDataTemplateListBigDataCase(dto, collect, null, list, metadataNameList, attr, projectName);
        return true;
    }

    /*案件处理数据*/
    public List<DahcDataTemplate> getDahcDataTemplateListBigDataCase(DataTemplateDto dto, String sqlSelect,
                                                                     List<Long> subDeptIdList,
                                                                     List<Map<String, String>> list,
                                                                     Map<Long, String> metadataNameList, String attr, String projectName) {
        List<DahcDataTemplate> dahcDataTemplate = new ArrayList<>();
        QueryWrapper<DahcDataTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(sqlSelect);
        if (dto.getFileIds().size() > 0) {
            queryWrapper.in("pid", dto.getFileIds());
        }
        queryWrapper.eq("project_id", dto.getProjectId()).groupBy("pid");
        //流式条件查询
        CurrencyResultHandler<Map<String, String>> currencyResultHandler = new CurrencyResultHandler<Map<String, String>>() {
            @Override
            public void handle() {
                try {
                    /*添加到本地*/
                    ExcelUtil.addContentToExcel2(gxids, metadataNameList, list, attr, projectName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.handle();
            }

            @Override
            public void end() {
                handle();
                //String path = FileUtil.getPath();
                //String fileName = "导出条目信息.xlsx";
                //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ////将工作簿写到输出流中
                //XSSFWorkbook workbook = new XSSFWorkbook();
                //try {
                //    workbook = new XSSFWorkbook(path + fileName);
                //    workbook.write(bos);
                //    new DownloadUtil().download(bos, response, "条目导出表.xlsx");
                //    bos.close();
                //    workbook.close();
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
            }
        };
        dahcDataTemplateMapper.getDahcDataTemplateListBigData(queryWrapper, currencyResultHandler);
        currencyResultHandler.end();
        return dahcDataTemplate;
    }

    /*案卷处理数据*/
    public List<DahcDataTemplate> getDahcDataTemplateListBigData(DataTemplateDto dto, String sqlSelect, List<Long> subDeptIdList, String projectName) {
        List<DahcDataTemplate> dahcDataTemplate = new ArrayList<>();
        String fileName = "案卷数据.xlsx";
        QueryWrapper<DahcDataTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(sqlSelect);
        if (dto.getFileIds().size() > 0) {
            queryWrapper.in("id", dto.getFileIds());
        }
        queryWrapper.eq("project_id", dto.getProjectId());
        //流式条件查询
        CurrencyResultHandler<Map<String, String>> currencyResultHandler = new CurrencyResultHandler<Map<String, String>>() {
            @Override
            public void handle() {
                try {
                    ExcelUtil.addContentToExcel(gxids, fileName, projectName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.handle();
            }

            @Override
            public void end() {
                handle();
                //String path = FileUtil.getPath();
                //String fileName = "案卷数据.xlsx";
                //ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ////将工作簿写到输出流中
                //XSSFWorkbook workbook = new XSSFWorkbook();
                //try {
                //    workbook = new XSSFWorkbook(path + "\\" + projectName + "\\" + fileName);
                //    workbook.write(bos);
                //    new DownloadUtil().download(bos, response, "案卷数据.xlsx");
                //    bos.close();
                //    workbook.close();
                //} catch (IOException e) {
                //    e.printStackTrace();
                //}
            }
        };
        dahcDataTemplateMapper.getDahcDataTemplateListBigData(queryWrapper, currencyResultHandler);
        currencyResultHandler.end();
        return dahcDataTemplate;
    }

    @Override
    public CommonResult<Object> getzipInformation(String id) {
        if (futures.containsKey(id)) {
            Future<?> future = futures.get(id);
            if (future.isDone()) {
                String s = "";
                try {
                    s = (String) future.get();
                } catch (InterruptedException | ExecutionException e) {
                    //e.printStackTrace();
                    //任务执行完毕,定时停止,前台提示任务失败 code 50000 错误信息 msg
                    return CommonResult.success(e.getMessage(), "失败");
                } finally {
                    futures.remove(id);
                }
                if (s.startsWith("压缩失败,请重试或联系管理员") || s.startsWith("下载异常,请重试或联系管理员")) {
                    return CommonResult.success(s, "失败");
                }
                //任务执行完毕,定时停止,前台提示 code 20000 msg
                return CommonResult.success(s, "成功");
            } else {
                //任务未执行完毕,继续执行 code 10001 不提示信息
                return CommonResult.success(id, "未完成");
            }
        }
        //任务执行完毕,定时停止,前台提示任务失败 code 50000 错误信息 msg
        return CommonResult.success("下载失败", "失败");
    }

    @Override
    @Transactional
    public CommonResult<?> getTheFileInformationAndCompressIt(DahcFilePhotoDto dto) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        /*案件表名*/
        String tableLevel2En = archiveTypeService.getOne(Wrappers.<DahcArchiveType>lambdaQuery().eq(DahcArchiveType::getId, dto.getArchivesId())).getTableLevel2En();
        List<Map<String, String>> maps = dahcDataTemplateMapper.queryDataThatHasNotBeenCheckedAll(tableLevel2En, null, dto.getProjectId(), dto.getFileIds());
        List<String> fileIds = maps.stream().map(m -> String.valueOf(m.get("id"))).collect(Collectors.toList());
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            String zipPath = "失败";
            String fileId = SnowflakeIdWorker.getUUID() + SnowflakeIdWorker.getUUID();
            try {
                LambdaQueryWrapper<DahcFilePhoto> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(DahcFilePhoto::getProjectId, dto.getProjectId())
                        .eq(DahcFilePhoto::getFileDelete, 0);
                if (fileIds.size() > 0) {
                    wrapper.in(DahcFilePhoto::getPid, fileIds);
                }
                wrapper.orderByAsc(DahcFilePhoto::getPid, DahcFilePhoto::getPageNum);

                /*获取当前工序的所有图片数据*/
                List<DahcFilePhoto> filePhotos = dahcFilePhotoService.list(wrapper);
                /*图片数据加入到被本地*/
                DahcProjectTable projectTable = projectTableService.getOne(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getId, dto.getProjectId()));
                String path = FileUtil.getPath();
                //2.  创建文件夹对象     创建文件对象
                String projectPath = path + "\\" + projectTable.getProjectName() + dateFormat(new Date());
                zipPath = compressTheFile(projectTable, filePhotos, projectPath);
                /*向数据库添加一条数据*/
                UploadFileInfo uploadFileInfo = new UploadFileInfo();
                uploadFileInfo.setId(fileId);
                uploadFileInfo.setIdentifier(fileId);
                uploadFileInfo.setFilename(projectTable.getProjectName() + "电子文件" + dateFormat(new Date()) + ".zip");
                uploadFileInfo.setType("zip");
                uploadFileInfo.setLocation(zipPath);
                uploadFileInfo.setDelFlag("0");
                uploadFileInfo.setTotalSize(getFileSize3(new File(zipPath)));
                uploadFileInfo.setRefProjectId(dto.getProjectId());
                int i = tFileInfoMapper.insertSelective(uploadFileInfo);
            } catch (Exception e) {
                return "压缩失败,请重试或联系管理员";
            }
            return "压缩成功,开始下载文件-" + fileId;
        }, threadPoolTaskExecutor);
        voidCompletableFuture.thenAccept((result) -> {
            futures.put(date2, voidCompletableFuture);
        });
        voidCompletableFuture.exceptionally((e) -> {
            futures.put(date2, voidCompletableFuture);
            e.printStackTrace();
            return "下载异常,请重试或联系管理员";
        });
        return CommonResult.success("正在后台压缩数据,请稍后", date2);
    }

    /*压缩文件*/
    public String compressTheFile(DahcProjectTable projectTable, List<DahcFilePhoto> filePhotos, String projectPath) {
        String path = FileUtil.getPath();
        //2.  创建文件夹对象     创建文件对象
        //String projectPath = path + "\\" + projectTable.getProjectName();
        writeToTheFolder(projectPath);
        for (DahcFilePhoto filePhoto : filePhotos) {
            String dossierLevelName = filePhoto.getKeyName().substring(0, filePhoto.getKeyName().lastIndexOf('-'));
            /*创建案卷文件夹*/
            String dossierLevelPath = projectPath + "\\" + dossierLevelName;
            writeToTheFolder(dossierLevelPath);
            /*创建案件文件夹*/
            String casePath = dossierLevelPath + "\\" + filePhoto.getKeyName();
            writeToTheFolder(casePath);
            try {
                byte[] imageBytes = FileUtils.readFileToByteArray(new File(filePhoto.getFilePath()));
                /*移动文件*/
                FileUtils.writeByteArrayToFile(new File(casePath + "\\" + filePhoto.getFileName()), imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String zipPath = path + projectTable.getProjectName() + "电子文件" + dateFormat(new Date()) + ".zip";
        FileCompressUtils.toZip(zipPath, projectPath, true);
        FileCompressUtils.deleteDir(new File(projectPath));
        return zipPath;
    }

    private void writeToTheFolder(String filePath) {
        File file = new File(filePath);
        //如果文件夹不存在  就创建一个空的文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 根据java.nio.*的流获取文件大小
     *
     * @param file
     */
    private long getFileSize3(File file) {
        FileChannel fc = null;
        try {
            if (file.exists() && file.isFile()) {
                String fileName = file.getName();
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                long size = fc.size();
                System.out.println("文件" + fileName + "的大小是：" + fc.size() + "\n");
                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

}
