package com.fudian.dahc.mapper.trueingManage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.query.ProcedureInspectQuery;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import com.fudian.dahc.pojo.entity.trueingManage.ProcedureInspectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-09
 */
@Mapper
public interface DahcHcxTrueingManagementMapper extends BaseMapper<DahcHcxTrueingManagement>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    DahcHcxTrueingManagement selectDahcHcxTrueingManagementById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<DahcHcxTrueingManagement> selectDahcHcxTrueingManagementList(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    List<DahcHcxTrueingManagement> selectTrueingScopeStairList(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    List<DahcHcxTrueingManagement> queryProcedureTrueingIds(@Param("procedureId") String procedureId);

    /**
     * 新增【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    int insertDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    /**
     * 修改【请填写功能名称】
     *
     * @param dahcHcxTrueingManagement 【请填写功能名称】
     * @return 结果
     */
    int updateDahcHcxTrueingManagement(DahcHcxTrueingManagement dahcHcxTrueingManagement);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    int deleteDahcHcxTrueingManagementById(Long id);

    int deleteDictDataById(String trueingScopeStair);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteDahcHcxTrueingManagementByIds(String[] ids);


    List<ProcedureInspectDto> queryProcedureInspect(ProcedureInspectQuery procedureInspectQuery);

}
