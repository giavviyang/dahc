package com.fudian.dahc.controller.business;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.annotation.RepeatSubmit;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.dto.DataTemplateUpAndDownDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataDto;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataVO;
import com.fudian.dahc.pojo.query.ArchiveTypeQuery;
import com.fudian.dahc.service.business.ArchiveTypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author wenbovo
 */

@Api(tags = "业务管理-档案类型管理")
@ApiSupport(order = 11)
@RestController
@RequestMapping("/archiveType")
public class ArchiveTypeManagementController extends MyBaseController {

    private final ArchiveTypeService archiveTypeService;

    public ArchiveTypeManagementController(ArchiveTypeService archiveTypeService) {
        this.archiveTypeService = archiveTypeService;
    }


    @ApiOperation("条件查询档案类型")
    @ApiOperationSupport(order = 1, includeParameters = {"archiveTypeName", "archiveTypeDesc", "pageNum", "pageSize"})
    @GetMapping("/search")
    public CommonGridResult searchArchiveTypeList(ArchiveTypeQuery archiveTypeQuery) {
        Page<DahcArchiveType> page = PageHelper.startPage(archiveTypeQuery.getPageNum(), archiveTypeQuery.getPageSize());
        List<DahcArchiveType> list = archiveTypeService.searchPage(archiveTypeQuery);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @RepeatSubmit
    //@PreAuthorize("@ss.hasPermi('business:archivesType:add')")
    @Log(title = "增加档案类型", businessType = BusinessType.INSERT)
    @ApiOperation("增加档案类型")
    @ApiOperationSupport(order = 2, ignoreParameters = "^id")
    @PostMapping("/add")
    public CommonResult<Void> addArchiveType(@RequestBody @Validated(value = AddGroup.class) DahcArchiveType dahcArchiveType) {
        return toAxios(archiveTypeService.add(dahcArchiveType));
    }

    @RepeatSubmit
    //@PreAuthorize("@ss.hasPermi('business:archivesType:edit')")
    @Log(title = "修改档案类型", businessType = BusinessType.UPDATE)
    @ApiOperation("修改档案类型")
    @ApiOperationSupport(order = 3, ignoreParameters = "")
    @PostMapping("/edit")
    public CommonResult<Void> updateArchiveType(@RequestBody @Validated(value = UpdateGroup.class) DahcArchiveType dahcArchiveType) {
        return toAxios(archiveTypeService.update(dahcArchiveType));
    }


    @RepeatSubmit
    //@PreAuthorize("@ss.hasPermi('business:archivesType:delete')")
    @Log(title = "批量删除档案类型", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除档案类型")
    @ApiOperationSupport(order = 4)
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<?> batchDeleteByIdsArchiveType(@PathVariable("ids") List<Long> ids) {
        return archiveTypeService.batchDeleteByIdsArchiveType(ids);
    }


    @ApiOperation("查询档案类型和其元数据")
    @ApiOperationSupport(order = 11, includeParameters = {"id", "level", "pageNum", "pageSize"})
    @GetMapping("/selectListAndMetadata")
    public CommonGridResult selectListAndMetadata(ArchiveTypeQuery archiveTypeQuery) {
        Page<DahcArchiveTypeAndMetadataVo> page = PageHelper.startPage(archiveTypeQuery.getPageNum(), archiveTypeQuery.getPageSize());
        List<DahcArchiveTypeAndMetadataVo> dahcArchiveTypeList = archiveTypeService.selectListAndMetadataById(archiveTypeQuery.getLevel(), archiveTypeQuery.getId());
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), dahcArchiveTypeList);
    }

    @RepeatSubmit
    @ApiOperation("批量设置文档关联元数据")
    @Log(title = "设置文档关联元数据", businessType = BusinessType.INSERT)
    @ApiOperationSupport(order = 12, ignoreParameters = "")
    @PostMapping("/insertListAndMetadataList")
    public CommonResult<Void> insertListAndMetadataList(@RequestBody @Validated ArchiveAndMetadataVO archiveAndMetadataVO) {
        return toAxios(archiveTypeService.insertMetadataList(archiveAndMetadataVO));
    }

    /**
     * 设置映射--回显表格数据
     *
     * @author MCY
     */
    @ApiOperation(value = "回显表格数据", hidden = true)
    @ApiOperationSupport(order = 13, ignoreParameters = {"metadataId", "archiveTypeLevel"})
    @PostMapping("/queryArchiveAndMetadata")
    public CommonResult<List<ArchiveAndMetadataDto>> queryArchiveAndMetadata(@RequestBody ArchiveAndMetadataDto archiveAndMetadataDto) {
        return archiveTypeService.queryArchiveAndMetadata(archiveAndMetadataDto);
    }


    @ApiOperation("查询案卷级及其文件级模板")
    @ApiOperationSupport(order = 21)
    @PostMapping("/selecctModelByArchiveType")
    public CommonResult<?> selectModelByArchiveType(String id) {
        return archiveTypeService.selectModelByArchiveType(id);
    }

    /**
     * 根据档案类型id获取元数据 案卷 档号字段为那个数据库字段
     *
     * @author MCY
     */
    @ApiOperation(value = "根据档案类型id查询档案主键")
    @ApiOperationSupport(order = 22)
    @GetMapping("/theFileNumberFieldIsThatDatabaseField/{archiveTypeId}")
    public CommonResult<?> theFileNumberFieldIsThatDatabaseField(@PathVariable("archiveTypeId") String archiveTypeId) {
        return archiveTypeService.theFileNumberFieldIsThatDatabaseField(archiveTypeId);
    }

    @ApiOperation(value = "案件档号")
    @ApiOperationSupport(order = 22)
    @GetMapping("/caseFileNumberAttr/{archiveTypeId}")
    public CommonResult<?> caseFileNumberAttr(@PathVariable("archiveTypeId") String archiveTypeId) {
        return archiveTypeService.caseFileNumberAttr(archiveTypeId);
    }

    @ApiOperation(value = "查询文件级数据的主键attr")
    @ApiOperationSupport(order = 23)
    @GetMapping("/obtainCaseDataAttr")
    public CommonResult<?> obtainCaseDataAttr(DataTemplateUpAndDownDto dto) {
        DahcArchiveType archiveType = archiveTypeService.getOne(Wrappers.<DahcArchiveType>lambdaQuery().eq(DahcArchiveType::getId, dto.getArchiveTypeId()));
        String dataAttr = archiveTypeService.queryProfileDefaultFieldBindingAttr(String.valueOf(dto.getArchiveTypeId()), archiveType.getTableLevel2En(), dto.getMetadataName());
        return CommonResult.success(dataAttr);
    }


    /**
     * 下拉框类型转换
     *
     * @author MCY
     */
    @ApiOperation(value = "下拉框类型转换", hidden = true)
    @GetMapping("/queryArchiveTransition")
    public CommonResult<List<Map<String, String>>> queryArchiveTransition() {
        return archiveTypeService.queryArchiveTransition();
    }
}

