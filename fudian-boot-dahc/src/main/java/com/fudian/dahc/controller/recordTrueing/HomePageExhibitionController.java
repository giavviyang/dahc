package com.fudian.dahc.controller.recordTrueing;

import com.fudian.common.annotation.Anonymous;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.recordTrueing.IDahcRecordProcedureFilesService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * wenbovo
 * 2023/4/11
 * FD-dahc-project
 */

@Api(tags = "档案核查-首页展示")
@ApiSupport(order = 26)
@RestController
@RequestMapping("/homePageExhibition")
public class HomePageExhibitionController extends MyBaseController {

    @Autowired
    private IDahcRecordProcedureFilesService dahcRecordProcedureFilesService;

    @Autowired
    private DataTemplateService dataTemplateService;

    /**
     * 首页核查结果查询
     *
     * @return com.fudian.common.core.page.TableDataInfo
     * @author MCY
     * @date 2023/4/10 10:20
     */
    @ApiOperation("首页核查结果查询")
    @GetMapping("/homepageCheckResultQuery")
    public Integer homepageCheckResultQuery(DahcRecordProcedureFiles files) {
        return dahcRecordProcedureFilesService.homepageCheckResultQuery(files);
    }

    @ApiOperation("首页待领取任务查询")
    @GetMapping("/homePageToBePickedUpTaskQuery")
    public Integer homePageToBePickedUpTaskQuery(DahcRecordProcedureFiles files) {
        return dahcRecordProcedureFilesService.homePageToBePickedUpTaskQuery(files);
    }

    @ApiOperation("首页已领取任务查询")
    @GetMapping("/theTaskHasBeenClaimed")
    public Integer theTaskHasBeenClaimed(DahcRecordProcedureFiles files) {
        return dahcRecordProcedureFilesService.theTaskHasBeenClaimed(files);
    }


    /**
     * 查询个人核查数据
     *
     * @author MCY
     * @date 2023/3/30 17:12
     */
    @ApiOperationSupport(order = 25)
    @ApiOperation("查询个人核查数据")
    @GetMapping("/accessPersonalVerificationData")
    public CommonGridResult accessPersonalVerificationData(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {

       return  dataTemplateService.accessPersonalVerificationData(dossierLevelDto);

    }

    /**
     * 查询个人存疑数据
     *
     * @author MCY
     * @date 2023/3/31 10:26
     */
    @ApiOperationSupport(order = 26)
    @ApiOperation("查询个人存疑数据")
    @GetMapping("/queryPersonalDoubtfulData")
    public CommonGridResult queryPersonalDoubtfulData(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {
         PageHelper.startPage(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize());
        List<Map<String, Object>> list = dataTemplateService.queryPersonalDoubtfulData(dossierLevelDto);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }
}
