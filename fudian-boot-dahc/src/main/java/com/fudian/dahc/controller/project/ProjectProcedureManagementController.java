package com.fudian.dahc.controller.project;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.query.ProjectProcedureQuery;
import com.fudian.dahc.service.project.ProjectProcedureService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "项目管理-工序管理")
@ApiSupport(order = 17)
@RestController
@RequestMapping("/projectprocedure")
public class ProjectProcedureManagementController extends MyBaseController {

    @Autowired
    private ProjectProcedureService projectProcedureService;


    @ApiOperation("查询工序")
    @GetMapping("/query")
    @ResponseBody
    public List<DahcProjectProcedure> queryProjectList() {
        return projectProcedureService.list();
    }

    @Log(title = "按条件查询工序", businessType = BusinessType.OTHER)
    @ApiOperation("按条件查询工序")
    @ApiOperationSupport(order = 1)
    @GetMapping("/search")
    public CommonGridResult searchProcedureList(ProjectProcedureQuery projectProcedureQuery) {
        Page<DahcProjectProcedure> page = PageHelper.startPage(projectProcedureQuery.getPageNum(), projectProcedureQuery.getPageSize());
        List<DahcProjectProcedure> list = projectProcedureService.searchPage(projectProcedureQuery);
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    /**
     * 根据工序id查询核查项回显表格
     *
     * @return com.fudian.common.pojo.CommonGridResult
     * @author MCY
     * @date 2023/2/14 11:51
     */
    @ApiOperation(value = "根据工序id查询核查项回显表格", hidden = true)
    @GetMapping("/queryInspectTableList")
    public CommonGridResult queryInspectTableList(ProjectProcedureQuery projectProcedureQuery) {
        return projectProcedureService.queryInspectTableList(projectProcedureQuery);

    }


    /**
     * 编辑回显未选择的核查项数据
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/13 18:21
     */
    @ApiOperation(value = "编辑回显未选择的核查项数据", hidden = true)
    @GetMapping("/searchProcedureUnselectedList")
    public CommonResult<?> searchProcedureUnselectedList(@RequestParam Map<String, String> procedureId) {
        return projectProcedureService.searchProcedureUnselectedList(procedureId);
    }

    /**
     * 工序上下移
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 16:30
     */
    @Log(title = "工序上下移", businessType = BusinessType.OTHER)
    @ApiOperation("工序上下移")
    @ApiOperationSupport(order = 16)
    @PostMapping("/procedureUpAndDown")
    public CommonResult<?> procedureUpAndDown(@RequestParam Map<String, String> id) {
        return projectProcedureService.procedureUpAndDown(id);
    }


    @Log(title = "核查项上下移", businessType = BusinessType.OTHER)
    @ApiOperation("核查项上下移")
    @ApiOperationSupport(order = 15)
    @PostMapping("/procedureInspectUpAndDown")
    public CommonResult<?> procedureInspectUpAndDown(@RequestParam Map<String, String> trueingId) {
        return projectProcedureService.procedureInspectUpAndDown(trueingId);
    }


    /**
     * 新增工序，以及关联的核查项关系
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/13 17:14
     */
    @Log(title = "增加工序", businessType = BusinessType.INSERT)
    @ApiOperation("增加工序")
    @ApiOperationSupport(order = 2, ignoreParameters = "id")
    @PostMapping("/add")
    public CommonResult<?> addProcedure(@RequestBody DahcProjectProcedure dahcProjectProcedure) {
        return projectProcedureService.addProcedure(dahcProjectProcedure);
    }


    /**
     * 修改工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 10:41
     */
    @Log(title = "修改工序", businessType = BusinessType.UPDATE)
    @ApiOperation("修改工序")
    @ApiOperationSupport(order = 3, ignoreParameters = "")
    @PostMapping("/update")
    public CommonResult<?> updateProcedure(@RequestBody DahcProjectProcedure dahcProjectProcedure) {
        //return toAxios(projectProcedureService.update(dahcProjectProcedure));
        return projectProcedureService.updateProcedure(dahcProjectProcedure);
    }

    @Log(title = "删除工序", businessType = BusinessType.DELETE)
    @ApiOperation("删除工序")
    @ApiOperationSupport(order = 4)
    @PostMapping("/deleteById")
    public CommonResult<Void> deleteByIdProcedure(Long id) {
        return toAxios(projectProcedureService.deleteById(id));
    }


    /**
     * 批量删除工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 11:13
     */
    @Log(title = "批量删除工序", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除工序")
    @ApiOperationSupport(order = 5)
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<?> batchDeleteByIdsProcedure(@PathVariable("ids") List<String> ids) {
        //return toAxios(projectProcedureService.batchDeleteByIds(ids));
        return projectProcedureService.deleteProcedure(ids);
    }

    /**
     * 删除工序核查项
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 14:27
     */
    @Log(title = "删除工序核查项", businessType = BusinessType.DELETE)
    @ApiOperation("删除工序核查项")
    @ApiOperationSupport(order = 6)
    @PostMapping("/deleteProcedureTrueingId/{trueingId}")
    public CommonResult<?> deleteProcedureTrueingId(@PathVariable("trueingId") String trueingId) {
        return projectProcedureService.deleteProcedureTrueingId(trueingId);
    }

    /**
     * 查询用户关联工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/16 14:36
     */
    @ApiOperation("查询用户关联工序")
    @ApiOperationSupport(order = 10)
    @GetMapping("/queryUserProcedure")
    public CommonResult<?> queryUserProcedure(@RequestParam Map<String, String> userMessage) {
        return projectProcedureService.queryUserProcedure(userMessage);
    }


    /**
     * 保存用户关联工序
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/16 17:07
     */
    @ApiOperationSupport(order = 11)
    @Log(title = "保存用户关联工序", businessType = BusinessType.INSERT)
    @ApiOperation("保存用户关联工序")
    @PostMapping("/saveUserProcedure")
    public CommonResult<?> saveUserProcedure(@RequestBody Map<String, Object> userMessage) {
        return projectProcedureService.saveUserProcedure(userMessage);
    }


    /**
     * 根据项目和登录人员id查询工序数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 11:09
     */
    @ApiOperationSupport(order = 12)
    @ApiOperation("根据项目和登录人员id查询工序数据")
    @GetMapping("/queryUserProjectProcedure/{projectId}")
    public CommonResult<?> queryUserProjectProcedure(@PathVariable("projectId") String projectId) {

        return projectProcedureService.queryUserProjectProcedure(projectId);

    }

    @ApiOperationSupport(order = 13)
    @ApiOperation("根据工序id查询数据")
    @GetMapping("/getOne/{id}")
    public CommonResult<?> getOne(@PathVariable("id") String id) {
        return CommonResult.success(projectProcedureService.getOne(Wrappers.<DahcProjectProcedure>lambdaQuery().eq(DahcProjectProcedure::getId, id)));

    }

    static int getNumbers(String s) {
        String[] n = s.split(""); //array of strings
        StringBuilder f = new StringBuilder(); // buffer to store numbers

        for (String value : n) {
            if ((value.matches("[0-9]"))) {// validating numbers
                f.append(value); //appending
            } else {
                //parsing to int and returning value
                return Integer.parseInt(f.toString());
            }
        }
        return 0;
    }


}
