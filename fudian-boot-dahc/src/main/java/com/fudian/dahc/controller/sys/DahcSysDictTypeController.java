package com.fudian.dahc.controller.sys;

import java.util.List;
import java.util.Map;

import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictType;
import com.fudian.dahc.service.sys.IDahcSysDictTypeService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fudian.common.annotation.Log;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.core.domain.AjaxResult;
import com.fudian.common.enums.BusinessType;

/**
 * 字典类型Controller
 *
 * @author fudian
 * @date 2023-02-08
 */
@Api(tags = "系统管理-字典类型管理")
@ApiSupport(order = 47)
@RestController
@RequestMapping("/system/dictType")
public class DahcSysDictTypeController extends BaseController {
    @Autowired
    private IDahcSysDictTypeService dahcSysDictTypeService;

    /**
     * 查询字典类型列表
     */
    //@PreAuthorize("@ss.hasPermi('system:type:list')")
    @ApiOperation(value ="字典类型tree查询")
    @GetMapping("/list")
    public CommonResult list() {
        return dahcSysDictTypeService.selectDahcSysDictTypeList();
    }


    /**
     * 获取字典类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @ApiOperation(value ="获取字典类型详细信息")
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable("dictId") Long dictId) {
        return success(dahcSysDictTypeService.selectDahcSysDictTypeByDictId(dictId));
    }

    /**
     * 新增字典类型
     */
    //@PreAuthorize("@ss.hasPermi('system:type:add')")
    @ApiOperation(value = "新增字典类型")
    @Log(title = "新增字典类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DahcSysDictType dahcSysDictType) {
        return toAjax(dahcSysDictTypeService.insertDahcSysDictType(dahcSysDictType));
    }

    /**
     * 修改字典类型
     */
    @ApiOperation(value = "修改字典类型")
    //@PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "修改字典类型", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody DahcSysDictType dahcSysDictType) {
        return toAjax(dahcSysDictTypeService.updateDahcSysDictType(dahcSysDictType));
    }

    /**
     * 删除字典类型
     */
    @ApiOperation(value = "删除字典类型")
    //@PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "删除字典类型", businessType = BusinessType.DELETE)
    @PostMapping("/{dictId}")
    public AjaxResult remove(@PathVariable(value = "dictId") String dictId) {
        return toAjax(dahcSysDictTypeService.deleteDahcSysDictTypeByDictIds(dictId));
    }

    /**
     *用户表格类型转换
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author MCY
     * @date 2023/2/9 10:12
     */
//    @Log(title = "用户表格转换", businessType = BusinessType.DELETE)
    @ApiOperation("用户表格转换")
    @GetMapping("/queryDictType")
    public List<Map<String, String>> queryDictType() {
        return dahcSysDictTypeService.queryDictType();
    }
}
