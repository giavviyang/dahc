package com.fudian.dahc.controller.project;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fudian.common.annotation.Anonymous;
import com.fudian.common.annotation.Log;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.controller.base.MyBaseController;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import com.fudian.dahc.pojo.query.ProjectTableQuery;
import com.fudian.dahc.service.project.ProjectTableService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Api(tags = "项目管理-项目初始化")
@ApiSupport(order = 16)
@RestController
@RequestMapping("/projecttable")
public class ProjectTableManagementController extends MyBaseController {

    @Autowired
    private ProjectTableService projectTableService;

    //    @ApiOperation("查询项目")
//    @GetMapping("/query")
//    @ResponseBody
//    public List<DahcProjectTable> queryProjectList() {
//        return projectTableService.list();
//    }
    @Log(title = "按条件查询项目", businessType = BusinessType.OTHER)
    @ApiOperation("按条件查询项目")
    @ApiOperationSupport(order = 1, ignoreParameters = {""})
    @GetMapping("/search")
    public CommonGridResult searchProcedureList(ProjectTableQuery projectTableQuery) {
        PageHelper.startPage(projectTableQuery.getPageNum(), projectTableQuery.getPageSize());
        List<DahcProjectTable> list = projectTableService.searchPage(projectTableQuery);
        Page page = (Page) list;
        return CommonGridResult.initData(page.getPageNum(), page.getPageSize(), page.getTotal(), list);
    }

    @Log(title = "增加项目", businessType = BusinessType.INSERT)
    @ApiOperation("增加项目")
    @ApiOperationSupport(order = 2, ignoreParameters = {""})
    @PostMapping("/add")
    public CommonResult<Void> addProjectTable(@RequestBody DahcProjectTable dahcProjectTable) {
        return toAxios(projectTableService.add(dahcProjectTable));
    }

    @Log(title = "修改项目", businessType = BusinessType.UPDATE)
    @ApiOperation("修改项目")
    @ApiOperationSupport(order = 3, ignoreParameters = {"^id"})
    @PostMapping("/update")
    public CommonResult<Void> updateProjectTable(@RequestBody DahcProjectTable dahcProjectTable) {
        return toAxios(projectTableService.update(dahcProjectTable));
    }

    @Log(title = "删除项目", businessType = BusinessType.DELETE)
    @ApiOperation("删除项目")
    @ApiOperationSupport(order = 4, ignoreParameters = {""})
    @PostMapping("/deleteById")
    public CommonResult<Void> deleteByIdProjectTable(Long id) {

        return toAxios(projectTableService.deleteById(id));
    }

    @Log(title = "批量删除项目", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除项目")
    @ApiOperationSupport(order = 5, ignoreParameters = {""})
    @PostMapping("/batchDeleteByIds/{ids}")
    public CommonResult<Void> batchDeleteByIdsProjectTable(@PathVariable("ids") List<Long> ids) {
        return projectTableService.batchDeleteByIdsProjectTable(ids);
        //return toAxios(projectTableService.batchDeleteByIds(ids));
    }

    /**
     * 开始核查
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/20 11:47
     */
    @Log(title = "开始核查", businessType = BusinessType.OTHER)
    @ApiOperation("开始核查")
    @ApiOperationSupport(order = 6, ignoreParameters = {""})
    @PostMapping("/startInspect")
    public CommonResult<Void> startInspect(@RequestBody DahcProjectTable dahcProjectTable) {
        return projectTableService.startInspect(dahcProjectTable);
    }
    /**
     * 结束核查
     *
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/20 11:47
     */
    @Log(title = "结束核查", businessType = BusinessType.OTHER)
    @ApiOperation("结束核查")
    @ApiOperationSupport(order = 7, ignoreParameters = {""})
    @PostMapping("/endOfVerification")
    public CommonResult<Void> endOfVerification(@RequestBody DahcProjectTable dahcProjectTable) {
        return projectTableService.endOfVerification(dahcProjectTable);
    }
    /**
     * 用于项目下拉框
     *
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @author MCY
     * @date 2023/2/16 15:44
     */
    @ApiOperation(value = "表格字段字典转换", hidden = true)
    @GetMapping("/queryProjectPullDown")
    public CommonResult queryProjectPullDown() {
        return projectTableService.queryProjectPullDown();
    }

    /**
     * 根据用户查询项目数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 10:15
     */
    @ApiOperation("根据用户查询项目数据")
    @GetMapping("/queryUserProject")
    public CommonResult queryUserProject() {
        return projectTableService.queryUserProject();
    }

    /**
     * 根据项目id查询数据
     *
     * @return com.fudian.dahc.common.CommonResul
     * @author MCY
     * @date 2023/2/28 15:19
     */
    @ApiOperation("根据项目id查询数据")
    @GetMapping("/getOne/{id}")
    public CommonResult getOne(@PathVariable("id") String id) {

        return CommonResult.success(projectTableService.getOne(Wrappers.<DahcProjectTable>lambdaQuery().eq(DahcProjectTable::getId, id)));
    }


    /**
     * 查询存疑tree树
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/10 10:32
     */
    @ApiOperation(value = "查询存疑页面项目tree树", hidden = true)
    @GetMapping("/queryTheProjectOperationTree")
    public CommonResult queryTheProjectOperationTree() {
        return projectTableService.queryTheProjectOperationTree();

    }


    /**
     * 查询个人管理项目tree树
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/3/17 10:37
     */
    @ApiOperation(value = "查询个人管理项目tree树", hidden = true)
    @GetMapping("/queryThePersonalManagementProjectTree")
    public CommonResult queryThePersonalManagementProjectTree() {
        return projectTableService.queryThePersonalManagementProjectTree();
    }


    @ApiOperation(value = "查询个人任务tree树", hidden = true)
    @GetMapping("/queryThePersonalTaskTree")
    public CommonResult queryThePersonalTaskTree() {
        return projectTableService.queryThePersonalTaskTree();
    }


    /**
     * 查询项目批次 tree树
     *
     * @return
     * @author MCY
     * @date 2023/3/30 12:25
     */
    @ApiOperation(value = "查询项目批次 tree树", hidden = true)
    @GetMapping("/queryArchiveBatchData")
    public CommonResult queryArchiveBatchData(DahcProjectTable dahcProjectTable) {
        return projectTableService.queryArchiveBatchData(dahcProjectTable);
    }
}

