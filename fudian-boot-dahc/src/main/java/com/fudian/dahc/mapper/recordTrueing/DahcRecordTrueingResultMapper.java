package com.fudian.dahc.mapper.recordTrueing;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordTrueingResult;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.dto.UpdateImpeachResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-21
 */
@Mapper
public interface DahcRecordTrueingResultMapper extends BaseMapper<DahcRecordTrueingResult> {


    public List<RecordTrueingDto> queryInspectTableList(@Param("recordTrueingDto") RecordTrueingDto recordTrueingDto);

    /**
     * 回显数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.RecordTrueingDto>
     * @author MCY
     * @date 2023/2/23 16:45
     */
    public List<RecordTrueingDto> echoInspectTableList(RecordTrueingDto recordTrueingDto);

    /**
     * 存疑查询数据
     *
     * @return java.util.List<com.fudian.dahc.pojo.dto.RecordTrueingDto>
     * @author MCY
     * @date 2023/2/23 16:45
     */
    public List<RecordTrueingDto> doubtQueryVerificationResultData(@Param("isQuestion") String isQuestion, @Param("recordProcedureFilesId") String recordProcedureFilesId);


    /*批量修改存疑结果数据*/
/*    public int modifyDoubtfulResultsInBulk
    (@Param("impeachCaseNumResultsIds") List<String> impeachCaseNumResultsIds,
     @Param("impeachPageNumResults") List<Long> impeachPageNumResults,
     @Param("state") Integer state); // 0-件 1-页    */
     public int modifyDoubtfulResultsInBulk
    (@Param("updateImpeachResultDtos") List<UpdateImpeachResultDto> updateImpeachResultDtos,
     @Param("state") Integer state); // 0-件 1-页


    public int deleteDahcRecordTrueingResultByIds(@Param("dtos") List<RecordTrueingDto> dtos);

    public int insertDahcRecordTrueingResults(@Param("dahcRecordTrueingResults") List<DahcRecordTrueingResult> dahcRecordTrueingResults);
}
