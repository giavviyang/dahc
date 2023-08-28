package com.fudian.dahc.mapper.project;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.project.DahcProjectTable;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 2023/2/10
 */
@Mapper
public interface ProjectTableMapper extends MyBaseMapper<DahcProjectTable> {

//    List<DahcProjectTable> selectListAndProcedure(@Param("pid") Long id);

    @Select("select * from dahc_project_table where project_state = 1")
    List<DahcProjectTable> queryProjectPullDown();

    @Update("update dahc_project_table set project_state = 1 where id = #{id}")
    int startInspect(@Param("id") String id);

    @Select("SELECT *,p.user_id from dahc_project_table t \n" +
            "inner join dahc_sys_user_procedure p on t.id = p.project_id\n" +
            "where " +
            "p.user_id = #{userId} \n" +
            "and t.project_state = 1 \n" +
            "GROUP BY t.id")
    List<DahcProjectTable> queryUserProject(@Param("userId") String userId);

    @Delete({"<script> " +
            "DELETE FROM ${tableName} t " +
            "WHERE t.project_id IN" +
            "<foreach collection=\"ids\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">" +
            "#{id}" +
            "</foreach>" +
            "</script>"})
    int deleteProjectAssociatedData(@Param("ids") List<Long> ids, @Param("tableName") String tableName);


    List<DahcProjectTable> queryThePersonalProjectTree(@Param("userId") Long userId);

    /**
     * 根据核查id获取绑定的项目的状态
     *
     * @return java.util.List<com.fudian.dahc.pojo.entity.project.DahcProjectTable>
     * @author MCY
     * @date 2023/2/21 15:01
     */
    List<RecordTrueingDto> queryProjectState(@Param("ids") String[] ids);


    @MapKey(value = "id")
    @Select("SELECT *, n.batch_no batchNo FROM ${archiveLevelName} n\n" +
            "where n.project_id = #{projectId}\n" +
            "GROUP BY n.batch_no")
    List<Map<String, Object>> queryArchiveBatchData(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("projectId") String projectId);


    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tdahc_project_table t \n" +
            "\t\n" +
            "\tINNER JOIN dahc_archive_type a ON t.archive_type_id = a.id \n" +
            "\n" +
            "WHERE\n" +
            "\ta.id = #{archiveTypeId}\n" +
            "\tGROUP BY a.id")
    DahcArchiveType queryTableNamesBasedOnItems(@Param("archiveTypeId") String archiveTypeId);
}
