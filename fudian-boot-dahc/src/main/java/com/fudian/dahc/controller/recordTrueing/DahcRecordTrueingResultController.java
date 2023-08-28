package com.fudian.dahc.controller.recordTrueing;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.core.page.TableDataInfo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.mapper.business.DahcDataTemplateMapper;
import com.fudian.dahc.mapper.recordTrueing.DahcRecordProcedureFilesMapper;
import com.fudian.dahc.pojo.dto.*;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DahcFilePhotoService;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordTrueingResultService;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingStandardService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Controller
 *
 * @author fudian
 * @date 2023-02-21
 */

@Api(tags = "档案核查-核查结果处理")
@ApiSupport(order = 25)
@RestController
@RequestMapping("/recordTrueing")
public class DahcRecordTrueingResultController extends BaseController {
    @Autowired
    private IDahcRecordTrueingResultService dahcRecordTrueingResultService;
    @Autowired
    private IDahcRecordProcedureFilesService dahcRecordProcedureFilesService;
    @Autowired
    private IDahcHcxTrueingStandardService dahcHcxTrueingStandardService;
    @Autowired
    private DahcRecordProcedureFilesMapper dahcRecordProcedureFilesMapper;
    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;

    /**
     * 查询核查结果
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/2/22 13:56
     */
    //@PreAuthorize("@ss.hasPermi('system:result:list')")
    @ApiOperation("实体档案：查询核查结果")
    @GetMapping("/queryProcedureInspect")
    public CommonResult queryProcedureInspect(RecordTrueingDto dto) {
        List<RecordTrueingDto> recordTrueingDtos = dahcRecordTrueingResultService.queryProcedureInspect(dto);;
        return CommonResult.success(recordTrueingDtos);
    }

    /**
     * 新增核查结果
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/2/22 15:21
     */
    @ApiOperation("提交核查结果")
    @PostMapping("/insertProcedureInspect")
    public CommonResult insertProcedureInspect(@RequestBody List<RecordTrueingDto> dto) {
        return dahcRecordTrueingResultService.insertProcedureInspect(dto);
    }


    /**
     *修改核查人
     * @return
     * @author MCY
     * @date 2023/4/21 14:26
     */
    @ApiOperation("修改核查人")
    @PostMapping("/modifyTheVerifier")
    public CommonResult modifyTheVerifier(@RequestBody DahcRecordProcedureFiles dahcRecordProcedureFiles) {

        return dahcRecordProcedureFilesService.modifyTheVerifier(dahcRecordProcedureFiles);
    }
    /**
     * 实体档案：领取档案数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/28 15:53
     */
    @ApiOperation("实体档案：领取档案数据")
    @PostMapping("/drawEntityFile")
    public CommonResult drawEntityFile(@RequestBody DahcRecordProcedureFiles procedureFiles) {
        return dahcRecordProcedureFilesService.drawEntityFile(procedureFiles);
    }



    /**
     * 根据档案id集合领取档案
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 17:41
     */
    @ApiOperation("根据档案id集合领取档案")
    @PostMapping("/collectYourFileByIds")
    public CommonResult collectYourFileByIds(@RequestBody DahcRecordProcedureFiles procedureFiles) {
        return dahcRecordProcedureFilesService.collectYourFileByIds(procedureFiles);
    }

    /**
     * @return
     */
    @ApiOperation("把数据加入到第一个工序中")
    @PostMapping("/startVerificationButton")
    public CommonResult startVerificationButton(String projectId) {
        return dahcRecordProcedureFilesService.startVerificationButton(projectId);
    }


    /**
     * 电子档案：查询核查结果
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/3 10:54
     */
    @ApiOperation("电子档案：查询核查结果")
    @GetMapping("/queryProcedureInspectElectronicArchives")
    public CommonResult queryProcedureInspectElectronicArchives(RecordTrueingDto dto) {
        /*查询电子档案是否有核查到一半的数据，并根据核查时间回显最近一次核查到一半的数据*/
        DahcRecordProcedureFiles dahcRecordProcedureFiles = dahcRecordProcedureFilesService.queryTheResultsOfTheVerificationWereNotCompleted(dto);
        List<RecordTrueingDto> dtoList = new ArrayList<>();
        /*查询普通核查项数据*/
        dtoList = dahcRecordTrueingResultService.queryCheckItems(dto);
        if (dahcRecordProcedureFiles != null) {
            /*回显核查结果*/
            for (RecordTrueingDto recordTrueingDto : dtoList) {
                recordTrueingDto = dahcRecordTrueingResultService.echoTheVerificationResults(recordTrueingDto, dahcRecordProcedureFiles);
            }
        } else {
            for (RecordTrueingDto recordTrueingDto : dtoList) {
                /*拼接核查标准*/
                recordTrueingDto.setExamineStasString(dahcHcxTrueingStandardService.queryById(recordTrueingDto.getTrueingId()));
            }
        }
        return CommonResult.success(dtoList);
    }

    /**
     * 电子档案: 查询电子档案数据 案卷级
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 12:15
     */
    @ApiOperation("电子档案模板二: 查询电子档案数据 案卷级")
    @GetMapping("/queryElectronicArchivesDossierLevelTemplateTwo")
    public CommonGridResult queryElectronicArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto) {
        /*查询是否有核查到一半的数据*/
        return dahcRecordTrueingResultService.queryElectronicArchivesDossierLevelTemplateTwo(dto);
    }

    @ApiOperation("电子档案模板二: 查询电子档案数据 案件级")
    @JsonSerialize(using = ToStringSerializer.class)
    @GetMapping("/queryCaseLevelArchivesDossierLevelTemplateTwo")
    public CommonResult queryCaseLevelArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto) {
        return CommonResult.success(dahcRecordProcedureFilesService.queryCaseLevelArchivesDossierLevelTemplateTwo(dto));
    }

    @ApiOperation("电子档案模板二: 查询电子档案数据 案件级备份")
    @JsonSerialize(using = ToStringSerializer.class)
    @GetMapping("/queryArchivesDossierLevelTemplateTwoBackup")
    public CommonResult queryArchivesDossierLevelTemplateTwoBackup(QueryElectronicArchivesDossierLevelDto dto) {
        return CommonResult.success(dahcRecordProcedureFilesService.queryArchivesDossierLevelTemplateTwoBackup(dto));
    }


    /**
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/10 16:37
     */
    @ApiOperation("存疑页面查询存疑数据")
    @GetMapping("/conditionalQueries")
    public CommonGridResult conditionalQueries(DahcRecordProcedureFiles files) {
        PageHelper.startPage(files.getPageNum(), files.getPageSize());
        List<DahcRecordProcedureFiles> list = dahcRecordProcedureFilesService.conditionalQueries(files);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 查询档案未核查数量
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/27 11:53
     */
    @ApiOperation("查询档案未核查数量")
    @GetMapping("/theNumberOfUncheckedFilesInTheQueryFile")
    public CommonResult theNumberOfUncheckedFilesInTheQueryFile(DahcRecordProcedureFiles files) {
        List<DahcRecordProcedureFiles> list = dahcRecordProcedureFilesService.theNumberOfUncheckedFilesInTheQueryFile(files);
        return CommonResult.success(list.size());

    }

    @ApiOperation("判断是否关联当前工序")
    @PostMapping("/associateTheCurrentOperation/{procedureId}")
    public CommonResult associateTheCurrentOperation(@PathVariable("procedureId") String procedureId) {
        return dahcRecordProcedureFilesService.associateTheCurrentOperation(SecurityUtils.getUserId(),procedureId);
    }


    /**
     * 根据核查结果id查询
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/13 11:35
     */
    @ApiOperation("根据核查结果id查询档案数据 案卷级")
    @GetMapping("/queryBasedOnTheIDOfTheVerificationResult")
    public CommonResult queryBasedOnTheIDOfTheVerificationResult(QueryElectronicArchivesDossierLevelDto dto) {
        return CommonResult.success(dahcRecordProcedureFilesService.queryBasedOnTheIDOfTheVerificationResult(dto));
    }

    /**
     * 存疑查询核查结果数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/13 11:35
     */
    @ApiOperation("存疑查询核查结果数据")
    @GetMapping("/doubtQueryVerificationResultData")
    public CommonResult doubtQueryVerificationResultData(QueryElectronicArchivesDossierLevelDto dto) {
        return CommonResult.success(dahcRecordTrueingResultService.doubtQueryVerificationResultData(dto));
    }


    /**
     * 任务管理查询工序
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/16 16:14
     */
    @ApiOperation("任务管理查询工序")
    @GetMapping("/taskManagementQueryOperations")
    public CommonGridResult taskManagementQueryOperations(TaskManagementProcessVo taskManagementProcessVo) {
        PageHelper.startPage(taskManagementProcessVo.getPageNum(), taskManagementProcessVo.getPageSize());
        List<TaskManagementProcessVo> list = dahcRecordProcedureFilesService.taskManagementQueryOperations(taskManagementProcessVo);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }


    /**
     * 存疑提交核查结果
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/13 17:23
     */
    @ApiOperation("存疑提交核查结果")
    @PostMapping("/submissionOfVerificationResultsInDoubt")
    public CommonResult submissionOfVerificationResultsInDoubt(@RequestBody List<RecordTrueingDto> dto) {
        return dahcRecordTrueingResultService.submissionOfVerificationResultsInDoubt(dto);
    }

    /**
     * 任务管理页面查询数据
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/16 14:24
     */
    @ApiOperation("任务管理页面查询数据")
    @GetMapping("/theTaskManagementPageQueriesTheSuspectData")
    public CommonGridResult theTaskManagementPageQueriesTheSuspectData(TaskRecordProcedureFilesDto files) {
        PageHelper.startPage(files.getPageNum(),files.getPageSize());
        List<DahcDataTemplate> list = dahcRecordProcedureFilesService.theTaskManagementPageQueriesTheSuspectData(files,files.getIds());
        PageInfo pageInfo = new PageInfo<>(list);
        return CommonGridResult.initData(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), list);
    }

    /**
     * 任务管理页面查询数据
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/16 14:24
     */
    @ApiOperation("任务管理页面查询数据")
    @GetMapping("/taskManagementPageQueriesTheSuspectData")
    public   List<String>  taskManagementPageQueriesTheSuspectData(DahcRecordProcedureFiles files) {
        List<DahcRecordProcedureFiles> recordProcedureFiles = dahcRecordProcedureFilesMapper.theTaskManagementPageQueriesTheSuspectData(files);
        return recordProcedureFiles.stream().map(DahcRecordProcedureFiles::getFilesId).collect(Collectors.toList());
    }





    /**
     * 任务管理完成核查
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/16 18:12
     */
    @ApiOperation("任务管理完成核查")
    @PostMapping("/taskManagementCompletesTheVerification")
    public CommonResult taskManagementCompletesTheVerification(@RequestBody String[] filesIds) {
        return dahcRecordProcedureFilesService.taskManagementCompletesTheVerification(filesIds);
    }

    /**
     * description:根据url下载返回给前端二进制文件流
     *
     * @param request
     * @param response
     * @param path
     */
    @GetMapping("/download")
    public void downLoadFromUrl(DahcFilePhotoDto dto, HttpServletRequest request, HttpServletResponse response) {
        String path = dto.getFilePath();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
//            根据url创建URL对象
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);
            byte[] tmp = new byte[1024];
            String name = path.substring(path.lastIndexOf('/') + 1);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.addHeader("Access-Control-Allow-Origin", "*");//后端允许跨域
            response.addHeader("Access-Control-Expose-Headers", "Content-Disposition,download-filename");
//            将文件名转成utf8的字符串形式
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), "utf-8"));
//            获取输出流，将文件流返回给前端
            OutputStream out = response.getOutputStream();
            int len = 0;
//            从url指向的链接中读取数据保存到tmp字节数组中，写入到输出流中
            while ((len = bis.read(tmp, 0, 1024)) != -1) {
                out.write(tmp, 0, len);
            }
            DahcCheckRecordLog dahcCheckRecordLog = dahcCheckRecordLogService.queryCommonData(dto.getProjectId(), dto.getProcedureId(),dto.isCheckStatus());
            dahcCheckRecordLogService.insertDahcCheckRecordLog(new DahcCheckRecordLog(
                    dto.getButtonName(), dahcCheckRecordLog.getProjectName(), dahcCheckRecordLog.getProjectId(),
                    dahcCheckRecordLog.getProcedureName(), dahcCheckRecordLog.getProcedureId(), dahcCheckRecordLog.getCaseTableName(), null,
                    dto.getCaseFileNumber(),dto.getKeyName(), null,dto.getPid(), 1,null, JSON.toJSONString(dto),
                    String.valueOf(dto.getPageNum()),null,null,null,dto.getFilePath(),dto.getFileName(),dto.getId(),dahcCheckRecordLog.getCheckStatus()));
            bis.close();
            out.close();
        } catch (IOException e) {
            //log.error("下载文件失败", e);
            throw new RuntimeException("下载文件失败");
        }
    }

}
