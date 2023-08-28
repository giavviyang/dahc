package com.fudian.dahc.service.recordTrueing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.TaskManagementProcessVo;
import com.fudian.dahc.pojo.dto.TaskRecordProcedureFilesDto;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-24
 */
public interface IDahcRecordProcedureFilesService extends IService<DahcRecordProcedureFiles> {


    /**
     *条件查询
     * @return java.util.List<com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles>
     * @author MCY
     * @date 2023/3/10 16:31
     */
    List<DahcRecordProcedureFiles> conditionalQueries(DahcRecordProcedureFiles dahcRecordProcedureFiles);

    public CommonResult associateTheCurrentOperation(Long userId, String procedureId);

    CommonResult modifyTheVerifier(DahcRecordProcedureFiles dahcRecordProcedureFiles);

    Integer theNumberOfFilesNotVerified(String procedureId);


    Integer numberOfFilesVerified(String procedureId);
    /**
     *查询档案未核查数量
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/3/27 11:53
     */
    List<DahcRecordProcedureFiles> theNumberOfUncheckedFilesInTheQueryFile(DahcRecordProcedureFiles dahcRecordProcedureFiles);
    List<DahcDataTemplate> theTaskManagementPageQueriesTheSuspectData(TaskRecordProcedureFilesDto dahcRecordProcedureFiles, List<String> recordProcedureFiles);


    /**
     * 实体档案：领取档案数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/28 15:53
     */
    CommonResult drawEntityFile(DahcRecordProcedureFiles procedureFiles);

    /**
     * 根据档案id集合领取档案
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 17:41
     */
    CommonResult collectYourFileByIds(DahcRecordProcedureFiles procedureFiles);


    /**
     * 查询电子档案是否有核查到一半的数据，并根据核查时间回显最近一次核查到一半的数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles>
     * @author MCY
     * @date 2023/3/3 10:54
     */
    DahcRecordProcedureFiles queryTheResultsOfTheVerificationWereNotCompleted(RecordTrueingDto dto);

    /**
     * 查询电子档案是否有核查到一半的数据，并根据核查时间回显最近一次核查到一半的数据
     *
     * @return com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles
     * @author MCY
     * @date 2023/3/4 16:34
     */
    DahcRecordProcedureFiles checkWhetherTheVerificationIsIncomplete(RecordTrueingDto dto);

    List<Map<String, Object>> queryBasedOnTheIDOfTheVerificationResult(QueryElectronicArchivesDossierLevelDto dto);
    List<TaskManagementProcessVo> taskManagementQueryOperations(TaskManagementProcessVo taskManagementProcessVo);


    boolean bulkInsert(List<DahcRecordProcedureFiles> dahcRecordProcedureFiles);

    CommonResult<Object> startVerificationButton(String projectId);

    List<Map<String, String>> queryCaseLevelArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto);
    List<Map<String, String>> queryArchivesDossierLevelTemplateTwoBackup(QueryElectronicArchivesDossierLevelDto dto);
    CommonResult taskManagementCompletesTheVerification(String[] ids);

    Integer homepageCheckResultQuery(DahcRecordProcedureFiles files);
    Integer homePageToBePickedUpTaskQuery(DahcRecordProcedureFiles files);
    Integer theTaskHasBeenClaimed(DahcRecordProcedureFiles files);
}
