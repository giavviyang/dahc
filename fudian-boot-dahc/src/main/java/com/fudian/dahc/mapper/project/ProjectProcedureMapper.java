package com.fudian.dahc.mapper.project;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.entity.project.DahcProjectProcedure;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 2023/2/10
 */
@Mapper
public interface ProjectProcedureMapper extends MyBaseMapper<DahcProjectProcedure> {


    List<DahcProjectProcedure> selectProcedureAndT(@Param("pid") Long id);

    int modifyTheSort(@Param("id1") String id1, @Param("id2") String id2);

    List<DahcProjectProcedure> queryUserProjectProcedure(@Param("projectId") String projectId, @Param("userId") String userId);


    @Select("SELECT * FROM dahc_project_procedure p \n" +
            "left join dahc_record_procedure_files f on p.id = f.procedure_id\n" +
            "WHERE \n" +
            "f.is_procedure_inspect = '3'\n" +
            "group by f.procedure_id\n" +
            "order by p.sort")
    List<DahcProjectProcedure> theProcessOfQueryingForSuspiciousData();
    @Select({"<script>  " +
            "SELECT* FROM dahc_project_procedure " +
            "WHERE (sort=" +
            "(SELECT min(sort) FROM dahc_project_procedure" +
            " WHERE project_id =#{projectId}))" +
            " and project_id =#{projectId}" +
            "</script>"})
    DahcProjectProcedure queryTheFirstOperation(@Param("projectId") String projectId);

    List<DahcProjectProcedure> queryThePersonalProcedureTree(
            @Param("userId") Long userId,
            @Param("projectId") String projectId);

    List<DahcProjectProcedure> queryThePersonalTaskTree(
            @Param("userId") Long userId,
            @Param("projectId") String projectId);


    /**
     * 核查人数
     *
     * @return
     * @author MCY
     * @date 2023/3/27 17:15
     */

    @Select("SELECT count(*) FROM sys_user u\n" +
            "inner join dahc_sys_user_procedure p on p.user_id = u.user_id\n" +
            "WHERE p.procedure_id = #{procedureId}")
   Integer numberOfPeopleVerified(@Param("procedureId") String procedureId);
}
