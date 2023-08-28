package com.fudian.dahc.service.project;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.dahc.common.CommonResult;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.query.ProjectProcedureQuery;
import com.fudian.dahc.service.base.MyBaseService;

import java.util.List;
import java.util.Map;

/**
 * 2023/2/7
 */
public interface ProjectProcedureService extends MyBaseService<DahcProjectProcedure> {


    List<DahcProjectProcedure> searchPage(ProjectProcedureQuery projectProcedureQuery);

    CommonResult addProcedure(DahcProjectProcedure dahcProjectProcedure);
    CommonResult updateProcedure(DahcProjectProcedure dahcProjectProcedure);
    CommonResult deleteProcedure(List<String> ids);
    CommonResult deleteProcedureTrueingId(String trueingId);
    /**
     *查询用户关联工序
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/16 14:36
     */
    CommonResult queryUserProcedure(Map<String, String> userMessage);
    CommonResult saveUserProcedure(Map<String, Object> userMessage);

    /**
     *根据id查询核查项数据
     * @return java.util.List<com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement>
     * @author MCY
     * @date 2023/2/14 11:54
     */
    CommonGridResult queryInspectTableList(ProjectProcedureQuery projectProcedureQuery);

    /**
     *编辑回显未选择的核查项数据
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/13 18:21
     */
    CommonResult searchProcedureUnselectedList(Map<String,String> procedureId);
    /**
     *工序上下移
     * @return com.fudian.dahc.common.CommonResult<java.lang.Void>
     * @author MCY
     * @date 2023/2/14 16:30
     */
    CommonResult procedureUpAndDown(Map<String,String> id);
    CommonResult procedureInspectUpAndDown(Map<String,String> trueingId);

    /**
     根据项目和登录人员id查询工序数据
     * @return com.fudian.dahc.common.CommonResult
     * @author MCY
     * @date 2023/2/24 11:09
     */
    CommonResult queryUserProjectProcedure(String projectId);


    DahcProjectProcedure queryTheFirstOperation(String projectId);

}
