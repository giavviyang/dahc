package com.fudian.dahc.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.sys.DahcSysDictData;

import java.util.List;
import java.util.Map;


/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-08
 */
public interface IDahcSysDictDataService extends IService<DahcSysDictData>
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
    public CommonGridResult selectDahcSysDictDataList(DahcSysDictData dahcSysDictData);
    public CommonGridResult selectDahcSysDictDataListAll(DahcSysDictData dahcSysDictData);
    /**
     *核查项tree查询
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/9 10:44
     */
    public CommonResult queryInspectTree(String dictTypeId);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    public int insertDahcSysDictData(DahcSysDictData dahcSysDictData);
    public int auditManagementAdd(DahcSysDictData dahcSysDictData);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcSysDictData 【请填写功能名称】
     * @return 结果
     */
    public int updateDahcSysDictData(DahcSysDictData dahcSysDictData);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public CommonResult deleteDahcSysDictDataByIds(String[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcSysDictDataById(String id);

    List<Map<String, String>> queryDictDataTransition();
    List<Map<String, String>> queryDictDataTransitionId(String dictTypeId);
    /**
     *根据工序的字典id获取字典属性
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 13:56
     */
    CommonResult queryProcedureData(String dictDataId);


    Boolean getRolePermissions(Long userId);
}
