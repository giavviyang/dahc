package com.fudian.dahc.controller.business;


import com.alibaba.fastjson2.JSON;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.*;
import com.fudian.dahc.pojo.query.DataTemplateQuery;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.fileInfo.ExprotService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "项目管理-数据导入")
@ApiSupport(order = 19)
@RestController
@Slf4j
@RequestMapping("/datatemplate")
public class DataTemplateManagementController extends MyBaseController {


    private final DataTemplateService dataTemplateService;

    @Autowired
    private ExprotService exprotService;

    public DataTemplateManagementController(DataTemplateService dataTemplateService) {
        this.dataTemplateService = dataTemplateService;
    }


    @Log(title = "上传excel 获取表头和对应序号", businessType = BusinessType.OTHER)
    @ApiOperationSupport(order = 10, ignoreParameters = "")
    @ApiOperation(value = "上传excel 获取表头和对应序号", hidden = true)
    @PostMapping("/uploadExcelToReadTheHeaderAndSerialNumber")
    public CommonResult<Object> uploadExcelToReadTheHeaderAndSerialNumber(@RequestPart @RequestParam("file") MultipartFile uploadFile, Integer sheetNum) {
        return dataTemplateService.uploadExcelToReadTheHeaderAndSerialNumber(uploadFile, sheetNum);
    }

    @Log(title = "上传excel 解析表头 推荐模板", businessType = BusinessType.OTHER)
    @ApiOperationSupport(order = 11, ignoreParameters = {"^id", "modelId", "projectId", "batchNoList", "sortList"})
    @ApiOperation("上传excel 解析表头 推荐模板")
    @PostMapping("/queryTemplateRelevanceAccordingToExcel")
    public CommonResult<Object> parseExcelHeader(DahcDataTemplateUploadDto dto) {
        return dataTemplateService.queryTemplateRelevanceAccordingToExcel(dto.getFile()[0], null, dto.getArchivesId(), dto.getTableLv());
    }

    @Log(title = "上传Excel 导入案卷条目数据", businessType = BusinessType.INSERT)
    @ApiOperationSupport(order = 12, ignoreParameters = {"modelId", "archivesId", "tableLv", "batchNoList", "sortList"})
    @ApiOperation("上传Excel 导入案卷条目数据")
    @PostMapping("/importDataVolume")
    public CommonResult<?> importDataVolume(DahcDataTemplateUploadDto dto) {
        return dataTemplateService.importDataVolume(dto.getId(), dto.getProjectId(), dto.getFile());
    }

    @Log(title = "上传Excel 导入文件条目数据", businessType = BusinessType.INSERT)
    @ApiOperation("上传Excel 导入文件条目数据")
    @ApiOperationSupport(order = 13)
    @PostMapping("/importDataPiece")
    public CommonResult<?> importDataPiece(@RequestPart @RequestParam("file") MultipartFile[] uploadFile, @RequestParam(value = "dtoJson") String dto) throws NoSuchFieldException {
        DataTemplateDto dataTemplateDto = JSON.parseObject(dto, DataTemplateDto.class);
        List<RuleSerialNumberDto> list = dataTemplateDto.getList();
        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(RuleSerialNumberDto::getName, RuleSerialNumberDto::getOrder));
        String join = StringUtils.join(dataTemplateDto.getBatchNoList().toArray(), ',');
        return dataTemplateService.importDataPiece(dataTemplateDto.getModelId(), dataTemplateDto.getProjectId(), dataTemplateDto.getBatchNoList(), uploadFile, collect);
    }


    @Log(title = "目录读取图片", businessType = BusinessType.INSERT)
    @ApiOperationSupport(order = 14)
    @ApiOperation("目录读取图片")
    @PostMapping("/readingPicturesFromAFileDirectory")
    public CommonResult<?> readingPicturesFromAFileDirectory(@RequestBody @Validated DahcReadFilePhotoDto dahcFilePhoto) {
        return dataTemplateService.readPicture(dahcFilePhoto.getArchivesId(), dahcFilePhoto.getFilePath(), dahcFilePhoto.getProjectId());
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

    @Log(title = "查询档案表头 根据档案id和表等级", businessType = BusinessType.OTHER)
    @ApiOperationSupport(order = 21, ignoreParameters = {
            ""
    })
    @ApiOperation("查询档案表头 根据档案id和表等级")
    @PostMapping("/getFileHeader")
    public CommonResult<List<DahcArchiveTypeAndMetadataVo>> getHeader(@RequestBody DataTemplateQuery dataTemplateQuery) {
        return dataTemplateService.getHeader(dataTemplateQuery);
    }

    @Log(title = "查询档案内容 根据档案id和表等级", businessType = BusinessType.OTHER)
    @ApiOperationSupport(order = 22, ignoreParameters = {
            ""
    })
    @ApiOperation("查询档案内容 根据档案id和表等级")
    @PostMapping("/getFileBody")
    public CommonGridResult getBody(@RequestBody DataTemplateQuery dataTemplateQuery) {
        return dataTemplateService.getBody(dataTemplateQuery);
    }

    @Log(title = "查询档案内容 根据表名和其他条件 (分页)", businessType = BusinessType.OTHER)
    @ApiOperationSupport(order = 23, ignoreParameters = {
            "^dahcDataTemplateList", "^archivesId", "^fileName", "^batchNo", "^desc", "^url", "^list"
    })
    @ApiOperation("查询档案内容 根据表名和其他条件 (分页)")
    @PostMapping("/readMetadata")
    public CommonResult<?> readMetadata(@RequestBody DataTemplateDto dto) {
        return dataTemplateService.readMetadata(dto);
    }


    //    @Log(title = "excel根据档案id导出所有数据 ", businessType = BusinessType.EXPORT)
    @ApiOperation("excel根据档案id导出所有数据")
    @ApiOperationSupport(order = 32)
    @PostMapping("/exportExcelAccordingToFileType")
    public CommonResult<?> exportExcelAccordingToFileType(@RequestBody DataTemplateDto dto) {
        return exprotService.exportExcelAccordingToFileType(dto);
    }


    @ApiOperation("excel根据档案id导出核查结果")
    @ApiOperationSupport(order = 35)
    @PostMapping("/exportTheAuditResults")
    public void exportTheAuditResults(@RequestBody DataTemplateDto dto, HttpServletResponse response) {
        dataTemplateService.exportTheAuditResults(dto, response);
    }

    @Log(title = "导出文件  根据档案类型id", businessType = BusinessType.EXPORT)
    @ApiOperationSupport(order = 31)
    @ApiOperation(value = "导出文件  根据档案类型id", hidden = true)
    @PostMapping("/exportDataToZip")
    public void exportDataToZip(DataTemplateDto dto, HttpServletResponse response) {
        //todo
        dataTemplateService.download(response, dto.getUrl(), dto.getFileName());
    }

    @ApiOperationSupport(order = 40)
    @ApiOperation(value = "导出文件  获取文件信息并压缩", hidden = true)
    @PostMapping("/getTheFileInformationAndCompressIt")
    public CommonResult<?> getTheFileInformationAndCompressIt(@RequestBody DahcFilePhotoDto dto) {
        return exprotService.getTheFileInformationAndCompressIt(dto);
    }

    /**
     * 获取异步任务执行结果
     */
    @ApiOperationSupport(order = 20)
    @ApiOperation("查询异步任务执行结果")
    @GetMapping("getzipInformation/{id}")
    public CommonResult<?> getzipInformation(@PathVariable("id") String id) {
        return exprotService.getzipInformation(id);
    }
}

