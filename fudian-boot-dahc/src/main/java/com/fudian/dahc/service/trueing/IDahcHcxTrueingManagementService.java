package com.fudian.dahc.service.trueing;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.pojo.query.ProcedureInspectQuery;


import java.util.List;;

/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-09
 */
public interface IDahcHcxTrueingManagementService extends IService<DahcHcxTrueingManagement>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public DahcHcxTrueingManagement selectDahcHcxTrueingManagementById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcHcxTrueingManagement> selectDahcHcxTrueingManagementList(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    public CommonResult insertDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    public CommonResult updateDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public CommonResult deleteDahcHcxTrueingManagementByIds(String[] ids);

    /**
     * 根据工序和项目查询核查项数据
     *
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/20 18:05
     */
    public CommonResult queryProcedureInspect(ProcedureInspectQuery procedureInspectQuery);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteDahcHcxTrueingManagementById(Long id);

    /**
     * 根据字典id删除关联的核查项数据
     * @return int
     * @author MCY
     * @date 2023/2/10 14:37
     */
    public Boolean deleteDictDataById(String trueingScopeStairId);


    /**
     *根据id集合查询数据
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/13 18:28
     */
    List<DahcHcxTrueingManagement> queryProcedureTrueing(List<String> trueingIds,Boolean b,String trueingId);

    /**
     *根据集合id查询数据
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/14 11:59
     */
    List<DahcHcxTrueingManagement> queryProcedureTrueingIds(List<String> ids);
}
