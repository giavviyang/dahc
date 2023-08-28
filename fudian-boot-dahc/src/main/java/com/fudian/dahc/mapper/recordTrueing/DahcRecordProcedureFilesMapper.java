package com.fudian.dahc.mapper.recordTrueing;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.dahc.pojo.dto.QueryDahcRecordProcedureFilesDto;
import com.fudian.dahc.pojo.dto.TaskManagementProcessVo;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import com.fudian.dahc.pojo.dto.RecordTrueingDto;
import com.fudian.dahc.pojo.entity.sys.DahcSysUserProcedure;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author fudian
 * @date 2023-02-24
 */
@Mapper
public interface DahcRecordProcedureFilesMapper extends BaseMapper<DahcRecordProcedureFiles> {

    @Select("select id from ${fileTableName} where ${fileTableNameAttr} = #{filesName} and status = '2' and project_id = #{projectId}")
    public String queryFileNumber(@Param("fileTableName") String fileTableName,
                                  @Param("fileTableNameAttr") String fileTableNameAttr,
                                  @Param("filesName") String filesName,
                                  @Param("projectId") String projectId);

    @Select("SELECT count(*) FROM `dahc_sys_user_procedure` where user_id = #{userId} and procedure_id=#{procedureId}")
    Integer associateTheCurrentOperation(@Param("userId") Long userId,
                                 @Param("procedureId") String procedureId);


    //@Select("SELECT count(*) FROM `dahc_sys_user_procedure` p\n" +
    //        "inner join dahc_record_procedure_files f  on p.procedure_id = f.procedure_id\n" +
    //        "where p.user_id =  #{userId} and f.id in " +
    //        "<foreach collection = \"fileIds\" item = \"item\" index=\"index\" separator = \",\">" +
    //        "(#{item})" +
    //        "</foreach>")
    Integer theIDDeterminesTheCurrentProcess(@Param("userId") Long userId,
                                 @Param("fileIds") String[] fileIds);

    @MapKey(value = "id")
    public List<Map<String, Object>> queryElectronicArchivesDossierLevel(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("isProcedureInspect") String isProcedureInspect,
            @Param("userId") Long userId,
            @Param("attrOrder") String attrOrder,
            @Param("caseNum") String caseNum,
            @Param("start") Integer start
    );

    @MapKey(value = "id")
    public List<Map<String, Object>> queryBasedOnTheIDOfTheVerificationResult(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("attrOrder") String attrOrder,
            @Param("caseNum") String caseNum
    );

    @Select("SELECT\n" +
            "\t* , d.sort\n" +
            "FROM\n" +
            "\tdahc_record_procedure_files f\n" +
            "\tLEFT JOIN dahc_project_procedure d ON d.id = f.procedure_id \n" +
            "WHERE\n" +
            "\tf.files_id = #{filesId}\n" +
            "\tORDER BY d.sort")
    public List<TaskManagementProcessVo> taskManagementQueryOperations(
            @Param("filesId") String filesId
    );

    @MapKey(value = "id")
    public List<Map<String, String>> queryCaseDataBasedOnFileID(
            @Param("archiveLevelName1") String archiveLevelName1,
            @Param("filesId") String filesId,
            @Param("archiveLevelName2") String archiveLevelName2,
            @Param("attrOrder") String attrOrder,
            @Param("caseNum") String caseNum,
            @Param("attrOrderSequenceNumber") String attrOrderSequenceNumber, //根据顺序号排序
            @Param("startTheVerification") Integer startTheVerification //根据顺序号排序
    );


    @MapKey(value = "id")
    public List<Map<String, Object>> queryElectronicArchivesDossierLevelTemplateTwo(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("isProcedureInspect") String isProcedureInspect,
            @Param("userId") Long userId,
            @Param("attrOrder") String attrOrder,
            @Param("caseNum") String caseNum,
            @Param("start") Integer start //0-查询修改过数据
    );

    /**
     * 查询【请填写功能名称】列表
     *
     * @param dahcRecordProcedureFiles 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DahcRecordProcedureFiles> selectDahcRecordProcedureFilesList(DahcRecordProcedureFiles dahcRecordProcedureFiles);

    /*查询存疑页面数据*/
    public List<DahcRecordProcedureFiles> querySuspectData(DahcRecordProcedureFiles dahcRecordProcedureFiles);

    public List<DahcRecordProcedureFiles> theTaskManagementPageQueriesTheSuspectData(DahcRecordProcedureFiles dahcRecordProcedureFiles);

    public List<DahcRecordProcedureFiles> queryTheResultsOfTheVerificationWereNotCompleted(@Param("dto") RecordTrueingDto dto);

    public List<QueryDahcRecordProcedureFilesDto> categorizeDataBasedOnPages(@Param("trueingPagePaths") List<String> trueingPagePaths);


    @Select("SELECT\n" +
            "\t *  \n" +
            "FROM\n" +
            "\tdahc_sys_user_procedure \n" +
            "WHERE\n" +
            "\tproject_id = #{projectId} \n" +
            "\tAND user_id = #{userId}")
    List<DahcSysUserProcedure> querySysUserProcedure(@Param("projectId") String projectId, @Param("userId") Long userId);

    @Select("SELECT * FROM \n" +
            "dahc_sys_user_procedure u\n" +
            "inner join dahc_record_procedure_files f on u.procedure_id = f.procedure_id\n" +
            "WHERE \n" +
            "u.project_id = #{projectId}\n" +
            "and f.is_procedure_inspect = '0'\n" +
            "and u.user_id = #{userId}\n" +
            "GROUP BY f.procedure_id,f.files_id")
    List<DahcRecordProcedureFiles> homePageToBePickedUpTaskQuery(@Param("projectId") String projectId, @Param("userId") Long userId);

}
