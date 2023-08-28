package com.fudian.dahc.mapper.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.dto.DataTemplateDto;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.util.mybatispuls.CurrencyResultHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;
import java.util.Map;


/**
 * 2023/2/7
 */
@Mapper
public interface DahcDataTemplateMapper extends MyBaseMapper<DahcDataTemplate> {

    int bulkAdditions(DataTemplateDto dto);

    @MapKey(value = "id")
    List<Map<String, Object>> readMetadataLimit(DataTemplateDto dto);

    /*查询根据项目id查询数量*/
    @Select("select * from ${archiveLevelName}")
    List<DahcDataTemplate> queryTheAmountOfFileDataBasedOnTheProject(@Param("projectId") String projectId,@Param("archiveLevelName")String archiveLevelName);


    @Select("select * from ${archiveLevelName} where id = #{id}")
    List<DahcDataTemplate> queryTheAmountOfFileDataBasedOnId(@Param("id") String id,@Param("archiveLevelName")String archiveLevelName);

    @Select("select count(*) from ${archiveLevelName} attr where attr.project_id = #{projectId}")
    Integer queryTheAmountOfFileDataBasedOnTheProjectCount(@Param("projectId") String projectId,@Param("archiveLevelName")String archiveLevelName);

    /*查询案件数量*/
    @MapKey(value = "id")
    //@Select("select count(*) from ${archiveLevelName} attr where attr.project_id = #{projectId} and attr.pid = #{pid}")
    List<Map<String, Long>> checkTheNumberOfCasesCount(@Param("projectId") String projectId,
                                       @Param("archiveLevelName")String archiveLevelName,
                                       @Param("pid")List<String> pid
                                       );

    /**
     * 备份原始存储
     * 状态值 文件表(0文件未备份,1已备份未挂接,2已挂接) 案卷级(0文件未备份,1,已备份未加入核查,2已加入核查)
     */
    @Insert("REPLACE INTO ${originalTableName} SELECT * FROM ${tableName} where status = 0;")
    int insertOriginalDataStorage(@Param("tableName") String tableName, @Param("originalTableName") String originalTableName);

    /**
     * 修改状态值
     *
     * @param tableName 表名
     * @param newStatus 新状态值 0 文件未备份 文件表(1已备份未挂接,2已挂接) 案卷级(1,已备份未加入核查,2已加入核查)
     * @param oldStatus 旧状态值 0 文件未备份 文件表(1已备份未挂接,2已挂接) 案卷级(1,已备份未加入核查,2已加入核查)
     */
    @Update("UPDATE ${tableName} AS A " +
            "INNER JOIN ( SELECT status FROM ${tableName} WHERE status = #{oldStatus} ) AS B " +
            "ON A.status = B.status SET A.status = #{newStatus}")
    int modifyStatusValue(@Param("tableName") String tableName, @Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus);

    /**
     * 修改表中数据的状态
     *
     * @param tableName 表名
     * @param newStatus 新状态值 0 文件未备份 文件表(1已备份未挂接,2已挂接) 案卷级(1,已备份未加入核查,2已加入核查)
     * @param oldStatus 旧状态值 0 文件未备份 文件表(1已备份未挂接,2已挂接) 案卷级(1,已备份未加入核查,2已加入核查)
     * @param ids       ids
     * @return
     */
    @Update("UPDATE ${tableName} AS A INNER JOIN ( SELECT status FROM ${tableName} WHERE status = #{oldStatus} ) AS B " +
            "ON A.status = B.status SET A.status = #{newStatus} where A.id in (${ids})")
    int modifyStatusValueInList(@Param("tableName") String tableName, @Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus, @Param("ids") String ids);


    /**
     * status = 1 状态值 文件表(0文件未备份,1已备份未挂接,2已挂接) 案卷级(0文件未备份,1,已备份未加入核查,2已加入核查)
     * 根据表名查询状态为1的id和主键
     *
     * @param tableName
     * @param attr
     * @return
     */
    @MapKey(value = "id")
    @Select({"<script> " +
            "SELECT id,${attr} FROM ${tableName} where status = 1" +
            "<if test=\"projectId != null and projectId != ''\">and project_id = #{projectId} </if>" +
            "</script>"})
    List<Map<String, String>> queryDataThatHasNotBeenChecked(@Param("tableName") String tableName, @Param("attr") String attr, @Param("projectId") String projectId);


    @MapKey(value = "id")
    List<Map<String, String>> queryDataThatHasNotBeenCheckedAll(@Param("tableName") String tableName,
                                                                @Param("attr") String attr,
                                                                @Param("projectId") String projectId,
                                                                @Param("fileIds") List<String> fileIds
                                                                );

    /**
     * 根据表名查询全部id和主键
     *
     * @param tableName
     * @param attr
     * @return
     */
    @MapKey(value = "id")
    @Select("SELECT id,${attr} FROM ${tableName};")
    List<Map<String, String>> queryDataThatHasNotBeenCheckedNoStatus(@Param("tableName") String tableName, @Param("attr") String attr);


    /**
     * status = 1 状态值 文件表(0文件未备份,1已备份未挂接,2已挂接) 案卷级(0文件未备份,1,已备份未加入核查,2已加入核查)
     * 根据主键查询文件表中匹配数据
     *
     * @param tableName
     * @param attr
     * @param key
     * @return
     */
    @MapKey(value = "id")
    @Select("SELECT id,${attr} FROM ${tableName} where status = 1 and ${attr} like '%${key}%' ;")
    List<Map<String, String>> queryDataThatHasNotBeenBulk(@Param("tableName") String tableName, @Param("attr") String attr, @Param("key") String key);

    /**
     * 创建索引
     * CREATE UNIQUE INDEX ux_attr_dh ON ${tableName}(${indexName})
     * ALTER TABLE dahc_dt1_bwubqvyvcbqldqlgybfd ADD INDEX ux_attr_dh (attr1);
     */
    @Update("ALTER TABLE ${tableName} ADD UNIQUE INDEX ux_attr_dh (${indexName},project_id)")
    int createIndex(@Param("tableName") String tableName, @Param("indexName") String indexName);

    /**
     * 根据表名和唯一索引查询到索引字段
     * SELECT t2.stat_description FROM (
     * SELECT * FROM mysql.`innodb_index_stats` a WHERE a.table_name like '%dahc_dt1_uislfnaccfcpbcehjlvi_backup%') t2
     * WHERE t2.sample_size = '1' and index_name='ux_attr_dh';
     * SELECT COLUMN_NAME  FROM information_schema.STATISTICS WHERE table_name='dahc_dt1_bwubqvyvcbqldqlgybfd' AND index_name <>'PRIMARY'
     */
    @Select("SELECT COLUMN_NAME  FROM information_schema.STATISTICS " +
            "WHERE table_name=#{tableName} AND index_name <>'PRIMARY' AND COLUMN_NAME like 'attr%'")
    List<String> indexFieldFound(@Param("tableName") String tableName);

    @Select("SELECT INDEX_NAME  FROM information_schema.STATISTICS " +
            "WHERE table_name=#{tableName} AND index_name <>'PRIMARY' AND COLUMN_NAME like 'attr%'")
    List<String> indexFieldFound2(@Param("tableName") String tableName);

    @Update("drop index ${indexName} on ${tableName}")
    int deleteIndex(@Param("tableName") String tableName,@Param("indexName") String indexName);

    /**
     * 查询所有案卷
     * mybatis 封装流式处理
     */
    @MapKey(value = "id")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1)
    @ResultType(java.util.Map.class)
    @Select("SELECT id,${attr} FROM ${tableName} where status = 1")
    void mybatisStreamQuery(ResultHandler<Map<String, String>> handler, @Param("tableName") String tableName, @Param("attr") String attr);/**

     / * 查询所有案卷
     * mybatis 封装流式处理
     */
    @MapKey(value = "id")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1)
    @ResultType(java.util.Map.class)
    //@Select("SELECT id,${attr} FROM ${tableName} where project_id = #{projectId} and batch_no in (" +
    //        "<foreach collection=\"batchNo\" item=\"item\" open=\"where\" separator=\"or\">" +
    //        "#{item}" +
    //        "</foreach> )")
    void mybatisStreamQueryBybatch(ResultHandler<Map<String, String>> handler, @Param("tableName") String tableName, @Param("attr") String attr,@Param("projectId")String projectId,@Param("batchNo")List<String> batchNo);



    @MapKey(value = "id")
    @Select("select ${ew.sqlSelect} from dahc_file t ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(java.util.Map.class)
    void getDahcDataTemplateListBigData(@Param(Constants.WRAPPER) QueryWrapper<DahcDataTemplate> wrapper, CurrencyResultHandler<Map<String, String>> handler);


    @MapKey(value = "id")
    @Select("select ${ew.sqlSelect},pid from dahc_file t ${ew.customSqlSegment}")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1000)
    @ResultType(java.util.Map.class)
    void getDahcDataTemplateListBigDataCase(@Param(Constants.WRAPPER) QueryWrapper<DahcDataTemplate> wrapper, CurrencyResultHandler<Map<String, String>> handler);

//    @Select("select * from ${profileDataTableName} where id in(#{ids})")
    List<DahcDataTemplate> queryBasedOnPrimaryKeyIDData(@Param("profileDataTableName") String profileDataTableName,
                                                        @Param("ids") List<String> ids,
                                                        @Param("pageSize") Integer pageSize,
                                                        @Param("pageNum") Integer pageNum,
                                                        @Param("attrOrder") String attrOrder,
                                                        @Param("caseNum") String caseNum
                                                        );


    /**
     * 修改排序
     *
     * @return int
     * @author MCY
     * @date 2023/3/22 10:43
     */
    int modifyTheSort(@Param("id1") String id1,
                      @Param("id2") String id2,
                      @Param("caseTableName") String caseTableName,
                      @Param("attrOrder") String attrOrder);


    @MapKey(value = "id")
    List<Map<String, Object>> queryCaseFileDataBasedOnBatches(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("attrOrder") String attrOrder,
            @Param("batchNo") String batchNo,
            @Param("caseNum") String caseNum,
            @Param("projectId") String projectId
    );

    @MapKey(value = "id")
    List<Map<String, Object>> accessPersonalVerificationData(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("projectId") String projectId,
            @Param("caseNum") String caseNum,
            @Param("isProcedureInspect") String isProcedureInspect,
            @Param("inspectId") Long inspectId
    );

    /**
     *查看自己关联工序的未领取的数据
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author MCY
     * @date 2023/4/26 16:01
     */
    @MapKey(value = "id")
    List<Map<String, Object>> notClaimedAccessPersonalVerificationData(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("projectId") String projectId,
            @Param("caseNum") String caseNum,
            @Param("isProcedureInspect") String isProcedureInspect,
            @Param("inspectId") Long inspectId
    );

    @MapKey(value = "id")
    List<Map<String, Object>> queryPersonalDoubtfulData(
            @Param("archiveLevelName") String archiveLevelName,
            @Param("procedureId") String procedureId,
            @Param("projectId") String projectId,
            @Param("caseNum") String caseNum,
            @Param("inspectId") Long inspectId
    );
}
