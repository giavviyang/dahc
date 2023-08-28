package com.fudian.dahc.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-16
 */
public interface IDahcSysUserProcedureService extends IService<DahcSysUserProcedure>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param userId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DahcSysUserProcedure selectDahcSysUserProcedureByUserId(String userId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcSysUserProcedure> selectDahcSysUserProcedureList(DahcSysUserProcedure dahcSysUserProcedure);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 结果
     */
    public int insertDahcSysUserProcedure(DahcSysUserProcedure dahcSysUserProcedure);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcSysUserProcedure(DahcSysUserProcedure dahcSysUserProcedure);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param userIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteDahcSysUserProcedureByUserIds(String[] userIds);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param userId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcSysUserProcedureByUserId(String userId);
}
