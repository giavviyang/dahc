package com.fudian.dahc.controller.business;


import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import com.fudian.dahc.pojo.query.ModelQuery;
import com.fudian.dahc.service.business.ModelService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "业务管理-模板管理")
@ApiSupport(order = 12)
@RestController
@RequestMapping("/model")
public class ModelManagementController extends MyBaseController {

    private final ModelService modelService;

    public ModelManagementController(ModelService modelService) {
        this.modelService = modelService;
    }


    @ApiOperation("按条件查询模板")
    @ApiOperationSupport(order = 4)
    @GetMapping("/search")
    public CommonGridResult searchModelList(ModelQuery modelQuery) {
        List<DahcModel> list = modelService.searchPage(modelQuery);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @Log(title = "增加模板", businessType = BusinessType.INSERT)
    @ApiOperation("增加模板")
    @ApiOperationSupport(order = 5, ignoreParameters = "^id")
    @PostMapping("/add")
    public CommonResult<Void> addModel(@RequestBody @Validated(value = AddGroup.class) DahcModel model) {
        return toAxios(modelService.add(model));
    }

    @Log(title = "修改模板", businessType = BusinessType.UPDATE)
    @ApiOperation("修改模板")
    @ApiOperationSupport(order = 6, ignoreParameters = {
            "^pid", "^archiveTypeName", "^archiveTypeId", "^archiveTableName", "^dahcBusinessMapperList", "^dahcModelLv", "^archiveTableLevel"
    })
    @PostMapping("/edit")
    public CommonResult<Void> updateModel(@RequestBody @Validated(value = UpdateGroup.class) DahcModel model) {
        return toAxios(modelService.update(model));
    }


    @Log(title = "批量删除模板", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除模板")
    @ApiOperationSupport(order = 8)
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<Void> batchDeleteByIdsModel(@PathVariable("ids") List<Long> ids) {
        return toAxios(modelService.batchDeleteByIds(ids));
    }


    @ApiOperation("根据模板id获取数据映射关系")
    @ApiOperationSupport(order = 16)
    @GetMapping("/selectMapperByModel")
    public CommonGridResult selectMapperByModel(ModelQuery modelQuery) {
        Page<DahcModel> page = PageHelper.startPage(modelQuery.getPageNum(), modelQuery.getPageSize());
        List<DahcModel> list = modelService.selectMapperByModel(modelQuery.getId(), modelQuery.getArchiveTypeId());
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }


}





