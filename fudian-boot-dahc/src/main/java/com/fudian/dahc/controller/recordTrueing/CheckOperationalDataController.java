package com.fudian.dahc.controller.recordTrueing;


import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.CommonStatus;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.DahcDataTemplateDto;
import com.fudian.dahc.pojo.dto.DataTemplateUpAndDownDto;
import com.fudian.dahc.pojo.dto.QueryElectronicArchivesDossierLevelDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.pojo.query.DataTemplateQuery;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.fudian.dahc.service.business.DataTemplateService;
import com.fudian.dahc.service.business.impl.DahcFilePhotoServiceImpl;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.fudian.dahc.util.common.AssertUtil;
import com.fudian.dahc.util.common.CurrencyUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * wenbovo
 * 2023/4/11
 * FD-dahc-project
 */

@Api(tags = "档案核查-条目数据管理")
@ApiSupport(order = 28)
@RestController
@RequestMapping("/datatemplateCheck")
public class CheckOperationalDataController extends MyBaseController {

    private final DataTemplateService dataTemplateService;

    private final ArchiveTypeService archiveTypeService;

    @Autowired
    private DahcFilePhotoServiceImpl dahcFilePhotoService;

    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;

    public CheckOperationalDataController(DataTemplateService dataTemplateService, ArchiveTypeService archiveTypeService) {
        this.dataTemplateService = dataTemplateService;
        this.archiveTypeService = archiveTypeService;
    }


    @ApiOperation(value = "分页查询")
    @ApiOperationSupport(order = 1)
    @GetMapping("/search")
    public CommonGridResult searchModelList(DataTemplateQuery dataTemplateQuery) {
        return dataTemplateService.selectPage(dataTemplateQuery);
    }

    @Log(title = "增加案卷或文件数据", businessType = BusinessType.INSERT)
    @ApiOperation("增加案件")
    @ApiOperationSupport(order = 3, ignoreParameters = {"^id", "^tableLevel", "^dahcDataTemplateList"})
    @PostMapping("/increaseCases")
    public CommonResult<Void> increaseCases(@RequestBody @Validated(value = AddGroup.class) DahcDataTemplateDto dahcDataTemplate) {
       return dataTemplateService.increaseCases(dahcDataTemplate);
    }


    @Log(title = "修改案卷或文件数据", businessType = BusinessType.UPDATE)
    @ApiOperationSupport(order = 4, ignoreParameters = {"^ids", "^tableLevel", "^dahcDataTemplateList"})
    @ApiOperation("修改")
    @PostMapping("/update")
    public CommonResult<Void> updateModel(@RequestBody DahcDataTemplateDto dahcDataTemplate){
        return dataTemplateService.updateModel(dahcDataTemplate);
    }



    @Log(title = "删除修改案卷或文件数据", businessType = BusinessType.DELETE)
    @ApiOperationSupport(order = 5, ignoreParameters = {
            "^attr", "^dahcDataTemplateList", "^desc", "^projectId", "^pid", "^tableLevel"
    })
    @ApiOperation("删除")
    @PostMapping("/deleteById")
    public CommonResult<Void> deleteByIdModel(@RequestBody @Validated DahcDataTemplateDto dahcDataTemplate) {
        return dataTemplateService.deleteByIdModel(dahcDataTemplate);
    }


    @Log(title = "批量删除修改案卷或文件数据", businessType = BusinessType.DELETE)
    @ApiOperationSupport(order = 6, ignoreParameters = {
            "attr", "dahcDataTemplateList", "^desc", "^projectId", "^pid", "^tableLevel"
    })
    @ApiOperation("批量删除")
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<Void> batchDeleteByIdsModel(@RequestBody @Validated DahcDataTemplateDto dahcDataTemplate) {
        dynamicSelectionTable(dahcDataTemplate.getArchiveTypeId(), dahcDataTemplate.getLevel());
        return toAxios(dataTemplateService.batchDeleteByIds(dahcDataTemplate.getIds()));
    }

    @Log(title = "案件上下移动", businessType = BusinessType.OTHER)
    @ApiOperation("案件上下移动")
    @ApiOperationSupport(order = 33)
    @PostMapping("/modifyTheSort")
    public CommonResult<?> modifyTheSort(@RequestBody DataTemplateUpAndDownDto dto) {
        dahcFilePhotoService.associateTheCurrentOperation(SecurityUtils.getUserId(),dto.getProcedureId());
        return dataTemplateService.modifyTheSort(dto.getId1(), dto.getId2(), String.valueOf(dto.getArchiveTypeId()),dto);
    }

    /**
     * 根据批次查询案卷数据
     *
     * @author MCY
     * @date 2023/3/30 14:58
     */
    @ApiOperationSupport(order = 24)
    @ApiOperation("根据批次查询案卷数据")
    @GetMapping("/queryCaseFileDataBasedOnBatches")
    public CommonGridResult queryCaseFileDataBasedOnBatches(QueryElectronicArchivesDossierLevelDto dossierLevelDto) {
        Page<Map<String, Object>> page = PageHelper.startPage(dossierLevelDto.getPageNum(), dossierLevelDto.getPageSize());
        List<Map<String, Object>> list = dataTemplateService.queryCaseFileDataBasedOnBatches(dossierLevelDto);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }


    public void dynamicSelectionTable(Long archiveTypeId, Integer level) {
        DahcArchiveType byId = archiveTypeService.findById(archiveTypeId);
        AssertUtil.isTrueServiceInvoke(byId != null, CommonStatus.ERROR, "未能找到对应档案");
        String tableLevelEn = level == 2 ? byId.getTableLevel2En() : byId.getTableLevel1En();
        CurrencyUtil.dynamicSelectionTableByTableName(tableLevelEn);
    }
}
