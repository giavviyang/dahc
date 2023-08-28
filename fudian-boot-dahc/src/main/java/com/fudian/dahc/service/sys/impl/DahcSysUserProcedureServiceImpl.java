package com.fudian.dahc.service.sys.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.dahc.mapper.sys.DahcSysUserProcedureMapper;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import com.fudian.dahc.service.sys.IDahcSysUserProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author fudian
 * @date 2023-02-16
 */
@Service
public class DahcSysUserProcedureServiceImpl extends ServiceImpl<DahcSysUserProcedureMapper,DahcSysUserProcedure> implements IDahcSysUserProcedureService
{
    @Autowired
    private DahcSysUserProcedureMapper dahcSysUserProcedureMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param userId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public DahcSysUserProcedure selectDahcSysUserProcedureByUserId(String userId)
    {
        return dahcSysUserProcedureMapper.selectDahcSysUserProcedureByUserId(userId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DahcSysUserProcedure> selectDahcSysUserProcedureList(DahcSysUserProcedure dahcSysUserProcedure)
    {
        return dahcSysUserProcedureMapper.selectDahcSysUserProcedureList(dahcSysUserProcedure);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDahcSysUserProcedure(DahcSysUserProcedure dahcSysUserProcedure)
    {
        return dahcSysUserProcedureMapper.insertDahcSysUserProcedure(dahcSysUserProcedure);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcSysUserProcedure 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDahcSysUserProcedure(DahcSysUserProcedure dahcSysUserProcedure)
    {
        return dahcSysUserProcedureMapper.updateDahcSysUserProcedure(dahcSysUserProcedure);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param userIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcSysUserProcedureByUserIds(String[] userIds)
    {
        return dahcSysUserProcedureMapper.deleteDahcSysUserProcedureByUserIds(userIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param userId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteDahcSysUserProcedureByUserId(String userId)
    {
        return dahcSysUserProcedureMapper.deleteDahcSysUserProcedureByUserId(userId);
    }
}
