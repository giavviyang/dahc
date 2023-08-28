package com.fudian.dahc.controller.business;


import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.annotation.RepeatSubmit;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import com.fudian.dahc.pojo.query.MetadataQuery;
import com.fudian.dahc.service.business.MetadataService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author fudian
 */
@Api(tags = "业务管理-元数据管理")
@ApiSupport(order = 10)
@RestController
@RequestMapping("/metadata")
public class MetadataManagementController extends MyBaseController {

    @Autowired
    private MetadataService metadataService;

    @ApiOperation("按条件查询元数据")
    @ApiOperationSupport(order = 3, includeParameters = {"metadataName", "metadataType", "metadataDesc", "pageNum", "pageSize"})
    @GetMapping("/search")
    public CommonGridResult searchMetadataList(MetadataQuery metadataQuery) {
        Page<DahcMetadata> page = PageHelper.startPage(metadataQuery.getPageNum(), metadataQuery.getPageSize());
        List<DahcMetadata> list = metadataService.searchPage(metadataQuery);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    //@PreAuthorize("@ss.hasPermi('business:metaData:add')")
    @Log(title = "增加元数据", businessType = BusinessType.INSERT)
    @ApiOperation("增加元数据")
    @ApiOperationSupport(order = 1, ignoreParameters = {"^id", "update", "create", "defaultField"})
    @PostMapping("/add")
    public CommonResult<Void> addMetadata(@RequestBody @Validated(value = AddGroup.class) DahcMetadata dahcMetadata) {
        return toAxios(metadataService.add(dahcMetadata));
    }

    //@PreAuthorize("@ss.hasPermi('business:metaData:edit')")
    @Log(title = "修改元数据", businessType = BusinessType.UPDATE)
    @ApiOperation("修改元数据")
    @ApiOperationSupport(order = 2, ignoreParameters = {"create", "update", "defaultField"})
    @PostMapping("/edit")
    public CommonResult<?> updateMetadata(@RequestBody @Validated(value = UpdateGroup.class) DahcMetadata dahcMetadata) {
        return metadataService.updateMetadata(dahcMetadata);
    }


    @RepeatSubmit
    //@PreAuthorize("@ss.hasPermi('business:metaData:delete')")
    @Log(title = "批量删除元数据", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除元数据")
    @ApiOperationSupport(order = 4, includeParameters = {"ids"})
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<?> batchDeleteByIdsMetadata(@PathVariable("ids") List<Long> ids) {
        return metadataService.deleteMetadataInBulk(ids);
    }

    /**
     * 下拉框类型转换
     *
     * @author MCY
     */
    @ApiOperation(value = "下拉框类型转换", hidden = true)
    @GetMapping("/queryMetadataSelect")
    public List<Map<String, Object>> queryMetadataSelect() {
        return metadataService.queryMetadataSelect();
    }

}

