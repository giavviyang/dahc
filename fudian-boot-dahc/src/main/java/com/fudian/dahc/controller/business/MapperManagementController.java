package com.fudian.dahc.controller.business;


import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.annotation.RepeatSubmit;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.dto.DahcModelAndMetadataDto;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.query.MapperQuery;
import com.fudian.dahc.service.business.MapperService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "业务管理-映射管理")
@ApiSupport(order = 13)
@RestController
@RequestMapping("/mapper")
public class MapperManagementController extends MyBaseController {

    private final MapperService mapperService;

    public MapperManagementController(MapperService mapperService) {
        this.mapperService = mapperService;
    }

    @ApiOperation("按条件查询映射")
    @GetMapping("/search")
    public CommonGridResult searchDahcMapperList(MapperQuery mapperQuery) {
        Page<DahcBusinessMapper> page = PageHelper.startPage(mapperQuery.getPageNum(), mapperQuery.getPageSize());
        List<DahcBusinessMapper> list = mapperService.searchPage(mapperQuery);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @RepeatSubmit
    @Log(title = "批量增加excel映射关系", businessType = BusinessType.INSERT)
    @ApiOperation("批量增加映射")
    @ApiOperationSupport(order = 5, ignoreParameters = {
            "dahcBusinessMapperList.id", "dahcModelLv", "archiveTypeId", "archiveTypeName", "archiveTableName", "archiveTableLevel"
    })
    @PostMapping("/bulkAdditions")
    public CommonResult<?> bulkAdditions(@RequestBody @Validated(value = AddGroup.class) DahcModelAndMetadataDto andMetadataDto) {
        return toAxios(mapperService.bulkAdditions(andMetadataDto));
    }

    @RepeatSubmit
    @Log(title = "批量删除excel映射关系", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除映射")
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<Void> batchDeleteByIdsDahcMapper(@PathVariable("ids") List<Long> ids) {
        return toAxios(mapperService.batchDeleteByIds(ids));
    }

}

