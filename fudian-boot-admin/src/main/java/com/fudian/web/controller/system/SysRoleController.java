package com.fudian.web.controller.system;

import com.fudian.common.annotation.Log;
import com.fudian.common.constant.UserConstants;
import com.fudian.common.core.controller.BaseController;
import com.fudian.common.core.domain.AjaxResult;
import com.fudian.common.core.domain.entity.SysDept;
import com.fudian.common.core.domain.entity.SysRole;
import com.fudian.common.core.domain.entity.SysUser;
import com.fudian.common.core.domain.model.LoginUser;
import com.fudian.common.core.page.TableDataInfo;
import com.fudian.common.enums.BusinessType;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.poi.ExcelUtil;
import com.fudian.framework.web.service.SysPermissionService;
import com.fudian.framework.web.service.TokenService;
import com.fudian.system.domain.SysUserRole;
import com.fudian.system.service.ISysDeptService;
import com.fudian.system.service.ISysRoleService;
import com.fudian.system.service.ISysUserService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色信息
 *
 * @author fudian
 */
@Api(tags = "系统管理—角色管理")
@ApiSupport(order = 41)
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;


    //@ApiOperation("分页查询")
    //@PreAuthorize("@ss.hasPermi('system:role:list')")
    //@GetMapping("/list")
    //public TableDataInfo list(SysRole role)
    //{
    //    startPage();
    //    List<SysRole> list = roleService.selectRoleList(role);
    //    return getDataTable(list);
    //}

    @ApiOperation("分页查询")
    @PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/list")
    public CommonResult list(SysRole role)
    {
        List<SysRole> list = roleService.selectRoleList(role);
        return CommonResult.success(list);
    }
    @ApiOperation("导出")
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:role:export')")
    @PostMapping("/export")
    public void export(HttpServletRequest httpServletRequest,HttpServletResponse response, SysRole role)
    {
        List<SysRole> list = roleService.selectRoleList(role);
        ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
        util.exportExcel(response, list, "角色数据");

    }

    @ApiOperation("用户关联角色分页查询-未选中数据")
    //@PreAuthorize("@ss.hasPermi('system:user:userRelevanceRoleList')")
    @GetMapping("/queryNotuserRelevanceRoleList/{userId}")
    public CommonResult queryNotuserRelevanceRoleList(@PathVariable("userId") Long userId)
    {

        return roleService.userRelevanceRoleList(userId);
    }

    @ApiOperation("用户关联角色分页查询-选中数据")
    //@PreAuthorize("@ss.hasPermi('system:user:queryUserRelevanceRoleList')")
    @GetMapping("/queryUserRelevanceRoleList/{userId}")
    public CommonResult queryUserRelevanceRoleList(@PathVariable("userId") Long userId)
    {
        return roleService.queryUserRelevanceRoleList(userId);
    }
    /**
     * 根据角色编号获取详细信息
     */
    @ApiOperation("条件查询")
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable Long roleId)
    {
        roleService.checkRoleDataScope(roleId);
        return success(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @ApiOperation("新增角色")
    //@PreAuthorize("@ss.hasPermi('system:role:add')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysRole role)
    {
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
/*        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
        {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }*/
        if (role.getRoleKey().equals("admin")) {
            return error("不允许添加权限字符为“admin”的角色权限");
        }
        role.setCreateBy(getUsername());
        return toAjax(roleService.insertRole(role));
    }

    /**
     *保存角色关联菜单权限
     * @return com.fudian.common.core.domain.AjaxResult
     * @author MCY
     * @date 2023/2/8 10:21
     */
    @ApiOperation("保存角色关联菜单权限")
    //@PreAuthorize("@ss.hasPermi('system:role:saveRoleMenu')")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/saveRoleMenu")
    public CommonResult saveRoleMenu(@RequestBody SysRole role)
    {
        return roleService.saveRoleMenu(role);
    }

    /**
     * 修改保存角色
     */
    @ApiOperation("修改角色")
    //@PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role)))
        {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        }
/*        else if (UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role)))
        {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }*/
        if (role.getRoleKey().equals("admin")) {
            return error("不允许修改权限字符为“admin”的角色权限");
        }
        role.setUpdateBy(getUsername());

        if (roleService.updateRole(role) > 0)
        {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (StringUtils.isNotNull(loginUser.getUser()) && !loginUser.getUser().isAdmin())
            {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return success();
        }
        return error("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    @ApiOperation("修改保存数据权限")
    //@PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxResult dataScope(@RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return toAjax(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    @ApiOperation("状态修改")
    //@PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysRole role)
    {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        role.setUpdateBy(getUsername());
        return toAjax(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    //@PreAuthorize("@ss.hasPermi('system:role:remove')")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds)
    {
        return toAjax(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @ApiOperation("获取角色选择框列表")
    //@PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        return success(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @ApiOperation("查询已分配用户角色列表")
    //@PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo allocatedList(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectAllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @ApiOperation("查询未分配用户角色列表")
    //@PreAuthorize("@ss.hasPermi('system:role:list')")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo unallocatedList(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权用户
     */
    @ApiOperation("取消授权用户")
    //@PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public AjaxResult cancelAuthUser(@RequestBody SysUserRole userRole)
    {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @ApiOperation("批量取消授权用户")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxResult cancelAuthUserAll(Long roleId, Long[] userIds)
    {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @ApiOperation("批量选择用户授权")
    @PreAuthorize("@ss.hasPermi('system:role:edit')")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxResult selectAuthUserAll(Long roleId, Long[] userIds)
    {
        roleService.checkRoleDataScope(roleId);
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * 获取对应角色部门树列表
     */
    @ApiIgnore
    @ApiOperation("获取对应角色部门树列表")
    @PreAuthorize("@ss.hasPermi('system:role:query')")
    @GetMapping(value = "/deptTree/{roleId}")
    public AjaxResult deptTree(@PathVariable("roleId") Long roleId)
    {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        ajax.put("depts", deptService.selectDeptTreeList(new SysDept()));
        return ajax;
    }
}
