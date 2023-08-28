package com.fudian.dahc.service.statistics;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.dto.StatisticsDto;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-21
 */
public interface DahcStatisticsService
{
    List<StatisticsDto> verificationOfProjectStatistics(Integer dataUnderVerification);
    /*核查项目与人数统计*/
    List<StatisticsDto> verificationItemsAndPeopleCounting(Integer dataUnderVerification);
    List<StatisticsDto> checkTheStatisticsOfTheResultsOfTheProject(Integer dataUnderVerification);
}
