package com.fudian.dahc.controller.project;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.annotation.Log;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.poi.ExcelUtil;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.pojo.query.ProcedureInspectQuery;
import com.fudian.dahc.service.trueing.IDahcHcxTrueingManagementService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【请填写功能名称】Controller
 *
 * @author fudian
 * @date 2023-02-09
 */
@Api(tags = "业务管理-核查项管理")
@RestController
@ApiSupport(order = 15)
@RequestMapping("/trueing/management")
public class DahcHcxTrueingManagementController extends BaseController {
    @Autowired
    private IDahcHcxTrueingManagementService dahcHcxTrueingManagementService;


    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation(value = "根据ID查询核查项数据")
    @ApiOperationSupport(order = 1)
    @GetMapping("/getOne/{id}")
    public CommonResult getOne(@PathVariable("id") String id) {
        DahcHcxTrueingManagement trueingManagement = dahcHcxTrueingManagementService.getOne(Wrappers.<DahcHcxTrueingManagement>lambdaQuery().eq(DahcHcxTrueingManagement::getId, id));
        return CommonResult.success(trueingManagement);
    }

    /**
     * 查询列表
     */
    @ApiOperation(value = "查询")
    @ApiOperationSupport(order = 2)
    @GetMapping("/list")
    public CommonGridResult list(DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        Page<DahcHcxTrueingManagement> page = PageHelper.startPage(dahcHcxTrueingManagement.getPageNum(), dahcHcxTrueingManagement.getPageSize());
        List<DahcHcxTrueingManagement> list = dahcHcxTrueingManagementService.selectDahcHcxTrueingManagementList(dahcHcxTrueingManagement);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 根据工序和项目查询核查项数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/20 18:05
     */
    @ApiOperation(value = "根据工序id查询核查项数据")
    @ApiOperationSupport(order = 3)
    @GetMapping("/queryProcedureInspect")
    public CommonResult queryProcedureInspect(ProcedureInspectQuery procedureInspectQuery) {
        return dahcHcxTrueingManagementService.queryProcedureInspect(procedureInspectQuery);
    }

    /**
     * 新增
     */
    @ApiOperation(value = "新增核查项")
    @Log(title = "新增核查项", businessType = BusinessType.INSERT)
    @ApiOperationSupport(order = 4,ignoreParameters = "^id")
    @PostMapping("/add")
    public CommonResult add(@RequestBody DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        return dahcHcxTrueingManagementService.insertDahcHcxTrueingManagement(dahcHcxTrueingManagement);
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改核查项")
    @ApiOperationSupport(order = 5)
    @Log(title = "修改核查项", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public CommonResult edit(@RequestBody DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        return dahcHcxTrueingManagementService.updateDahcHcxTrueingManagement(dahcHcxTrueingManagement);
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除核查项")
    @ApiOperationSupport(order = 6)
    @Log(title = "删除核查项", businessType = BusinessType.DELETE)
    @PostMapping("/remove/{ids}")
    public CommonResult remove(@PathVariable(value = "ids") String[] ids) {
        return dahcHcxTrueingManagementService.deleteDahcHcxTrueingManagementByIds(ids);
    }


    /**
     * 导出列表
     */
    @ApiOperation(value = "导出列表")
    @ApiOperationSupport(order = 10)
    @Log(title = "导出列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DahcHcxTrueingManagement dahcHcxTrueingManagement) {
        List<DahcHcxTrueingManagement> list = dahcHcxTrueingManagementService.selectDahcHcxTrueingManagementList(dahcHcxTrueingManagement);
        ExcelUtil<DahcHcxTrueingManagement> util = new ExcelUtil<>(DahcHcxTrueingManagement.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

}
