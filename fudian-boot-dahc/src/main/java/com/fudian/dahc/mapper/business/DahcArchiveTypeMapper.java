package com.fudian.dahc.mapper.business;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndModelVo;
import com.fudian.dahc.pojo.dto.DahcMetadataVo;
import com.fudian.dahc.pojo.dto.UserVo;
import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcBusinessArchiveMetadata;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataDto;
import com.fudian.dahc.pojo.query.ArchiveAndMetadataVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 2023/1/30
 */
@Mapper
public interface DahcArchiveTypeMapper extends MyBaseMapper<DahcArchiveType> {

    /**
     * 创建数据存储表
     *
     * @param tableName 表名称
     */
    void createDateTemplateTable(String tableName);

    /**
     * ** 查询用户id和别名 用于展示创建人
     *
     * @return UserVo
     */
    @Select("SELECT user_id as id,nick_name as name FROM sys_user;")
    List<UserVo> queryAllUsers();

    /**
     * ** 根据id查询 档案类型
     *
     * @return
     */
    @Select({"<script> " +
            "SELECT * FROM `dahc_archive_type` " +
            "<where>" +
            "<if test = \" id != null and  id != '' \"> AND id = #{id} </if> " +
            "</where>" +
            "</script>"})
    List<DahcArchiveTypeAndModelVo> queryFileTypeAndTemplate(@Param("id") String id);


/*    @Select("SELECT * \n" +
            "FROM\n" +
            "\tdahc_archive_type a\n" +
            "\tINNER JOIN dahc_business_archive_metadata am ON a.table_level1_en = am.archive_level_name \n" +
            "\tAND a.id = am.archive_type_id \n" +
            "WHERE\n" +
            "\tam.archive_key = 1 \n" +
            "\tAND a.id = #{archiveTypeId} \n")
    DahcBusinessArchiveMetadata theFileNumberFieldIsThatDatabaseField(@Param("archiveTypeId") String archiveTypeId);   */


    @Select("SELECT * FROM dahc_archive_type t\n" +
            "INNER join dahc_business_archive_metadata m on t.table_level1_en = m.archive_level_name\n" +
            "INNER join dahc_business_metadata bm on m.metadata_id = bm.id\n" +
            "WHERE \n" +
            "bm.metadata_name = #{name}\n" +
            "and t.id = #{archiveTypeId}")
    DahcBusinessArchiveMetadata theFileNumberFieldIsThatDatabaseField(
            @Param("archiveTypeId") String archiveTypeId,
            @Param("name") String name);


    /*案件档号*/
    @Select("SELECT * FROM dahc_archive_type t\n" +
            "INNER join dahc_business_archive_metadata m on t.table_level2_en = m.archive_level_name\n" +
            "INNER join dahc_business_metadata bm on m.metadata_id = bm.id\n" +
            "WHERE \n" +
            "bm.metadata_name = #{name}\n" +
            "and t.id = #{archiveTypeId}")
    DahcBusinessArchiveMetadata caseFileNumberAttr(
            @Param("archiveTypeId") String archiveTypeId,
            @Param("name") String name);


    /**
     * 删除数据存储表
     *
     * @param tableName 表名称
     */
    @Update("DROP TABLE IF EXISTS ${tableName}")
    void deleteTable(@Param("tableName") String tableName);

    /**
     * 创建备份原始数据存储表
     */
    @Update("CREATE TABLE ${originalTableName} AS SELECT * FROM ${tableName}")
    void originalDataStorageTable(@Param("tableName") String tableName, @Param("originalTableName") String originalTableName);


    /**
     * 根据档案id和等级查询档案和其元数据,id为空查询所有
     *
     * @param id
     * @return
     */
    List<DahcArchiveTypeAndMetadataVo> selectListAndMetadataById(@Param("level") Integer level, @Param("typeId") Long id);

    /**
     * 根据数据存储表表名查询
     *
     * @param archiveLevelName
     * @return
     */
    List<ArchiveAndMetadataDto> queryArchiveAndMetadata(String archiveLevelName);


    /**
     * 批量插入档案类型元数据中间表
     *
     * @param archiveAndMetadataVO
     * @return
     */
    @Insert({"<script> " +
            "REPLACE INTO dahc_business_archive_metadata (archive_type_id,archive_level_name,attr_order,metadata_id) VALUES" +
            "<foreach collection = \"archiveAndMetadataVO.ids\" item = \"item\" index=\"index\" separator = \",\">" +
            "(#{archiveAndMetadataVO.archiveTypeId},#{archiveAndMetadataVO.archiveLevelName},#{index},#{item})" +
            "</foreach>" +
            "</script>"})
    int insertListAndMetadataList(@Param("archiveAndMetadataVO") ArchiveAndMetadataVO archiveAndMetadataVO);

    /**
     * 批量保存元数据
     *
     * @return int
     * @author MCY
     * @date 2023/3/29 14:13
     */
    int saveMetadataInBulk(@Param("archiveAndMetadataVO") ArchiveAndMetadataVO archiveAndMetadataVO);

    /*批量修改模版数据*/
    int modifyTemplateSerialNumbersInBulk(@Param("dahcBusinessArchiveMetadata") List<DahcBusinessArchiveMetadata> dahcBusinessArchiveMetadata,@Param("modelIds")String modelIds);


    @Select("SELECT * FROM `dahc_business_archive_metadata`  WHERE archive_type_id = #{archiveId} and archive_level_name = #{archiveTableName}")
    List<DahcBusinessArchiveMetadata> theAssociatedMetadataField(@Param("archiveId") Long archiveId,@Param("archiveTableName") String archiveTableName);

    @Delete("DELETE FROM dahc_business_archive_metadata WHERE archive_type_id =#{archiveTypeId} AND metadata_id=#{metadataId} ")
    int deleteMetadataOfArchive(@Param("archiveTypeId") Long archiveTypeId, @Param("metadataId") String metadataId);

    /**
     * 档案类型批量新增默认数据
     *
     * @param
     * @return
     */
/*    @Insert("INSERT INTO dahc_business_archive_metadata ( archive_type_id, archive_level_name, attr_order, metadata_id )\n" +
            "VALUES\n" +
            "\t< foreach collection = \"archiveMetadatas\" item = \"archiveMetadata\" INDEX = \"index\" SEPARATOR = \",\" > (#{archiveMetadata.archiveTypeId},#{archiveMetadata.archiveLevelName},#{index},#{archiveMetadata.metadataId})\n" +
            "\t</ foreach >")*/
    int addDefaultDataToTheProfileTypeInBulk(@Param("archiveMetadatas") List<DahcBusinessArchiveMetadata> archiveMetadata);


    /**
     * 根据档案类型或者表删除全部元数据映射
     */
    @Delete({"<script> " +
            "DELETE FROM dahc_business_archive_metadata " +
            "<where>" +
            "<if test = \" archiveAndMetadataVO.archiveLevelName != null and  archiveAndMetadataVO.archiveLevelName != '' \"> AND archive_level_name = #{archiveAndMetadataVO.archiveLevelName} </if> " +
            "<if test = \" archiveAndMetadataVO.archiveTypeId != null and  archiveAndMetadataVO.archiveTypeId != '' \"> AND archive_type_id = #{archiveAndMetadataVO.archiveTypeId} </if> " +
            "</where>" +
            "</script>"})
    int deleteListAndMetadataAll(@Param("archiveAndMetadataVO") ArchiveAndMetadataVO archiveAndMetadataVO);


    /**
     * 根据表名获取主键
     *
     * @param archiveLevelName
     * @return
     */
    @Select({"<script> " +
            "select * FROM dahc_business_metadata," +
            "(SELECT metadata_id ,attr_order FROM `dahc_business_archive_metadata` " +
            "WHERE archive_key = 1 AND archive_level_name LIKE  '%${archiveLevelName}%') as b " +
            "WHERE id =b.metadata_id " +
            "</script>"})
    List<DahcMetadataVo> selectKeyOfMiddleTableByNameAndMid(@Param("archiveLevelName") String archiveLevelName);


    @Select("SELECT\n" +
            "\ta.table_level1_en\n" +
            "FROM\n" +
            "\tdahc_archive_type a\n" +
            "\tLEFT JOIN dahc_project_table t ON a.id = t.archive_type_id \n" +
            "WHERE\n" +
            "\tt.id = #{projectId}")
    String getsTheFileTableNameBasedOnTheFileType(@Param("projectId") String projectId);


    /**
     * 档案类型查重
     *
     * @param dahcArchiveType 档案类型
     * @return >0 有重复数据 =0没有重复数据
     */
    @Select({"<script> " +
            "select count(*) from dahc_archive_type a " +
            "where a.archive_name = #{archiveName} " +
            "<if test=\"id != null and id != ''\">and id != #{id} </if>" +
            "</script>"})
    int selectCountDuplicateChecking(DahcArchiveType dahcArchiveType);

    @Select("SELECT\n" +
            "\tam.attr_order \n" +
            "FROM\n" +
            "\tdahc_business_metadata m\n" +
            "\tLEFT JOIN dahc_business_archive_metadata am ON am.metadata_id = m.id \n" +
            "WHERE\n" +
            "\tam.archive_type_id = #{archiveTypeId} \n" +
            "\tAND am.archive_level_name = #{archiveLevelName} \n" +
            "\tand m.metadata_name = #{metadataName}" +
            "\t LIMIT 1"
    )
    String queryProfileDefaultFieldBindingAttr(@Param("archiveTypeId") String archiveTypeId,
                                               @Param("archiveLevelName") String archiveLevelName,
                                               @Param("metadataName") String metadataName);


    @Select({"<script> " +
            "SELECT * FROM dahc_archive_type t " +
            "inner JOIN dahc_project_table p ON t.id = p.archive_type_id " +
            "WHERE 1=1" +
            "<if test=\"ids.size()>0\">" +
            "AND t.id IN" +
            "<foreach collection=\"ids\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">" +
            "#{id}" +
            "</foreach>" +
            "</if>" +
            "</script>"})
    List<DahcArchiveTypeAndModelVo> queryWhetherThereIsAssociatedProjectData(@Param("ids") List<Long> ids);


    @Select("SELECT count(*) FROM dahc_archive_type t\n" +
            "left join dahc_business_archive_metadata m on t.id = m.archive_type_id and t.table_level1_en = m.archive_level_name\n" +
            "WHERE t.id = #{archiveTypeId}\n" +
            "and m.archive_level_name = #{archiveLevelName}")
    int checkWhetherTheCaseFileOrTheCaseIsSaved(@Param("archiveTypeId") Long archiveTypeId,
                                                @Param("archiveLevelName") String archiveLevelName);


    @Select("SELECT count(*) FROM ${tableName}")
    int queryWhetherThereIsData(@Param("tableName") String tableName);
}
