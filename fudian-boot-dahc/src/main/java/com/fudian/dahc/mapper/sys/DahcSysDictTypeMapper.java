package com.fudian.dahc.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictType;

import java.util.List;

/**
 * 字典类型Mapper接口
 *
 * @author fudian
 * @date 2023-02-08
 */
public interface DahcSysDictTypeMapper extends BaseMapper<DahcSysDictType>
{
    /**
     * 查询字典类型
     *
     * @param dictId 字典类型主键
     * @return 字典类型
     */
    public DahcSysDictType selectDahcSysDictTypeByDictId(Long dictId);

    /**
     * 查询字典类型列表
     *
     * @param dahcSysDictType 字典类型
     * @return 字典类型集合
     */
    public List<DahcSysDictType> selectDahcSysDictTypeList(DahcSysDictType dahcSysDictType);

    /**
     * 新增字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    public int insertDahcSysDictType(DahcSysDictType dahcSysDictType);

    /**
     * 修改字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    public int updateDahcSysDictType(DahcSysDictType dahcSysDictType);

    /**
     * 删除字典类型
     *
     * @param dictId 字典类型主键
     * @return 结果
     */
    public int deleteDahcSysDictTypeByDictId(Long dictId);

    /**
     * 批量删除字典类型
     *
     * @param dictIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDahcSysDictTypeByDictIds(String dictIds);
}
