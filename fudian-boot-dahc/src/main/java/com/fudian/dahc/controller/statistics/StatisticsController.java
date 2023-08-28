package com.fudian.dahc.controller.statistics;

import com.fudian.common.annotation.Anonymous;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.dto.StatisticsDto;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.fudian.dahc.service.statistics.DahcStatisticsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * wenbovo
 * 2023/4/11
 * FD-dahc-project
 */

@Api(tags = "档案核查-结果统计")
@ApiSupport(order = 28)
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends MyBaseController {

    @Autowired
    private DahcStatisticsService statisticsService;
    @ApiOperation("核查项目统计")
    @GetMapping("/verificationOfProjectStatistics/{dataUnderVerification}")
    public List<StatisticsDto> verificationOfProjectStatistics(@PathVariable("dataUnderVerification") Integer dataUnderVerification) {
        return statisticsService.verificationOfProjectStatistics(dataUnderVerification);
    }

    @ApiOperation("核查项目与人数统计")
    @GetMapping("/verificationItemsAndPeopleCounting/{dataUnderVerification}")
    public List<StatisticsDto> verificationItemsAndPeopleCounting(@PathVariable("dataUnderVerification") Integer dataUnderVerification) {
        return statisticsService.verificationItemsAndPeopleCounting(dataUnderVerification);
    }


    @ApiOperation("核查项目结果统计")
    @GetMapping("/checkTheStatisticsOfTheResultsOfTheProject/{dataUnderVerification}")
    public List<StatisticsDto> checkTheStatisticsOfTheResultsOfTheProject(@PathVariable("dataUnderVerification") Integer dataUnderVerification) {
        return statisticsService.checkTheStatisticsOfTheResultsOfTheProject(dataUnderVerification);
    }


}
