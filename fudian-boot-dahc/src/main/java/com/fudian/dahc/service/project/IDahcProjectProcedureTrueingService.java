package com.fudian.dahc.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedureTrueing;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 *
 * @author fudian
 * @date 2023-02-13
 */
public interface IDahcProjectProcedureTrueingService extends IService<DahcProjectProcedureTrueing>
{
    /**
     *添加工序关联核查项
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/13 18:02
     */
    Boolean addProcedureTrueing(List<String> trueingIds,String procedureId);

    /**
     *根据id查询关联数据
     * @return java.util.List<java.lang.String>
     * @author MCY
     * @date 2023/2/13 18:23
     */
    List<String> queryProcedureTrueing(String procedureId);

    /**
     *根据工序id删除数据
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/14 10:44
     */
    Boolean deleteProcedureTrueing(String procedureId);

    /**
     *批量删除
     * @return java.lang.Boolean
     * @author MCY
     * @date 2023/2/14 11:17
     */
    Boolean deleteProcedureTrueingIds(List<String> procedureIds);
}
