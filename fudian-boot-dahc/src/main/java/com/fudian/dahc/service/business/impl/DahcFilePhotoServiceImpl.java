package com.fudian.dahc.service.business.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.business.DahcFilePhotoMapper;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordProcedureFilesMapper;
import com.fudian.dahc.pojo.dto.DahcFilePhotoDto;
import com.fudian.dahc.pojo.dto.QueryFileImgDto;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.service.base.impl.MyBaseServiceImpl;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.project.ProjectTableService;
import com.fudian.dahc.util.ListBeanUtils;
import com.fudian.dahc.util.common.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 2023/3/9
 */
@Service
public class DahcFilePhotoServiceImpl extends MyBaseServiceImpl<DahcFilePhoto> implements DahcFilePhotoService {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /*默认路径*/
    @Value("${fudian.profile}")
    private String uploadFolder;

    @Autowired
    private DahcFilePhotoMapper dahcFilePhotoMapper;

    @Autowired
    private ProjectTableService projectTableService;
    @Autowired
    private DahcRecordProcedureFilesMapper dahcRecordProcedureFilesMapper;

    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;
    /**
     * 批量查询
     */
    @Override
    public List<DahcFilePhoto> bulkQueryByIds(List<String> lists) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DahcFilePhotoMapper mapper = sqlSession.getMapper(DahcFilePhotoMapper.class);
        //根据指定条件来查询
        String attribute = "key_name";
        List<DahcFilePhoto> dahcFilePhotos = mapper.getFilePhotoMapper(lists, attribute);
        sqlSession.commit();
        sqlSession.close();
        return dahcFilePhotos;
    }

    /**
     * 批量插入
     *
     * @param dahcFilePhotos dahcFilePhotos
     */
    @Override
    public Integer bulkInsert(List<DahcFilePhoto> dahcFilePhotos) {
        return dahcFilePhotoMapper.bulkInsertCheck(dahcFilePhotos);
    }

    @Override
    public Integer bulkInsertCheck(List<DahcFilePhoto> dahcFilePhotos) {
        return dahcFilePhotoMapper.bulkInsertCheck(dahcFilePhotos);
    }

    /**
     * 批量修改
     *
     * @param dahcFilePhotos dahcFilePhotos
     */
    @Override
    public boolean bulkUpdate(List<DahcFilePhoto> dahcFilePhotos) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        DahcFilePhotoMapper mapper = sqlSession.getMapper(DahcFilePhotoMapper.class);
        dahcFilePhotos.forEach(mapper::updateById);
        sqlSession.commit();
        sqlSession.clearCache();
        return true;
    }


    @Override
    public List<QueryFileImgDto> getPucter(QueryFileImgDto dto, HttpServletRequest request, HttpServletResponse respones) {
        /*获取图片信息*/
        List<DahcFilePhoto> dahcFilePhotos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery()
                .eq(DahcFilePhoto::getFileDelete, 0)
                //.like(DahcFilePhoto::getKeyName, dto.getKeyName())
                .eq(DahcFilePhoto::getPid, dto.getPid())
                .orderByAsc(DahcFilePhoto::getPageNum));
        List<QueryFileImgDto> queryFileImgDtos = ListBeanUtils.copyListProperties(dahcFilePhotos, QueryFileImgDto::new);

        for (QueryFileImgDto dahcFilePhoto : queryFileImgDtos) {
            dahcFilePhoto.setImgUrl(getPhoto(respones, dahcFilePhoto.getFilePath()));
            dahcFilePhoto.setTotalPage(String.valueOf(queryFileImgDtos.size()));
        }
        return queryFileImgDtos;
    }


    //path 为图片在服务器的绝对路径
    private String getPhoto(HttpServletResponse response, String path) {
        try {
            File file = new File(path);
            FileInputStream fis;
            fis = new FileInputStream(file);
            long size = file.length();
            byte[] temp = new byte[(int) size];
            fis.read(temp, 0, (int) size);
            fis.close();
            byte[] data = temp;
            return new String(Base64.encodeBase64(data));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取图片分辨率*/
    private String getsTheImageResolution(String path) {
        try {
            File file = new File(path);
            FileInputStream fis;
            fis = new FileInputStream(file);
            BufferedImage sourceImg = ImageIO.read(fis);
            return sourceImg.getWidth() + "*" + sourceImg.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 图片上下移动
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/22 17:35
     */
    @Override
    public CommonResult thePictureMovesUpAndDown(DahcFilePhotoDto dto) {
        DahcFilePhoto dahcFilePhoto = dahcFilePhotoMapper.selectOne(Wrappers.<DahcFilePhoto>lambdaQuery().eq(DahcFilePhoto::getId, dto.getId1()));
        Integer integer = dahcFilePhotoMapper.thePictureMovesUpAndDown(dto.getId1(), dto.getId2());
        if (integer > 0) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                    dto.getCaseFileNumber(),dto.getKeyName(), null,dto.getPid(), 1,null, JSON.toJSONString(dahcFilePhoto),
                    String.valueOf(dahcFilePhoto.getPageNum()),null,null,null,dahcFilePhoto.getFilePath(),dahcFilePhoto.getFileName(),dahcFilePhoto.getId(),dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }

    }

    /**
     * 图片替换
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/4/4 16:57
     */
    @Override
    @Transactional
    public CommonResult imageReplacement(MultipartFile uploadFile, DahcFilePhotoDto dto) {

        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(SecurityUtils.getUserId(), dto.getProcedureId());
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }
        /*判断文件类型*/
        // 获取文件名
        try {
            String fileName = uploadFile.getOriginalFilename();

            // 获取文件后缀
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            if (!prefix.equals(".jpg") & !prefix.equals(".png")) {
                return CommonResult.error("文件类型不正确");
            }
            /*重命名源文件*/
            Boolean rename = rename(dto.getFilePath(), prefix);
            if (!rename) {
                return CommonResult.error("文件上传失败");
            } else {
                /*写入文件*/
                uploadFile(uploadFile, dto.getFilePath());
                DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
                dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                        dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                        dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                        dto.getCaseFileNumber(),dto.getKeyName(), null,dto.getPid(), 1,null, JSON.toJSONString(dto),
                        String.valueOf(dto.getPageNum()),dto.getFilePath(),fileName,null,dto.getFilePath(),dto.getFileName(),dto.getId(),dahcCheckRecordLog.getCheckStatus()));
                return CommonResult.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("文件上传失败");
        }
    }

    @Override
    @Transactional
    public CommonResult imagePlusPage(MultipartFile[] uploadFile, DahcFilePhotoDto dto) {
        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(SecurityUtils.getUserId(), dto.getProcedureId());
        if (integer <= 0) {
            return CommonResult.error("没有关联当前工序");
        }
        /*页数*/
        Integer pages = 0;
        int i1 = dto.getFilePath().lastIndexOf("\\");
        if (i1 == -1) i1 = dto.getFilePath().lastIndexOf("/");
        /*文件夹路径*/
        String substring = dto.getFilePath().substring(0, i1);
        List<DahcFilePhoto> dahcFilePhotos = new ArrayList<>();
        /*查询当前页数以后的所有数据*/
        if (dto.getRadio().equals("1")) {
            /*本页前插入*/
            dahcFilePhotos = dahcFilePhotoMapper.theQueryIsIncludedUnderTheCurrentPage(dto.getProjectId(), dto.getPid(), dto.getTheCurrentNumberOfPages());
            pages = dto.getTheCurrentNumberOfPages();
        } else {
            /*本页后插入*/
            dahcFilePhotos = dahcFilePhotoMapper.queriesAllDataUnderTheCurrentPage(dto.getProjectId(), dto.getPid(), dto.getTheCurrentNumberOfPages());
            pages = dto.getTheCurrentNumberOfPages() + 1;
        }
        /*获取需要修改的数据ID集合*/
        List<String> idList = dahcFilePhotos.stream().map(DahcFilePhoto::getId).collect(Collectors.toList());
        //int i = dahcFilePhotoMapper.bulkModifyTheNumberOfPages(StringUtils.join(idList.toArray(), ','), uploadFile.length);
        if (idList.size() > 0) {
            int update = dahcFilePhotoMapper.update(new DahcFilePhoto(), Wrappers.<DahcFilePhoto>lambdaUpdate().setSql("page_num=page_num+" + uploadFile.length).in(DahcFilePhoto::getId, idList));
            if (update <= 0) {
                return CommonResult.error("加页失败，数据库数据错误");
            }
        }
        //定义符合要求的后缀
        String[] suffixImg = {"jpg", "png", "JPG", "PNG", "tif", "jpeg", "JPEG"};
        ArrayList<DahcFilePhoto> filePhotoArrayList = new ArrayList<>();
        for (MultipartFile multipartFile : uploadFile) {
            /*获取文件名称*/
            String name = multipartFile.getOriginalFilename();
            String type = name.substring(name.lastIndexOf('.') + 1);
            if (!Arrays.asList(suffixImg).contains(type)) {
                return CommonResult.error("导入文件类型错误");
            }
            /*存储路径*/
            String filePath = substring + "\\" + name;
            /*数据库加数据*/
            DahcFilePhoto dahcFilePhoto = processData(filePath, name, dto, pages, multipartFile, type);
            List<DahcFilePhoto> photos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery().eq(DahcFilePhoto::getFilePath, dahcFilePhoto.getFilePath()).eq(DahcFilePhoto::getKeyName,dahcFilePhoto.getKeyName())
                    .eq(DahcFilePhoto::getProjectId, dahcFilePhoto.getProjectId()).eq(DahcFilePhoto::getFileDelete, 0));
            if (photos.size() > 0) {
                return CommonResult.error("图片信息重复");
            }
            //List<DahcFilePhoto> filePhotos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery()
            //        .eq(DahcFilePhoto::getProjectId, dahcFilePhoto.getProjectId()).eq(DahcFilePhoto::getPid, dahcFilePhoto.getPid()).eq(DahcFilePhoto::getFileDelete, 1));
            //
            //if (filePhotos.size() > 0) {
            //    return CommonResult.error("图片信息重复");
            //}
            int insert = dahcFilePhotoMapper.insert(dahcFilePhoto);
            pages = pages + 1;
            if (insert <= 0) {
                return CommonResult.error("加页失败，数据库数据错误");
            }
            /*写入图片到本地*/
            uploadFile(multipartFile, filePath);
        }
        DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
        dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                dto.getCaseFileNumber(),dto.getKeyName(), null,dto.getPid(), 1,null, JSON.toJSONString(dto),
                String.valueOf(dto.getTheCurrentNumberOfPages()),null,null,null,substring,uploadFile[0].getOriginalFilename(),null,dahcCheckRecordLog.getCheckStatus()));
        return CommonResult.success("加页成功");
    }

    /*验证是否关联当前工序*/
    public void associateTheCurrentOperation(Long userId, String procedureId) {
        /*查询当前用户是否关联当前工序*/
        Integer integer = dahcRecordProcedureFilesMapper.associateTheCurrentOperation(userId, procedureId);
        if (integer <= 0) {
            throw new RuntimeException("没有关联当前工序");
        }
    }

    @Override
    @Transactional
    public CommonResult imagePlusPageTwo(MultipartFile[] uploadFile, DahcFilePhotoDto dto) {
        /*查询当前用户是否关联当前工序*/
        associateTheCurrentOperation(SecurityUtils.getUserId(), dto.getProcedureId());

        Integer pages = 0;
        DahcProjectTable projectTable = projectTableService.getOne(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getId, dto.getProjectId()));
        //定义符合要求的后缀
        String[] suffixImg = {"jpg", "png", "JPG", "PNG", "tif", "jpeg", "JPEG"};
        for (MultipartFile multipartFile : uploadFile) {
            pages = pages + 1;
            /*获取文件名称*/
            String name = multipartFile.getOriginalFilename();
            String type = name.substring(name.lastIndexOf('.') + 1);
            if (!Arrays.asList(suffixImg).contains(type)) {
                return CommonResult.error("导入文件类型错误");
            }
            /*存储路径  项目名称+案卷档号+案件档号+文件名称*/
            String filePath = uploadFolder + "\\" + projectTable.getProjectName() + "\\" + dto.getKeyName() + "\\" + dto.getCaseFileNumber() + "\\" + name;
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(multipartFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*添加数据*/
            int insert = dahcFilePhotoMapper.insert(new DahcFilePhoto(dto.getCaseFileNumber(), dto.getPid(), dto.getProjectId(), name, type, filePath, Double.valueOf(bufferedImage.getWidth())
                    , Double.valueOf(bufferedImage.getHeight()), null, (Long.valueOf(pages)), Long.valueOf(pages), null, 0));
            List<DahcFilePhoto> photos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery().eq(DahcFilePhoto::getFilePath, filePath).eq(DahcFilePhoto::getKeyName,dto.getKeyName())
                    .eq(DahcFilePhoto::getProjectId, dto.getProjectId()).eq(DahcFilePhoto::getFileDelete, 0));
            if (photos.size() > 0) {
                return CommonResult.error("图片信息重复");
            }
            if (insert <= 0) {
                return CommonResult.error("加页失败，数据库数据错误");
            }
            /*写入图片到本地*/
            uploadFile(multipartFile, filePath);
        }
        DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
        dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                dto.getKeyName(),dto.getCaseFileNumber(), null,dto.getPid(), 1,null, JSON.toJSONString(dto),
                "1",null,null,null,
                uploadFolder + "\\" + projectTable.getProjectName() + "\\" + dto.getKeyName() + "\\" + dto.getCaseFileNumber(),uploadFile[0].getOriginalFilename(),null,dahcCheckRecordLog.getCheckStatus()));
        return CommonResult.success("加页成功");
    }

    @Override
    public CommonResult imageMinus(DahcFilePhotoDto dto) {
        associateTheCurrentOperation(SecurityUtils.getUserId(),dto.getProcedureId());

        DahcFilePhoto dahcFilePhoto = dahcFilePhotoMapper.selectOne(Wrappers.<DahcFilePhoto>lambdaQuery().eq(DahcFilePhoto::getId, dto.getFilePhotoId()));
        List<DahcFilePhoto> filePhotos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery()
                .eq(DahcFilePhoto::getProjectId, dahcFilePhoto.getProjectId()).eq(DahcFilePhoto::getPid, dahcFilePhoto.getPid()).eq(DahcFilePhoto::getFileDelete, 1));
        if (filePhotos.size() > 0) {
            dahcFilePhotoMapper.delete(Wrappers.<DahcFilePhoto>lambdaQuery().in(DahcFilePhoto::getId, filePhotos.stream().map(DahcFilePhoto::getId).collect(Collectors.toList())));
        }
        int update = dahcFilePhotoMapper.update(new DahcFilePhoto(), Wrappers.<DahcFilePhoto>lambdaUpdate().eq(DahcFilePhoto::getId, dto.getFilePhotoId()).set(DahcFilePhoto::getFileDelete, 1));
        if (update > 0) {
            /*修改当前当前页后的所有数据*/
            List<DahcFilePhoto> dahcFilePhotos = dahcFilePhotoMapper.selectList(Wrappers.<DahcFilePhoto>lambdaQuery()
                    .eq(DahcFilePhoto::getPid, dahcFilePhoto.getPid()).eq(DahcFilePhoto::getFileDelete, 0).gt(DahcFilePhoto::getPageNum, dahcFilePhoto.getPageNum()));
            /*Id集合*/
            List<String> ids = dahcFilePhotos.stream().map(DahcFilePhoto::getId).collect(Collectors.toList());
            if (ids.size() > 0) {
                int update1 = dahcFilePhotoMapper.update(new DahcFilePhoto(), Wrappers.<DahcFilePhoto>lambdaUpdate().in(DahcFilePhoto::getId, ids).setSql("page_num = page_num - 1"));
                if (update1 > 0) {
                    DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
                    dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                            dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                            dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                            dto.getKeyName(),dto.getCaseFileNumber(), null,dto.getPid(), 1,JSON.toJSONString(dahcFilePhoto), null,
                            "1",dahcFilePhoto.getFilePath(),dahcFilePhoto.getFileName(),dahcFilePhoto.getId(),
                            null,null,null,dahcCheckRecordLog.getCheckStatus()));
                    return CommonResult.success("减页成功");
                }
            } else {
                DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
                dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                        dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                        dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), dahcCheckRecordLog.getFileTableName(),
                        dto.getKeyName(),dto.getCaseFileNumber(), null,dto.getPid(), 1,JSON.toJSONString(dahcFilePhoto), null,
                        String.valueOf(dahcFilePhoto.getPageNum()),dahcFilePhoto.getFilePath(),dahcFilePhoto.getFileName(),dahcFilePhoto.getId(),
                        null,null,null,dahcCheckRecordLog.getCheckStatus()));
                return CommonResult.success("减页成功");
            }

        }
        return CommonResult.error("减页失败");
    }

    @Override
    public CommonResult modifyTheNumberOfPages(DahcFilePhotoDto dto) {
        int i = dahcFilePhotoMapper.modifyTheNumberOfPages(dto.getProjectId(), dto.getPid(), dto.getTheNumberOfStartingPages() - 1, dto.getStartPages(), dto.getComeToAnEndPages());
        if (i > 0) {
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                    dto.getCaseFileNumber(),dto.getKeyName(), null,dto.getPid(), 1,null, JSON.toJSONString(dto),
                    String.valueOf(dto.getStartPages()),null,null,null,null,null,null,dahcCheckRecordLog.getCheckStatus()));
            return CommonResult.success("修改成功");
        } else {
            return CommonResult.error("修改失败");
        }
    }

    @Override
    public Integer queryTheMaximumNumberOfPages(String projectId, String pid) {
        return dahcFilePhotoMapper.queryTheMaximumNumberOfPages(projectId, pid);
    }

    /*处理数据*/
    private DahcFilePhoto processData(String filePath, String fileName, DahcFilePhotoDto dto, Integer pages, MultipartFile multipartFile, String type) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        DahcFilePhoto dahcFilePhoto = new DahcFilePhoto();
        dahcFilePhoto.setId(String.valueOf(IdWorker.getId()));
        dahcFilePhoto.setFilePath(filePath);
        dahcFilePhoto.setProjectId(dto.getProjectId());
        dahcFilePhoto.setKeyName(dto.getKeyName());
        dahcFilePhoto.setFileName(fileName);
        dahcFilePhoto.setPid(dto.getPid());
        dahcFilePhoto.setPageNum(Long.valueOf(pages));
        dahcFilePhoto.setFileSize(multipartFile.getSize());
        dahcFilePhoto.setWidth(Double.valueOf(bufferedImage.getWidth()));
        dahcFilePhoto.setHeight(Double.valueOf(bufferedImage.getHeight()));
        dahcFilePhoto.setFileType(type);
        return dahcFilePhoto;
    }

    /*文件重命名*/
    private Boolean rename(String filePath, String prefix) {
        try {
            //获取源文件
            File file = new File(filePath);
            String name = file.getName();
            int i = name.lastIndexOf(".");
            /*文件名称*/
            String substring = name.substring(0, i);
            String nowtime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            substring = substring + "-" + "修改时间-" + nowtime;
            String prefix1 = name.substring(name.lastIndexOf("."));
            substring = substring + prefix1;
            File file1 = new File(file.getParentFile(), substring);
            return file.renameTo(file1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
            //return false;
        }
    }


    private void uploadFile(MultipartFile mFile, String path) {
        try {
            InputStream in = mFile.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            OutputStream out = new FileOutputStream(path);
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            System.out.println("----------" + path + "文件上传失败————————");
            e.printStackTrace();
            throw new RuntimeException(path + "文件上传失败");
        }
    }
}
