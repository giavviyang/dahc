package com.fudian.dahc.service.sys;

import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictType;

import java.util.List;
import java.util.Map;


/**
 * 字典类型Service接口
 *
 * @author fudian
 * @date 2023-02-08
 */
public interface IDahcSysDictTypeService
{
    /**
     * 查询字典类型
     *
     * @param dictId 字典类型主键
     * @return 字典类型
     */
    DahcSysDictType selectDahcSysDictTypeByDictId(Long dictId);

    /**
     * 查询字典类型列表
     *
     * @return 字典类型集合
     */
    CommonResult selectDahcSysDictTypeList();

    /**
     * 新增字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    int insertDahcSysDictType(DahcSysDictType dahcSysDictType);

    /**
     * 修改字典类型
     *
     * @param dahcSysDictType 字典类型
     * @return 结果
     */
    int updateDahcSysDictType(DahcSysDictType dahcSysDictType);

    /**
     * 批量删除字典类型
     *
     * @param dictIds 需要删除的字典类型主键集合
     * @return 结果
     */
    int deleteDahcSysDictTypeByDictIds(String dictIds);

    List<Map<String, String>> queryDictType();
    /**
     * 删除字典类型信息
     *
     * @param dictId 字典类型主键
     * @return 结果
     */
    int deleteDahcSysDictTypeByDictId(Long dictId);
}
