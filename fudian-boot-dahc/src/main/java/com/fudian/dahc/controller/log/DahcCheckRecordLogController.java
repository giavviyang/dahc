package com.fudian.dahc.controller.log;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.log.DahcCheckRecordLog;
import com.fudian.dahc.service.log.IDahcCheckRecordLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.mongodb.CommandResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fudian.common.annotation.Log;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.core.domain.AjaxResult;
import com.fudian.common.enums.BusinessType;

import com.fudian.common.utils.poi.ExcelUtil;
import com.fudian.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author fudian
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/checkRecordLog")
public class DahcCheckRecordLogController extends BaseController {
    @Autowired
    private IDahcCheckRecordLogService dahcCheckRecordLogService;

    /**
     * 查询【请填写功能名称】列表
     */
    @ApiOperation("按条件查询日志")
    @ApiOperationSupport(order = 1)
    @GetMapping("/list")
    public CommonGridResult list(DahcCheckRecordLog dahcCheckRecordLog) {
        PageHelper.startPage(dahcCheckRecordLog.getPageNum(), dahcCheckRecordLog.getPageSize());
        List<DahcCheckRecordLog> list = dahcCheckRecordLogService.selectDahcCheckRecordLogList(dahcCheckRecordLog);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @ApiOperation("按条件导出日志")
    @ApiOperationSupport(order = 2)
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DahcCheckRecordLog dahcCheckRecordLog) {
        List<DahcCheckRecordLog> list = dahcCheckRecordLogService.selectDahcCheckRecordLogList(dahcCheckRecordLog);
        ExcelUtil<DahcCheckRecordLog> util = new ExcelUtil<DahcCheckRecordLog>(DahcCheckRecordLog.class);
        util.exportExcel(response, list, "核查工序操作日志");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @ApiOperation("按id查询日志")
    @ApiOperationSupport(order = 3)
    @GetMapping(value = "/getInfo/{id}")
    public CommonResult getInfo(@PathVariable("id") String id) {
        return CommonResult.success(dahcCheckRecordLogService.selectDahcCheckRecordLogById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @ApiOperation("新增日志")
    @ApiOperationSupport(order = 4)
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody DahcCheckRecordLog dahcCheckRecordLog) {
        return toAjax(dahcCheckRecordLogService.insertDahcCheckRecordLog(dahcCheckRecordLog));
    }

    /**
     * 修改【请填写功能名称】
     */
    @ApiOperation("修改日志")
    @ApiOperationSupport(order = 5)
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody DahcCheckRecordLog dahcCheckRecordLog) {
        return toAjax(dahcCheckRecordLogService.updateDahcCheckRecordLog(dahcCheckRecordLog));
    }

    /**
     * 删除【请填写功能名称】
     */
    @ApiOperation("按条件删除日志")
    @ApiOperationSupport(order = 6)
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping("/remove/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(dahcCheckRecordLogService.deleteDahcCheckRecordLogByIds(ids));
    }

    @Log(title = "清空日志", businessType = BusinessType.DELETE)
    @PostMapping("/removeAll")
    public AjaxResult removeAll() {
        return toAjax(dahcCheckRecordLogService.removeAll());
    }
}
