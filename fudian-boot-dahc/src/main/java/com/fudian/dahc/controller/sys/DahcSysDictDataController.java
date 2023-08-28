package com.fudian.dahc.controller.sys;

import java.util.List;
import java.util.Map;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import com.fudian.dahc.service.sys.IDahcSysDictDataService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fudian.common.annotation.Log;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.core.domain.AjaxResult;
import com.fudian.common.enums.BusinessType;

/**
 * 【请填写功能名称】Controller
 *
 * @author fudian
 * @date 2023-02-08
 */
@Api(tags = "系统管理-字典数据管理")
@ApiSupport(order = 48)
@RestController
@RequestMapping("/system/dictData")
public class DahcSysDictDataController extends BaseController {
    @Autowired
    private IDahcSysDictDataService dahcSysDictDataService;

    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation(value = "分页查询")
    //@PreAuthorize("@ss.hasPermi('system:data:list')")
    @GetMapping("/list")
    public CommonGridResult list(DahcSysDictData dahcSysDictData) {
        return dahcSysDictDataService.selectDahcSysDictDataList(dahcSysDictData);
    }

    /**查询全部数据
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/2/20 14:44
     */
    @ApiOperation(value = "分页查询字典数据")
    //@PreAuthorize("@ss.hasPermi('system:data:list')")
    @GetMapping("/listAll")
    public CommonGridResult listAll(DahcSysDictData dahcSysDictData) {
        return dahcSysDictDataService.selectDahcSysDictDataListAll(dahcSysDictData);
    }

    /**
     * 核查项tree查询
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/9 10:44
     */
    @ApiOperation(value = "核查项tree查询")
    @GetMapping("/queryInspectTree/{dictTypeId}")
    public CommonResult queryInspectTree(@PathVariable(value = "dictTypeId") String dictTypeId) {
        return dahcSysDictDataService.queryInspectTree(dictTypeId);
    }


    /**
     * 获取详细信息
     */
    @ApiOperation(value = "获取字典数据详细信息")
    //@PreAuthorize("@ss.hasPermi('system:data:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(dahcSysDictDataService.selectDahcSysDictDataById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @ApiOperation(value = "新增字典数据")
    //@PreAuthorize("@ss.hasPermi('system:data:add')")
    @Log(title = "新增字典数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DahcSysDictData dahcSysDictData) {
        return toAjax(dahcSysDictDataService.insertDahcSysDictData(dahcSysDictData));
    }

    @ApiOperation(value = "核查管理-新增字典数据")
    //@PreAuthorize("@ss.hasPermi('system:data:add')")
    @Log(title = "新增字典数据", businessType = BusinessType.INSERT)
    @PostMapping("/auditManagementAdd")
    public AjaxResult auditManagementAdd(@RequestBody DahcSysDictData dahcSysDictData) {
        return toAjax(dahcSysDictDataService.auditManagementAdd(dahcSysDictData));
    }

    /**
     * 修改【请填写功能名称】
     */
    //@PreAuthorize("@ss.hasPermi('system:data:edit')")
    @ApiOperation("修改字典数据")
    @Log(title = "修改字典数据", businessType = BusinessType.UPDATE)
    @PostMapping("/upadte")
    public AjaxResult edit(@RequestBody DahcSysDictData dahcSysDictData) {
        return toAjax(dahcSysDictDataService.updateDahcSysDictData(dahcSysDictData));
    }

    /**
     * 删除字典数据
     */
    //@PreAuthorize("@ss.hasPermi('system:data:remove')")
    @ApiOperation("删除字典数据")
    @Log(title = " 删除字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public CommonResult remove(@PathVariable String[] ids) {
        return dahcSysDictDataService.deleteDahcSysDictDataByIds(ids);
    }

    /**
     * 表格字段字典转换
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author MCY
     * @date 2023/2/10 10:42
     */
//    @Log(title = "表格字段字典转换", businessType = BusinessType.DELETE)
    @ApiOperation("表格字段字典转换")
    @GetMapping("/queryDictDataTransition")
    public List<Map<String, String>> queryDictDataTransition() {
        return dahcSysDictDataService.queryDictDataTransition();
    }

//    @Log(title = "根据id表格字段字典转换", businessType = BusinessType.DELETE)
    @ApiOperation("根据id表格字段字典转换")
    @GetMapping("/queryDictDataTransitionId/{dictTypeId}")
    public List<Map<String, String>> queryDictDataTransitionId(@PathVariable(value = "dictTypeId") String dictTypeId) {
        return dahcSysDictDataService.queryDictDataTransitionId(dictTypeId);
    }

    /**
     *根据工序的字典id获取字典属性
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 13:56
     */
    @ApiOperation("根据工序的字典id获取字典属性")
    @GetMapping("/queryProcedureData/{dictDataId}")
    public CommonResult queryProcedureData(@PathVariable(value = "dictDataId") String dictDataId) {
        return dahcSysDictDataService.queryProcedureData(dictDataId);
    }





}
