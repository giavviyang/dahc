package com.fudian.dahc.service.business.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.business.DahcDataTemplateMapper;
import com.fudian.dahc.pojo.dto.*;
import com.fudian.dahc.pojo.entity.business.*;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.query.DataTemplateQuery;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.business.ModelService;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.fudian.dahc.util.common.*;
import com.fudian.dahc.util.mybatispuls.CurrencyResultHandler;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 2023/2/7
 */
@Service
@Slf4j
public class DataTemplateServiceImpl extends MyBaseServiceImpl<DahcDataTemplate> implements DataTemplateService {
    private static Map<String, Future<?>> futures = new HashMap<>();

    @Autowired
    private DahcDataTemplateMapper dahcDataTemplateMapper;

    @Autowired
    private ArchiveTypeService archiveTypeService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private DahcFilePhotoService dahcFilePhotoService;
    @Autowired
    private DahcFilePhotoServiceImpl dahcFilePhotoServiceImpl;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private ProjectProcedureService projectProcedureService;

    @Autowired
    private IDahcRecordProcedureFilesService iDahcRecordProcedureFilesService;

    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;
    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;



    @Override
    public CommonGridResult selectPage(DataTemplateQuery dataTemplateQuery) {
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeList = archiveTypeService.selectListAndMetadataById(dataTemplateQuery.getLevel(), dataTemplateQuery.getArchiveTypeId());
        AssertUtil.isTrueServiceInvoke(dahcArchiveTypeList.size() > 0, CommonStatus.ERROR, "未能查询到档案类型");
        List<DahcMetadataVo> dahcMetadataList = dahcArchiveTypeList.get(0).getDahcMetadataList();
        Page<Map<String, Object>> page = PageHelper.startPage(dataTemplateQuery.getPageNum(), dataTemplateQuery.getPageSize());
        CurrencyUtil.dynamicSelectionTableByTableName(dahcMetadataList.get(0).getArchiveLevelName());
        LambdaQueryWrapper<DahcDataTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DahcDataTemplate::getStatus, "1");
        wrapper.orderByAsc(DahcDataTemplate::getId);
        List<Map<String, Object>> list = dahcDataTemplateMapper.selectMaps(wrapper);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @Override
    public List<DahcDataTemplate> queryTheAmountOfFileDataBasedOnTheProject(String projectId, String archiveLevelName) {
        return dahcDataTemplateMapper.queryTheAmountOfFileDataBasedOnTheProject(projectId, archiveLevelName);
    }

    @Override
    public CommonResult<List<Map<String, Object>>> readMetadata(DataTemplateDto dto) {
        List<Map<String, Object>> maps = dahcDataTemplateMapper.readMetadataLimit(dto);
        return CommonResult.success(maps);
    }

    @Override
    @Transactional
    public CommonResult increaseCases(DahcDataTemplateDto dahcDataTemplate) {
        dahcFilePhotoServiceImpl.associateTheCurrentOperation(SecurityUtils.getUserId(),dahcDataTemplate.getProcedureId());
        dynamicSelectionTable(dahcDataTemplate.getArchiveTypeId(), dahcDataTemplate.getLevel());
        int i = this.add(dahcDataTemplate);
        if (i > 0) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dahcDataTemplate.getProjectId(), dahcDataTemplate.getProcedureId(),dahcDataTemplate.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dahcDataTemplate.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                    dahcDataTemplate.getCaseFileNumber(), dahcDataTemplate.getCaseNumber(), dahcDataTemplate.getPid(), null, 0,JSON.toJSONString(dahcDataTemplate),null,null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("添加成功");
        } else {
            return CommonResult.error("添加失败");
        }
    }

    @Override
    @Transactional
    public CommonResult updateModel(DahcDataTemplateDto dahcDataTemplate) {
        dahcFilePhotoServiceImpl.associateTheCurrentOperation(SecurityUtils.getUserId(),dahcDataTemplate.getProcedureId());
        dynamicSelectionTable(dahcDataTemplate.getArchiveTypeId(), dahcDataTemplate.getLevel());
        int update = this.update(dahcDataTemplate);
        if (update > 0) {
            List<DahcDataTemplate> list = new ArrayList<>();
            DahcArchiveType typeServiceOne = archiveTypeService.getOne(String.valueOf(dahcDataTemplate.getArchiveTypeId()));
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dahcDataTemplate.getProjectId(), dahcDataTemplate.getProcedureId(),dahcDataTemplate.isCheckStatus());
            list = dahcDataTemplateMapper.queryTheAmountOfFileDataBasedOnId(dahcDataTemplate.getId(), typeServiceOne.getTableLevel2En());
            if (list.size() == 0) {
                list = dahcDataTemplateMapper.queryTheAmountOfFileDataBasedOnId(dahcDataTemplate.getId(), typeServiceOne.getTableLevel1En());
            }
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dahcDataTemplate.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                    dahcDataTemplate.getCaseFileNumber(),dahcDataTemplate.getCaseNumber(), dahcDataTemplate.getPid(), dahcDataTemplate.getFileId(), 0,
                    JSON.toJSONString(list),
                    JSON.toJSONString(dahcDataTemplate),null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    @Override
    @Transactional
    public CommonResult deleteByIdModel(DahcDataTemplateDto dahcDataTemplate) {
        dahcFilePhotoServiceImpl.associateTheCurrentOperation(SecurityUtils.getUserId(),dahcDataTemplate.getProcedureId());
        dynamicSelectionTable(dahcDataTemplate.getArchiveTypeId(), dahcDataTemplate.getLevel());
        int i = this.deleteById(Long.valueOf(dahcDataTemplate.getId()));
        if (i > 0) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dahcDataTemplate.getProjectId(), dahcDataTemplate.getProcedureId(),dahcDataTemplate.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dahcDataTemplate.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                    dahcDataTemplate.getCaseFileNumber(),dahcDataTemplate.getCaseNumber(), dahcDataTemplate.getPid(), dahcDataTemplate.getId(), 0,
                    JSON.toJSONString(dahcDataTemplate),
                    null,null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("删除成功");
        } else {
            return CommonResult.error("删除失败");
        }
    }

    @Override
    public CommonResult<List<DahcArchiveTypeAndMetadataVo>> getHeader(DataTemplateQuery dataTemplateQuery) {
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeList = archiveTypeService.selectListAndMetadataById(dataTemplateQuery.getLevel(), dataTemplateQuery.getArchiveTypeId());
        return CommonResult.success(dahcArchiveTypeList);
    }

    @Override
    public CommonGridResult getBody(DataTemplateQuery dataTemplateQuery) {
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeList = archiveTypeService.selectListAndMetadataById(dataTemplateQuery.getLevel(), dataTemplateQuery.getArchiveTypeId());

        if (StringUtils.isEmpty(dahcArchiveTypeList)) {
            return null;
        }
        List<DahcMetadataVo> dahcMetadataList = dahcArchiveTypeList.get(0).getDahcMetadataList();
        CurrencyUtil.dynamicSelectionTableByTableName(dahcMetadataList.get(0).getArchiveLevelName());
        LambdaQueryWrapper<DahcDataTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DahcDataTemplate::getStatus, "1");
        wrapper.orderByAsc(DahcDataTemplate::getId);
        PageHelper.startPage(dataTemplateQuery.getPageNum(), dataTemplateQuery.getPageSize());
        List<Map<String, Object>> maps = dahcDataTemplateMapper.selectMaps(wrapper);
        Page page = (Page) maps;
        for (DahcMetadataVo dto : dahcMetadataList) {
            Long order = dto.getAttrOrder();
            for (Map<String, Object> map : maps) {
                //删除元数据没有赋值的字段
                if (order.intValue() + 1 == dahcMetadataList.size()) {
                    for (int i = order.intValue() + 1; i < 39; i++) {
                        map.remove("attr" + i);
                    }
                }
            }
        }
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), maps);
    }

    @Override
    @Transactional
    public CommonResult<?> importDataVolume(String id, String projectId, MultipartFile[] multipartFile) {
        //查询模板和其映射关系
        List<DahcModel> dahcModels = modelService.selectMapperByModel(id, null);
        AssertUtil.isTrueServiceInvoke(!dahcModels.isEmpty(), CommonStatus.ERROR, "未找到模板");
        AssertUtil.isTrueServiceInvoke(projectId != null && !"".equals(projectId), CommonStatus.ERROR, "非法的项目id");
        DahcModel dahcModel = dahcModels.get(0);
        List<DahcBusinessMapper> dahcBusinessMapperList = dahcModel.getDahcBusinessMapperList();
        AssertUtil.isTrueServiceInvoke(!dahcBusinessMapperList.isEmpty(), CommonStatus.ERROR, "未找到模板数据");
        String dh = archiveTypeService.queryProfileDefaultFieldBindingAttr(dahcModel.getArchiveTypeId(), dahcModel.getArchiveTableName(), "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未能找到档号");
        String username = SecurityUtils.getUsername();
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        AtomicInteger i = new AtomicInteger();
        String batchNo = username + "-" + date2;
        Long userId = SecurityUtils.getUserId();
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            /*设置索引*/
            verificationGradeIndex(dh, dahcModel.getArchiveTableName());
            for (MultipartFile value : multipartFile) {
                try {
//                    File file = FileUtil.checkAndStorageFile(value);
                    File file = new File(filePath);
                    List<DahcDataTemplate> dahcDataTemplateList = ExcelUtil.readExcel(file, dahcBusinessMapperList);
                    if (dahcDataTemplateList.size() > 0) {
                        i.addAndGet(dahcDataTemplateList.size());
                        DataTemplateDto dataTemplateDto = new DataTemplateDto()
                                .setDahcDataTemplateList(dahcDataTemplateList)
                                .setProjectId(projectId)
                                .setUserId(userId)
                                .setTableName(dahcModel.getArchiveTableName())
                                .setAttr0("attr" + dh)
                                .setBatchNo(batchNo)
                                .setDesc("备注");
                        dahcDataTemplateMapper.bulkAdditions(dataTemplateDto);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (i.get() > 0) {
                //备份数据库
                int i1 = dahcDataTemplateMapper.insertOriginalDataStorage(dahcModel.getArchiveTableName(), dahcModel.getArchiveTableName() + "_backup");
                //修改状态,开启数据任务流程
                int i2 = 0;
                if (i1 > 0) {
                    i2 = dahcDataTemplateMapper.modifyStatusValue(dahcModel.getArchiveTableName(), 1, 0);
                }
                //根据项目查询第一个工序的id
                DahcProjectProcedure dahcProjectProcedure = projectProcedureService.queryTheFirstOperation(projectId);
                if (dahcProjectProcedure != null) {
                    String procedureId = dahcProjectProcedure.getId();
                    //查询该项目中所有任务未开始状态的数据
                    List<Map<String, String>> data = queryDataThatHasNotBeenChecked(dahcModel.getArchiveTableName(), "attr" + dh, projectId);
                    if (data.size() > 0) {
                        List<DahcRecordProcedureFiles> filesList = new ArrayList<>();
                        List<String> ids = new ArrayList<>();
                        for (Map<String, String> a : data) {
                            String s = String.valueOf(a.get("id"));
                            ids.add(s);
                            Object filesName = a.get("attr" + dh);
                            //isProcedureInspect 为0 表示开启核查任务 还未被领取
                            filesList.add(new DahcRecordProcedureFiles(
                                    s, String.valueOf(filesName), procedureId, projectId, "0",new Date()));
                        }
                        if (ids.size() > 0 && filesList.size() > 0) {
                            //把数据加入到任务表中
                            iDahcRecordProcedureFilesService.bulkInsert(filesList);
                            modifyStatusValueInList(dahcModel.getArchiveTableName(), 2, 1, ids);
                            return "任务完成,上传成功" + i + "条数据,重复" + (i.get() - i2) + "条数据," + "备份成功" + i2 + "条数据,开始核查任务" + filesList.size() + "条数据";
                        }
                    }

                }
                return "任务完成,上传成功" + i + "条数据,重复" + (i.get() - i2) + "条数据," + "备份成功" + i2 + "条数据";
            }
            return "任务失败,未能找到要导入的数据";
        }, threadPoolTaskExecutor);
        voidCompletableFuture.thenAccept((result) -> {
            futures.put(date2, voidCompletableFuture);
        });
        voidCompletableFuture.exceptionally((e) -> {
            futures.put(date2, voidCompletableFuture);
            e.printStackTrace();
            return "任务异常,请重试或联系管理员";
        });
        return CommonResult.success("正在后台解析上传,请稍后", date2);
    }

    @Override
    @Transactional
    public CommonResult<?> importDataPiece(String id, String projectId, List<String> batchNo, MultipartFile[] multipartFile, Map<String, Integer> objectObjectHashMap) {
        List<DahcModel> dahcModels = modelService.selectMapperByModel(id, null);
        AssertUtil.isTrueServiceInvoke(!dahcModels.isEmpty(), CommonStatus.ERROR, "未找到模板");
        DahcModel dahcModel = dahcModels.get(0);
        List<DahcBusinessMapper> dahcBusinessMapperList = dahcModel.getDahcBusinessMapperList();
        AssertUtil.isTrueServiceInvoke(!dahcBusinessMapperList.isEmpty(), CommonStatus.ERROR, "未找到模板数据");
        String s = dahcModel.getArchiveTableName().replaceFirst("2", "1");
        String dh = archiveTypeService.queryProfileDefaultFieldBindingAttr(dahcModel.getArchiveTypeId(), s, "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未能找到档号");
        String attr = "attr" + dh;
        Map<String, String> fileInformation = new HashMap<>(10);
        //获取该批次所有案卷
        dahcDataTemplateMapper.mybatisStreamQueryBybatch(resultContext -> {
            Map<String, String> resultObject = resultContext.getResultObject();
            String key = String.valueOf(resultObject.get(attr));
            String value = String.valueOf(resultObject.get("id"));
            fileInformation.put(key, value);
        }, s, attr, projectId, batchNo);
        AssertUtil.isTrueServiceInvoke(fileInformation.size() > 0, CommonStatus.ERROR, "未能获取到此批次相关案卷");
        AtomicInteger i = new AtomicInteger();
        //异步
        Long userId = SecurityUtils.getUserId();
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            verificationGradeIndex(dh, dahcModel.getArchiveTableName());
            for (MultipartFile value : multipartFile) {
                //File file = FileUtil.copeFileAndStorageFile(value);
                File file = new File(filePath);
                AssertUtil.isTrueServiceInvoke(file != null, CommonStatus.ERROR, "文件未找到");
                List<DahcDataTemplate> dahcDataTemplateList1 = ExcelUtil.readExcelFile(file, dahcBusinessMapperList, fileInformation, objectObjectHashMap);
                Optional<List<DahcDataTemplate>> dahcDataTemplateList = Optional.ofNullable(dahcDataTemplateList1);
                if (!dahcDataTemplateList.get().isEmpty()) {
                    dahcDataTemplateList.ifPresent(dahcDataTemplateList2 -> {
                        DataTemplateDto dataTemplateDto = new DataTemplateDto()
                                .setDahcDataTemplateList(dahcDataTemplateList2)
                                .setTableName(dahcModel.getArchiveTableName())
                                .setUserId(userId)
                                .setProjectId(projectId)
                                .setAttr0("attr" + dh)
                                .setBatchNo(StringUtils.join(batchNo.toArray(), ','))
                                .setDesc("备注");
                        int i3 = dahcDataTemplateMapper.bulkAdditions(dataTemplateDto);
                        i.addAndGet(i3);
                    });
                }
            }
            if (i.get() > 0) {
                //备份数据库
                int i1 = dahcDataTemplateMapper.insertOriginalDataStorage(dahcModel.getArchiveTableName(), dahcModel.getArchiveTableName() + "_backup");
                //修改状态,开启数据任务流程
                int i2 = dahcDataTemplateMapper.modifyStatusValue(dahcModel.getArchiveTableName(), 1, 0);
                return "任务完成,上传成功" + i + "条数据,重复" + (i.get() - i1) + "条数据," + "备份成功" + i2 + "条数据";
            }
            return "任务失败,没有找到匹配的数据";
        }, threadPoolTaskExecutor);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        voidCompletableFuture.thenAccept((result) -> {
            futures.put(date2, voidCompletableFuture);
        });

        voidCompletableFuture.exceptionally((e) -> {
            futures.put(date2, voidCompletableFuture);
            e.printStackTrace();
            return "任务异常,请重试或联系管理员";
        });
        return CommonResult.success("正在后台解析上传,请稍后", date2);
    }

    @Override
    public CommonResult<Object> getInformation(String id) {
        if (futures.containsKey(id)) {
            Future<?> future = futures.get(id);
            if (future.isDone()) {
                String s = "";
                try {
                    s = (String) future.get();
                } catch (InterruptedException | ExecutionException e) {
                    //e.printStackTrace();
                    //任务执行完毕,定时停止,前台提示任务失败 code 50000 错误信息 msg
                    return CommonResult.success(e.getMessage(), "完成但是失败");
                } finally {
                    futures.remove(id);
                }
                if (s.startsWith("任务失败")) {
                    return CommonResult.success(s, "完成但是失败");
                }
                //任务执行完毕,定时停止,前台提示 code 20000 msg
                return CommonResult.success(s, "成功");
            } else {
                //任务未执行完毕,继续执行 code 10001 不提示信息
                return CommonResult.success(id, "未完成");
            }
        }
        //任务执行完毕,定时停止,前台提示任务失败 code 50000 错误信息 msg
        return CommonResult.success("未找到对应任务", "完成但是失败");
    }

    private List<DahcFilePhoto> photos = new ArrayList<>();
    private int photosNum = 0;
    private int photosNumRepeat = 0;
    private int photosNumNonConforming = 0;
    private int fileCount = 0;
    private int count = 0;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> readPicture(String archivesId, String filePath, String projectId) {
        DahcArchiveType byId = archiveTypeService.getById(archivesId);
        AssertUtil.isTrueServiceInvoke(byId != null, CommonStatus.ERROR, "未找到档案");
        String dh = archiveTypeService.queryProfileDefaultFieldBindingAttr(archivesId, byId.getTableLevel2En(), "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未找到档号");
        File root = new File(filePath);
        File[] files = root.listFiles();
        AssertUtil.isTrueServiceInvoke(files != null, CommonStatus.ERROR, "空文件夹");
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            photosNum = 0;
            count = 0;
            readPictureAndMatchSave(filePath, byId, projectId, dh);
            if (photos.size() > 0) {
                photosNum = photosNum + dahcFilePhotoService.bulkInsert(photos);
                photos.clear();
            }
            return "图片上传完成,解析到图片" + count + "张，上传成功" + photosNum + "张";
        }, threadPoolTaskExecutor);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        voidCompletableFuture.thenAccept((result) -> {
            futures.put(date2, voidCompletableFuture);
        });
        voidCompletableFuture.exceptionally((e) -> {
            futures.put(date2, voidCompletableFuture);
            e.printStackTrace();
            return String.valueOf(e);
        });
        return CommonResult.success("正在后台解析上传,请稍后", date2);
    }


    public void readPictureAndMatchSave(String filePath, DahcArchiveType dahcArchiveType, String projectId, String dh) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        //定义符合要求的后缀
        String[] suffixImg = {"jpg", "png", "JPG", "PNG", "tif", "jpeg", "JPEG"};
        //定义一个图片的初始量
        int pageNum = 0;
        AssertUtil.isTrueServiceInvoke(files != null, CommonStatus.ERROR, "空文件夹");
        for (File file : files) {
            count++;
            String name = file.getName();
            long size = file.length();
            String suffixName = name.substring(name.lastIndexOf(".") + 1);
            if (file.isDirectory()) {
                pageNum = 0;
                //递归调用
                readPictureAndMatchSave(file.getAbsolutePath(), dahcArchiveType, projectId, dh);
            } else if (Arrays.asList(suffixImg).contains(suffixName)) {
                pageNum++;
//                Linux下：”/”
//                Window下：”\\”
//                System.getProperty("file.separator")  java 中通用路径分隔符
                String fileName = name.substring(name.lastIndexOf(System.getProperty("file.separator")) + 1, name.lastIndexOf("."));
                log.info("文件名:{}", fileName);
                int i = fileName.lastIndexOf("-");
                if (i == -1) {
                    continue;
                }
                String dhName = fileName.substring(0, i);
                log.info("档号:{}", dhName);
                CurrencyUtil.dynamicSelectionTableByTableName(dahcArchiveType.getTableLevel2En());
                QueryWrapper<DahcDataTemplate> wrapper = new QueryWrapper<>();
                wrapper.select("id", "attr" + dh, "project_id");
                if (projectId != null) {
                    wrapper.eq("project_id", projectId);
                }
                wrapper.eq("attr" + dh, dhName);
                DahcDataTemplate dahcDataTemplate = dahcDataTemplateMapper.selectOne(wrapper);
                if (dahcDataTemplate != null) {
                    //读取文件内容
                    try {
                        BufferedImage image = ImageIO.read(file);
                        Double width = (double) image.getWidth();
                        Double height = (double) image.getHeight();
                        Double pixelSize = (double) image.getColorModel().getPixelSize();
                        //photosNum++;
                        photos.add(new DahcFilePhoto(String.valueOf(IdWorker.getId()),
                                dhName, dahcDataTemplate.getId(), projectId, name, suffixName, file.getAbsolutePath(), width, height, pixelSize,
                                (long) pageNum, size, null, 0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (photos.size() > 100) {
                        photosNum = photosNum + dahcFilePhotoService.bulkInsert(photos);
                        photos.clear();
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<?> readPictureCheck(String archivesId, String filePath, String projectId, String caseFileNumber, String pid,String procedureId,String keyName,boolean checkStatus) {
        DahcArchiveType byId = archiveTypeService.getById(archivesId);
        AssertUtil.isTrueServiceInvoke(byId != null, CommonStatus.ERROR, "未找到档案");
        String dh = archiveTypeService.queryProfileDefaultFieldBindingAttr(archivesId, byId.getTableLevel2En(), "档号");
        AssertUtil.isTrueServiceInvoke(dh != null, CommonStatus.ERROR, "未找到档号");
        CompletableFuture<String> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
            photosNum = 0;
            photosNumRepeat = 0;
            photosNumNonConforming = 0;
            fileCount = 0;
            readPictureAndMatchSaveCheck(filePath, byId, projectId, dh, caseFileNumber, pid);
            if (photos.size() > 0) {
                photosNum = dahcFilePhotoService.bulkInsertCheck(photos);
                photos.clear();
            }
            photosNumNonConforming = fileCount - photosNum - photosNumRepeat;
            return "图片共上传" + fileCount + "张，成功" + photosNum + "张，重复" + photosNumRepeat + "张，不合格" + photosNumNonConforming + "张";
        }, threadPoolTaskExecutor);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String date2 = dateFormat.format(System.currentTimeMillis());
        voidCompletableFuture.thenAccept((result) -> {
            futures.put(date2, voidCompletableFuture);
        });
        voidCompletableFuture.exceptionally((e) -> {
            futures.put(date2, voidCompletableFuture);
            e.printStackTrace();
            return String.valueOf(e);
        });
        DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(projectId,procedureId,checkStatus);
        dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                "图片文件上传", dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                keyName,caseFileNumber, null,null, 1,null, JSON.toJSONString(filePath),
                null,null,null,null,null,null,null,dahcCheckRecordLog.getCheckStatus()));
        return CommonResult.success("正在后台解析上传,请稍后", date2);
    }

    public void readPictureAndMatchSaveCheck(String filePath, DahcArchiveType dahcArchiveType, String projectId, String dh, String caseFileNumber, String pid) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        //定义符合要求的后缀
        String[] suffixImg = {"jpg", "png", "JPG", "PNG", "tif", "jpeg", "JPEG"};
        //AssertUtil.isTrueServiceInvoke(files != null, CommonStatus.ERROR, "文件格式不正确或者文件为空");
        if (files == null) {
            throw new RuntimeException("文件格式不正确或者文件为空");
        }
        /*查询最大页数*/
        int pageNum = dahcFilePhotoService.queryTheMaximumNumberOfPages(projectId, pid);
        /*当前案件存在里存在的图片数据*/
        List<DahcFilePhoto> dahcFilePhotos = dahcFilePhotoService.list(Wrappers.<DahcFilePhoto>lambdaQuery().eq(DahcFilePhoto::getProjectId, projectId).eq(DahcFilePhoto::getPid, pid));
        for (File file : files) {
            if (file.isFile()) fileCount++;

            String name = file.getName();
            long size = file.length();
            String suffixName = name.substring(name.lastIndexOf(".") + 1);
            if (file.isDirectory()) {
                //pageNum = 0;
                pageNum = dahcFilePhotoService.queryTheMaximumNumberOfPages(projectId, pid);
                //递归调用
                readPictureAndMatchSaveCheck(file.getAbsolutePath(), dahcArchiveType, projectId, dh, caseFileNumber, pid);
            } else if (Arrays.asList(suffixImg).contains(suffixName)) {

//                Linux下：”/”
//                Window下：”\\”
//                System.getProperty("file.separator")  java 中通用路径分隔符
                String fileName = name.substring(name.lastIndexOf(System.getProperty("file.separator")) + 1, name.lastIndexOf("."));
                log.info("文件名:{}", fileName);
                int i = fileName.lastIndexOf("-");
                if (i == -1) {
                    continue;
                }
                String dhName = fileName.substring(0, i);
                log.info("档号:{}", dhName);
                CurrencyUtil.dynamicSelectionTableByTableName(dahcArchiveType.getTableLevel2En());
                if (caseFileNumber.equals(dhName)) {
                    /*判断数据库是否已存在该数据*/
                    DahcFilePhoto dahcFilePhoto = dahcFilePhotos.stream().filter(s -> s.getFilePath().equals(file.getPath())).findFirst().orElse(null);
                    if (dahcFilePhoto == null) {
                        pageNum++;
                    } else {
                        photosNumRepeat++;
                    }
                    QueryWrapper<DahcDataTemplate> wrapper = new QueryWrapper<>();
                    wrapper.select("id", "attr" + dh, "project_id");
                    if (projectId != null) {
                        wrapper.eq("project_id", projectId);
                    }
                    wrapper.eq("attr" + dh, dhName);
                    DahcDataTemplate dahcDataTemplate = dahcDataTemplateMapper.selectOne(wrapper);
                    if (dahcDataTemplate != null) {
                        //读取文件内容
                        try {
                            BufferedImage image = ImageIO.read(file);
                            Double width = (double) image.getWidth();
                            Double height = (double) image.getHeight();
                            Double pixelSize = (double) image.getColorModel().getPixelSize();
                            //photosNum++;
                            photos.add(new DahcFilePhoto(String.valueOf(IdWorker.getId()),
                                    dhName, dahcDataTemplate.getId(), projectId, name, suffixName, file.getAbsolutePath(), width, height, pixelSize,
                                    (long) pageNum, size, null, 0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (photos.size() > 100) {
                            photosNum = dahcFilePhotoService.bulkInsertCheck(photos);
                            photos.clear();
                        }
                    }
                } else {
                    photosNumNonConforming++;
                }
            } else {
                throw new RuntimeException("文件格式不正确");
            }
        }
    }


    public void threadMonitoring(String tableName) {
        ThreadPoolExecutor tpe = threadPoolTaskExecutor.getThreadPoolExecutor();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = () -> {
            log.info("定时任务执行");
            if (tpe.getActiveCount() == 0 && tpe.getQueue().size() == 0) {
                log.info("开始备份未备份数据");
                //备份数据库
                dahcDataTemplateMapper.insertOriginalDataStorage(tableName, tableName + "_backup");
                //修改状态,开启数据任务流程
                int i = dahcDataTemplateMapper.modifyStatusValue(tableName, 1, 0);
                log.info("关闭定时任务");
                executorService.shutdown();
            }
        };
        executorService.scheduleWithFixedDelay(runnable, 10000, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void download(HttpServletResponse response, String url, String fileName) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                URL downloadUrl = new URL(url);
                System.out.println(downloadUrl.toString());
                InputStream inputStream = downloadUrl.openStream();
                FileOutputStream outputStream = new FileOutputStream(fileName);

                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }

                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



    private String filePath;

    @SneakyThrows
    @Override
    public CommonResult queryTemplateRelevanceAccordingToExcel(MultipartFile uploadFile, Integer num, String archivesId, Integer lv) {
        if (num == null) {
            num = 0;
        }
        File file = FileUtil.checkAndStorageFile(uploadFile);
        filePath = file.getPath();
        String suffixName = file.getName().substring((file.getName().lastIndexOf(".")) + 1).toLowerCase();
        AssertUtil.isTrueServiceInvoke(suffixName.equals("xls") || suffixName.equals("xlsx"), CommonStatus.ERROR, "请选择正确的excel文件");
        Workbook workbook = ExcelUtil.selectExcel(file.getPath());
        //根据num获取页,默认获取第一页
        Sheet sheet = workbook.getSheetAt(num);
        // 获取第一行
        Row row = sheet.getRow(0);
        // 获取这行有多少个单元格
        short lastCellNum = row.getLastCellNum();
        Map<String, String> map = new HashMap();
        for (int j = 0; j < lastCellNum; j++) {
            Cell cell = row.getCell(j);
            cell.setCellType(CellType.STRING);
            String stringCellValue = cell.getStringCellValue();
            map.put(stringCellValue, stringCellValue);
        }
        return recommendedTemplateBased(map, archivesId, lv);
    }


    public CommonResult recommendedTemplateBased(Map<String, String> orderMap, String archivesId, Integer lv) {
        List<DahcModel> dahcModels = modelService.selectMapperByModel(null, archivesId);
        dahcModels.removeAll(Collections.singleton(null));
        AssertUtil.isTrueServiceInvoke(!dahcModels.isEmpty(), CommonStatus.ERROR, "未能找到对应模板");
        List<DahcModel> collect = new ArrayList<>();
        Integer integer = Optional.ofNullable(lv).orElse(1);
        if (dahcModels.size() > 0) {
            if (integer == 2) {
                collect = dahcModels.stream().filter(a -> !"0".equals(a.getPid())).collect(Collectors.toList());
            } else {
                collect = dahcModels.stream().filter(a -> "0".equals(a.getPid())).collect(Collectors.toList());
            }
        }
        List<Map<String, String>> resMap = new ArrayList<>();
        collect.forEach(a -> {
            Map<String, String> map = new TreeMap<>(Comparator.reverseOrder());
            Map<String, String> loopMap = a.getDahcBusinessMapperList().stream().collect(Collectors.toMap(DahcBusinessMapper::getSourceName, DahcBusinessMapper::getSourceName));
            Map<String, String> intersection = CurrencyUtil.Intersection(orderMap, loopMap);
            int size1 = intersection.size();
//            int size2 = orderMap.size();
//            Double percentage = CurrencyUtil.getPercentage(size1, size2, 2);
            map.put("name", size1 + "个字段");
            map.put("size", String.valueOf(size1));
            map.put("modelName", a.getModelName());
            map.put("id", a.getId());
            map.put("archiveTypeId", a.getArchiveTypeId());
            map.put("tableName", a.getArchiveTableName());
            map.put("lv", a.getArchiveTableLevel());
            resMap.add(map);
        });
        //降序排序
        resMap.sort((o1, o2) -> {
            Integer name1 = Integer.valueOf(o1.get("size"));
            Integer name2 = Integer.valueOf(o2.get("size"));
            return name2.compareTo(name1);
        });

        return CommonResult.success(resMap);
    }

    @SneakyThrows
    @Override
    public CommonResult uploadExcelToReadTheHeaderAndSerialNumber(MultipartFile uploadFile, Integer num) {
        if (num == null) {
            num = 0;
        }
        File file = FileUtil.checkAndStorageFile(uploadFile);
        AssertUtil.isTrueServiceInvoke(file != null, CommonStatus.ERROR, "文件为空");
        Workbook workbook = ExcelUtil.selectExcel(file.getPath());
        //根据num获取页,默认获取第一页
        Sheet sheet = workbook.getSheetAt(num);
        // 获取第一行
        Row row = sheet.getRow(0);
        // 获取这行有多少个单元格
        short lastCellNum = row.getLastCellNum();
        Map<String, String> map = new HashMap();
        for (int j = 0; j < lastCellNum; j++) {
            Cell cell = row.getCell(j);
            cell.setCellType(CellType.STRING);
            String stringCellValue = cell.getStringCellValue();
            map.put("attr" + j, stringCellValue);

        }
        return CommonResult.success(map);
    }

    /**
     * 根据档案类型id和表等级选择对应的表
     */
    public boolean dynamicSelectionTable(Long archiveTypeId, Integer level) {
        DahcArchiveType byId = archiveTypeService.findById(archiveTypeId);
        if (byId != null) {
            String tableLevelEn = level == 2 ? byId.getTableLevel2En() : byId.getTableLevel1En();
            CurrencyUtil.dynamicSelectionTableByTableName(tableLevelEn);
            return true;
        }
        return false;
    }


    @Override
    public List<Map<String, String>> queryDataThatHasNotBeenChecked(String tableName, String attr, String projectId) {
        return dahcDataTemplateMapper.queryDataThatHasNotBeenChecked(tableName, attr, projectId);
    }


    @Override
    public List<Map<String, String>> queryDataThatHasNotBeenCheckedNoStatus(String tableName, String attr) {
        return dahcDataTemplateMapper.queryDataThatHasNotBeenCheckedNoStatus(tableName, attr);
    }

    @Override
    public List<Map<String, String>> queryDataThatHasNotBeenBulk(String tableName, String attr, String key) {
        return dahcDataTemplateMapper.queryDataThatHasNotBeenBulk(tableName, attr, key);
    }

    @Override
    public int modifyStatusValue(String tableName, Integer newStatus, Integer oldStatus) {
        return dahcDataTemplateMapper.modifyStatusValue(tableName, newStatus, oldStatus);
    }

    @Override
    public int modifyStatusValueInList(String tableName, Integer newStatus, Integer oldStatus, List<String> list) {
        String string = CurrencyUtil.listToString(list, ',');
        return dahcDataTemplateMapper.modifyStatusValueInList(tableName, newStatus, oldStatus, string);
    }

    @Override
    public CommonResult<Object> exportExcelAccordingToFileType(DataTemplateDto dto, HttpServletResponse response) {
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeAndMetadataVos = archiveTypeService.selectListAndMetadataById(dto.getLevel(), dto.getArchiveTypeId());
        DahcArchiveTypeAndMetadataVo dahcArchiveTypeAndMetadataVo = dahcArchiveTypeAndMetadataVos.get(0);
        String tableLevel = "";
        if (dto.getLevel() == 2) {
            /*案件数据*/
            tableLevel = dahcArchiveTypeAndMetadataVo.getTableLevel2En();
            /*档号attr*/
            String attr = archiveTypeService.queryProfileDefaultFieldBindingAttr(String.valueOf(dto.getArchiveTypeId()), tableLevel, "档号");

        } else {
            /*案卷数据*/
            tableLevel = dahcArchiveTypeAndMetadataVo.getTableLevel1En();
        }
        //获取元数据
        List<DahcMetadataVo> dahcMetadataList = dahcArchiveTypeAndMetadataVo.getDahcMetadataList();
        //获取元数据序号和名字
        Map<Long, String> metadataNameList = dahcMetadataList.stream().collect(Collectors.toMap(DahcMetadataVo::getAttrOrder, DahcMetadata::getMetadataName));
        //创建excel 设置格式和表头
        String fileName = "导出条目信息.xlsx";
        //ExcelUtil.exportToExcel(metadataNameList, fileName, dto.getLevel());
        //获取需要查询的字段
        String collect = dahcMetadataList.stream().map(r -> "attr" + r.getAttrOrder()).collect(Collectors.joining(","));
        CurrencyUtil.dynamicSelectionTableByTableName(tableLevel);
        List<DahcDataTemplate> dahcDataTemplateListBigData = getDahcDataTemplateListBigData(dto, collect, null, response);
        return CommonResult.success(dahcDataTemplateListBigData);
    }


    public List<DahcDataTemplate> getDahcDataTemplateListBigData(DataTemplateDto dto, String sqlSelect, List<Long> subDeptIdList, HttpServletResponse response) {
        List<DahcDataTemplate> dahcDataTemplate = new ArrayList<>();
        String fileName = "导出条目信息.xlsx";
        QueryWrapper<DahcDataTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(sqlSelect).eq("project_id", dto.getProjectId());


        //流式条件查询
        CurrencyResultHandler<Map<String, String>> currencyResultHandler = new CurrencyResultHandler<Map<String, String>>() {
            @Override
            public void handle() {
                try {
                    ExcelUtil.addContentToExcel(gxids, fileName,"");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                super.handle();
            }

            @Override
            public void end() {
                handle();
                String path = FileUtil.getPath();
                String fileName = "导出条目信息.xlsx";
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //将工作簿写到输出流中
                XSSFWorkbook workbook = new XSSFWorkbook();
                try {
                    workbook = new XSSFWorkbook(path + fileName);
                    workbook.write(bos);
                    new DownloadUtil().download(bos, response, "条目导出表.xlsx");
                    bos.close();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        dahcDataTemplateMapper.getDahcDataTemplateListBigData(queryWrapper, currencyResultHandler);
        currencyResultHandler.end();
        return dahcDataTemplate;
    }

    @Override
    public CommonResult<Object> exportTheAuditResults(DataTemplateDto dto, HttpServletResponse response) {
        /*获取项目数据*/
        List<ExcelExp> list = new ArrayList<>();
        /*获取工序数据集合*/
        List<DahcProjectProcedure> dahcProjectProcedures = projectProcedureService.list(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getProjectId, dto.getProjectId()).orderByAsc(DahcProjectProcedure::getSort));
        /*获取核查结果集合*/
        //todo
        return null;
    }

    @Override
    public CommonResult<Object> uploadFile(MultipartHttpServletRequest uploadFile) {
        return FileUtil.batchSavePictures(uploadFile);
    }

    @Override
    public CommonResult uploadFileOfZip(MultipartHttpServletRequest uploadFile) {

        Set<String> set = FileUtil.saveZip(uploadFile);
        Runnable runnable = () -> {
            List<DahcFilePhoto> list = new ArrayList<>();
            set.forEach(a -> {
                System.out.println(a);
                ZipCompressUtil.unzip(a, FileUtil.getPath() + "/img/");
                List<DahcFilePhoto> dahcFilePhotos = ZipCompressUtil.readFile(a);
                list.addAll(dahcFilePhotos);
            });
            dahcFilePhotoService.bulkInsert(list);
        };
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.execute(runnable);
        return CommonResult.success();
    }


    @Override
    public List<DahcDataTemplate> queryBasedOnPrimaryKeyIDData(String profileDataTableName, List<String> ids,
                                                               Integer pageSize, Integer pageNum,
                                                               String attrOrder, String caseNum) {
        //String join = StringUtils.join(ids.toArray(), ',');
        //CurrencyUtil.dynamicSelectionTableByTableName(profileDataTableName);
        if (ids != null && profileDataTableName != null) {
            return dahcDataTemplateMapper.queryBasedOnPrimaryKeyIDData(profileDataTableName, ids, pageSize, pageNum, attrOrder, caseNum);
        } else {
            return new ArrayList<DahcDataTemplate>();
        }

    }


    @Override
    public CommonResult<?> modifyTheSort(String id1, String id2, String archiveTypeId,DataTemplateUpAndDownDto dto) {
        /*根据档案类型获取 案件表名*/
        DahcArchiveType archiveType = archiveTypeService.findById(Long.valueOf(archiveTypeId));
        String attr = archiveTypeService.queryProfileDefaultFieldBindingAttr(archiveTypeId, archiveType.getTableLevel2En(), "顺序号/件号");
        int i = dahcDataTemplateMapper.modifyTheSort(id1, id2, archiveType.getTableLevel2En(), "attr" + attr);
        if (i > 0) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                    dto.getCaseFileNumber(), dto.getCaseNumber(), dto.getPid(), dto.getId1(), 0,null,JSON.toJSONString(dto),null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    /**
     * 根据批次查询案卷数据
     *
     * @return 根据批次查询案卷数据
     * @author MCY
     * @date 2023/3/30 14:58
     */
    @Override
    public List<Map<String, Object>> queryCaseFileDataBasedOnBatches(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {
        List<Map<String, Object>> maps = dahcDataTemplateMapper.queryCaseFileDataBasedOnBatches(dossierLevelDto.getArchiveLevelName(), dossierLevelDto.getAttrOrder(), dossierLevelDto.getBatchNo(), dossierLevelDto.getCaseNum(), dossierLevelDto.getProjectId());
        if (maps.size() > 0) {
            for (Map<String, Object> map : maps) {
                Object id = map.get("id");
                map.put("id", id.toString());
            }
        }
        return maps;
    }

    @Override
    public CommonGridResult accessPersonalVerificationData(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {
        List<Map<String, Object>> list = new ArrayList<>();
        Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        if (SecurityUtils.getUsername().equals("admin") || rolePermissions) {
            PageHelper.startPage(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize());
            list = dahcDataTemplateMapper.accessPersonalVerificationData(dossierLevelDto.getArchiveLevelName(),
                    dossierLevelDto.getProcedureId(), dossierLevelDto.getProjectId(), dossierLevelDto.getFilesName(), dossierLevelDto.getIsProcedureInspect(), null);
            Page page = (Page) list;
            return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
        } else {
            if (StringUtils.isNotEmpty(dossierLevelDto.getIsProcedureInspect())) {
                if (dossierLevelDto.getIsProcedureInspect().equals("0")) {
                    PageHelper.startPage(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize());
                    list = dahcDataTemplateMapper.notClaimedAccessPersonalVerificationData(dossierLevelDto.getArchiveLevelName(),
                            dossierLevelDto.getProcedureId(), dossierLevelDto.getProjectId(), dossierLevelDto.getFilesName(), dossierLevelDto.getIsProcedureInspect(), SecurityUtils.getUserId());
                    Page page = (Page) list;
                    return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
                } else {
                    PageHelper.startPage(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize());
                    list = dahcDataTemplateMapper.accessPersonalVerificationData(dossierLevelDto.getArchiveLevelName(),
                            dossierLevelDto.getProcedureId(), dossierLevelDto.getProjectId(), dossierLevelDto.getFilesName(), dossierLevelDto.getIsProcedureInspect(), SecurityUtils.getUserId());
                    Page page = (Page) list;
                    return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
                }
            } else {
                return CommonGridResult.initData(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize(), list.size(), list);
            }

        }
    }

    @Override
    public List<Map<String, Object>> queryPersonalDoubtfulData(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {
        List<Map<String, Object>> list = new ArrayList<>();
        //Boolean rolePermissions = dahcSysDictDataService.getRolePermissions(SecurityUtils.getUserId());
        //if (SecurityUtils.getUsername().equals("admin")||rolePermissions)  {
        //    list = dahcDataTemplateMapper.queryPersonalDoubtfulData(dossierLevelDto.getArchiveLevelName(),
        //            dossierLevelDto.getProcedureId(), dossierLevelDto.getProjectId(), dossierLevelDto.getFilesName(), null);
        //} else {
        list = dahcDataTemplateMapper.queryPersonalDoubtfulData(dossierLevelDto.getArchiveLevelName(),
                dossierLevelDto.getProcedureId(), dossierLevelDto.getProjectId(), dossierLevelDto.getFilesName(), SecurityUtils.getUserId());
        //}

        return list;
    }


    public void verificationGradeIndex(String dh, String tableName) {
        //获取索引
        List<String> attrList = dahcDataTemplateMapper.indexFieldFound(tableName);
        //如果获取的索引数量==0
        if (attrList.size() == 0) {
            //新建索引
            dahcDataTemplateMapper.createIndex(tableName, "attr" + dh);
            dahcDataTemplateMapper.createIndex(tableName + "_backup", "attr" + dh);
            //如果获取的索引数量==1
        } else if (attrList.size() == 1) {
            String s = attrList.get(0);
            //判断该索引是否和档号进行对应
            if (!s.equals("attr" + dh)) {
                //删除索引
                List<String> indexs = dahcDataTemplateMapper.indexFieldFound2(tableName);
                String s1 = indexs.get(0);
                dahcDataTemplateMapper.deleteIndex(tableName, s1);
                dahcDataTemplateMapper.deleteIndex(tableName+ "_backup", s1);
                //对档号新建索引
                dahcDataTemplateMapper.createIndex(tableName, "attr" + dh);
                int index = dahcDataTemplateMapper.createIndex(tableName + "_backup", "attr" + dh);
            }
        }
//        else {
//            List<String> indexs = dahcDataTemplateMapper.indexFieldFound2(tableName);
//            AtomicBoolean sign = new AtomicBoolean(false);
//            indexs.forEach(a -> {
//                if (a.equals("ux_attr_dh")) {
//                    sign.set(true);
//                } else {
//                    //删除索引
//                    dahcDataTemplateMapper.deleteIndex(tableName, a);
//                }
//            });
//            if (!sign.get()) {
//                dahcDataTemplateMapper.createIndex(tableName, "attr" + dh);
//            }
//        }
    }
}
