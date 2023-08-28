package com.fudian.dahc.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-08
 */
public interface DahcSysDictDataMapper extends BaseMapper<DahcSysDictData>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DahcSysDictData selectDahcSysDictDataById(String id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcSysDictData> selectDahcSysDictDataList(DahcSysDictData dahcSysDictData);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    public int insertDahcSysDictData(DahcSysDictData dahcSysDictData);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcSysDictData(DahcSysDictData dahcSysDictData);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcSysDictDataById(String id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDahcSysDictDataByIds(String[] ids);

    @Select("SELECT\n" +
            "\tcount(*)  \n" +
            "FROM\n" +
            "\t`sys_user_role` ur\n" +
            "\tINNER JOIN sys_role r ON r.role_id = ur.role_id \n" +
            "WHERE\n" +
            "\tuser_id = #{userId} \n" +
            "\tAND r.role_key = 'manager'")
    public int getRolePermissions(@Param("userId") Long userId);
}
