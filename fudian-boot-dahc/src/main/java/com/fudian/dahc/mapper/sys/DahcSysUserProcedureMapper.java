package com.fudian.dahc.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-16
 */
@Mapper
public interface DahcSysUserProcedureMapper extends BaseMapper<DahcSysUserProcedure> {

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
     * 删除【请填写功能名称】
     *
     * @param userId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcSysUserProcedureByUserId(String userId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDahcSysUserProcedureByUserIds(String[] userIds);


    /**
     *批量新增
     * @return int
     * @author MCY
     * @date 2023/2/16 16:57
     */
    int saveUserProcedure(@Param("userId")String userId,@Param("projectId")String projectId,@Param("procedureIds")List<String> procedureIds);
}
