package com.fudian.dahc.controller.project;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.mapper.fileInfo.UploadFileInfoMapper;
import com.fudian.dahc.pojo.entity.fileInfo.*;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.fileInfo.ChunkService;
import com.fudian.dahc.service.fileInfo.FileInfoService;
import com.fudian.dahc.util.common.ZipCompressUtil;
import com.fudian.dahc.util.fileUpload.FileInfoUtils;
import com.fudian.dahc.util.fileUpload.ServletUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传下载文件
 */
@Api(tags = "项目管理-文件上传")
@ApiSupport(order = 18)
@RestController
@Anonymous
@RequestMapping("/uploader")
public class FileUploadController {

    @Value("${fudian.profile}")
    private String uploadFolder;

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    UploadFileInfoMapper tFileInfoMapper;

    @Resource
    private ChunkService chunkService;

    @Resource
    private DataTemplateService dataTemplateService;

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 上传文件块
     *
     * @param chunk
     * @return
     */
    @ApiOperation(value = "上传文件块")
    @PostMapping("/chunk")
    public String uploadChunk(UploadFileChunkInfo chunk) {
        String apiRlt = "200";

        MultipartFile file = chunk.getUpfile();
        logger.info("file originName: {}, chunkNumber: {}", file.getOriginalFilename(), chunk.getChunkNumber());

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FileInfoUtils.generatePath(uploadFolder, chunk));
            //文件写入指定路径
            Files.write(path, bytes);
            if (chunkService.saveChunk(chunk) < 0) {
                apiRlt = "415";
            }

        } catch (IOException e) {
            e.printStackTrace();
            apiRlt = "415";
        }
        return apiRlt;
    }

    @ApiOperation(value = "查询分块")
    @GetMapping("/chunk")
    public UploadResult checkChunk(UploadFileChunkInfo chunk, HttpServletResponse response) {
        UploadResult ur = new UploadResult();

        //默认返回其他状态码，前端不进去checkChunkUploadedByResponse函数，正常走标准上传
        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);

        String file = uploadFolder + "/" + chunk.getIdentifier() + "/" + chunk.getFilename();

        //先判断整个文件是否已经上传过了，如果是，则告诉前端跳过上传，实现秒传
        if (FileInfoUtils.fileExists(file)) {
            ur.setSkipUpload(true);
            ur.setLocation(file);
            response.setStatus(HttpServletResponse.SC_OK);
            ur.setMessage("完整文件已存在，直接跳过上传，实现秒传");
            return ur;
        }

        //如果完整文件不存在，则去数据库判断当前哪些文件块已经上传过了，把结果告诉前端，跳过这些文件块的上传，实现断点续传
        ArrayList<Integer> list = chunkService.checkChunk(chunk);
        if (list != null && list.size() > 0) {
            ur.setSkipUpload(false);
            ur.setUploadedChunks(list);
            response.setStatus(HttpServletResponse.SC_OK);
            ur.setMessage("部分文件块已存在，继续上传剩余文件块，实现断点续传");
            return ur;
        }
        return ur;
    }

    @Log(title = "上传完成,合并文件", businessType = BusinessType.INSERT)
    @ApiOperation(value = "合并文件")
    @PostMapping("/mergeFile")
    public CommonResult<?> mergeFile(@RequestBody UploadFileInfoVO fileInfoVO) {

//        String rlt = "FALURE";

        //前端组件参数转换为model对象
        UploadFileInfo fileInfo = new UploadFileInfo();
        fileInfo.setFilename(fileInfoVO.getName());
        fileInfo.setIdentifier(fileInfoVO.getUniqueIdentifier());
        fileInfo.setId(fileInfoVO.getId());
        fileInfo.setTotalSize(fileInfoVO.getSize());
        fileInfo.setRefProjectId(fileInfoVO.getRefProjectId());

        //进行文件的合并操作
        String filename = fileInfo.getFilename();
        String file = uploadFolder + "/" + fileInfo.getIdentifier() + "/" + filename;
        String folder = uploadFolder + "/" + fileInfo.getIdentifier();
        String fileSuccess = FileInfoUtils.merge(file, folder, filename);
        logger.info(file);
        String type = file.substring(file.lastIndexOf(".") + 1);
        int i = file.lastIndexOf("/");
        String substring = null;
        if (i > 0) {
            substring = file.substring(0, file.lastIndexOf("."));
        }

        fileInfo.setLocation(file);
        fileInfo.setType(type);
        CommonStatus ok;
        //文件合并成功后，保存记录至数据库
        if ("200".equals(fileSuccess) || "20000".equals(fileSuccess)) {
            if (fileInfoService.addFileInfo(fileInfo) > 0) {
//                rlt = "SUCCESS";
                ok = CommonStatus.OK;
            }
        }

        //如果已经存在，则判断是否同一个项目，同一个项目的不用新增记录，否则新增
        if ("300".equals(fileSuccess) || "30000".equals(fileSuccess)) {
            List<UploadFileInfo> tfList = fileInfoService.selectFileByParams(fileInfo);
            if (tfList != null) {
                if (tfList.size() == 0 || (tfList.size() > 0 && !fileInfo.getRefProjectId().equals(tfList.get(0).getRefProjectId()))) {
                    if (fileInfoService.addFileInfo(fileInfo) > 0) {
//                        rlt = "SUCCESS";
                        ok = CommonStatus.OK;
                    }
                }
            }
        }
        if ("Zip".equals(type) || "zip".equals(type) || "ZIP".equals(type)) {
            ZipCompressUtil.unzip(file, substring + "/");
        }
        if (StringUtils.isNotEmpty(fileInfoVO.getCaseFileNumber())) {
            return dataTemplateService.readPictureCheck(fileInfoVO.getArchivesId(), substring, fileInfoVO.getRefProjectId(),fileInfoVO.getCaseFileNumber(),fileInfoVO.getPid(),fileInfoVO.getProcedureId(),fileInfoVO.getKeyName(),fileInfoVO.isCheckStatus());
        } else {
            return dataTemplateService.readPicture(fileInfoVO.getArchivesId(), substring, fileInfoVO.getRefProjectId());
        }
    }

    /**
     * 查询列表
     *
     * @return ApiResult
     */
    @ApiOperation(value = "查询列表")
    @RequestMapping(value = "/selectFileList", method = RequestMethod.POST)
    public CommonResult selectFileList(@RequestBody QueryFileInfo query) {
        PageHelper.startPage(query.getPageIndex(), query.getPageSize());
        List<UploadFileInfo> list = fileInfoService.selectFileList(query);
        PageInfo<UploadFileInfo> pageResult = new PageInfo<>(list);
        return CommonResult.success(pageResult);
    }


    /**
     * 获取异步任务执行结果
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("查询异步任务执行结果")
    @GetMapping("getInformation/{id}")
    public CommonResult<?> getInformation(@PathVariable("id") String id) {
        return dataTemplateService.getInformation(id);
    }

    /**
     * 下载文件
     *
     * @param req
     * @param resp
     */
    @Log(title = "下载文件", businessType = BusinessType.EXPORT)
    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletRequest req, HttpServletResponse resp) {
        String location = req.getParameter("location");
        String fileName = req.getParameter("filename");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        OutputStream fos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(location));
            fos = resp.getOutputStream();
            bos = new BufferedOutputStream(fos);
            ServletUtils.setFileDownloadHeader(req, resp, fileName);
            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while ((byteRead = bis.read(buffer, 0, 8192)) != -1) {
                bos.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bis.close();
                fos.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public CommonResult deleteFile(@RequestBody UploadFileInfo fileInfo) {
        int result = fileInfoService.deleteFile(fileInfo);
        return CommonResult.success(result);
    }

    /**
     * 大文件下载
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/downloadFileBig", method = RequestMethod.GET)
    //@GetMapping(value = "/downloadFileBig", method = RequestMethod.GET)
    public void downloadFileBig(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        DownloadFile.downloadFileBig(req, resp, fileStorageType);
//        String location = request.getParameter("location");
        String fileId = request.getParameter("id");
        //String fileName = request.getParameter("filename");
        LambdaQueryWrapper<UploadFileInfo> fileInfo = new LambdaQueryWrapper<>();
        fileInfo.eq(UploadFileInfo::getId, fileId);
        UploadFileInfo fileInfoOne =tFileInfoMapper.selectOne(fileInfo);
        String fileName = fileInfoOne.getFilename();
        String location = fileInfoOne.getLocation();
        File file = new File(location);
        InputStream is = null;
        OutputStream os = null;
        try {
            long startTime = System.currentTimeMillis();   //获取开始时间
            // 分片下载 Range表示方式 bytes=100-1000  100-
            long fSize = fileInfoOne.getTotalSize();
            response.setContentType("application/x-download");
//            String fileName = URLEncoder.encode(location, Constants.UTF8);
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 支持分片下载
            response.setHeader("Accept-Range", "bytes");
            response.setHeader("fSize", String.valueOf(fSize));
            response.setHeader("fName", fileName);

            long pos = 0, last = fSize - 1, sum = 0;
            if (null != request.getHeader("Range")) {
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                String numberRange = request.getHeader("Range").replaceAll("bytes=", "");
                String[] strRange = numberRange.split("-");
                if (strRange.length == 2) {
                    pos = Long.parseLong(strRange[0].trim());
                    last = Long.parseLong(strRange[1].trim());
                    if (last > fSize - 1) {
                        last = fSize - 1;
                    }
                } else {
                    pos = Long.parseLong(numberRange.replaceAll("-", "").trim());
                }
            }
            long rangeLength = last - pos + 1;
            String contentRange = new StringBuffer("bytes").append(pos).append("-").append(last).append("/").append(fSize).toString();
//            String contentRange = "bytes" + pos + "-" + last + "/" + fSize;
            response.setHeader("Content-Range", contentRange);
            response.setHeader("Content-Length", String.valueOf(rangeLength));

            /*文件解密*/
//            ImgDESService imgDESService = ImgDESFactory.getInstance();
//            FileStorageService fileStorageService = FileStorageFactory.getFileStorage(FileStorageType.valueOf(fileStorageType));
//            byte[] bytes = fileStorageService.getFile(locationReq);
//            byte[] decode = imgDESService.decode(bytes);
//            is = new BufferedInputStream(new ByteArrayInputStream(decode));
            os = new BufferedOutputStream(response.getOutputStream());
            is = new BufferedInputStream(new FileInputStream(file));

            is.skip(pos);
            byte[] buffer = new byte[1024];
            int length = 0;
            while (sum < rangeLength) {
                int readLength = (int) (rangeLength - sum);
                length = is.read(buffer, 0, (rangeLength - sum) <= buffer.length ? readLength : buffer.length);
                sum += length;
                ServletUtils.setFileDownloadHeader(request, response, fileName);
                os.write(buffer, 0, length);
            }
            long endTime = System.currentTimeMillis(); //获取结束时间
            logger.info("大文件下载完成，程序运行时间： " + (endTime - startTime) + "ms");
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}
