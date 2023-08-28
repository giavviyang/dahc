package com.fudian.dahc.service.recordTrueing;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-21
 */
public interface IDahcRecordTrueingResultService
{


    List<RecordTrueingDto>  queryProcedureInspect(RecordTrueingDto dto);

    CommonResult insertProcedureInspect(List<RecordTrueingDto> dto);






    /**
     *电子档案: 查询电子档案数据 案卷级
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/2 12:15
     */
    List<Map<String,Object>> queryElectronicArchivesDossierLevel(QueryElectronicArchivesDossierLevelDto dto,Integer start);
    CommonGridResult queryElectronicArchivesDossierLevelTemplateTwo(QueryElectronicArchivesDossierLevelDto dto);

    /**
     *
     * @return com.fudian.dahc.pojo.dto.RecordTrueingDto
     * @author MCY
     * @date 2023/3/3 11:11
     */
    List<RecordTrueingDto> queryCheckItems(RecordTrueingDto dto);


    /**
     * 回显核查结果
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.RecordTrueingDto>
     * @author MCY
     * @date 2023/3/3 11:17
     */
    RecordTrueingDto echoTheVerificationResults(RecordTrueingDto dto, DahcRecordProcedureFiles dahcRecordProcedureFiles);
    /**
     *存疑查询核查结果数据
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/13 11:35
     */
    List<RecordTrueingDto> doubtQueryVerificationResultData(QueryElectronicArchivesDossierLevelDto dto);


    /**
     *存疑提交核查结果
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/13 17:23
     */
    CommonResult submissionOfVerificationResultsInDoubt(List<RecordTrueingDto> dto);
}
