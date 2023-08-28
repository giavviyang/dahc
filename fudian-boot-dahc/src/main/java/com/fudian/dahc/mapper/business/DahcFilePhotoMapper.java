package com.fudian.dahc.mapper.business;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;

import java.util.List;

/**
 * 2023/3/9
 */
@Mapper
public interface DahcFilePhotoMapper extends MyBaseMapper<DahcFilePhoto> {


    /**
     * mybatis 封装流式处理
     */
    @MapKey(value = "id")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = 1)
    @ResultType(java.util.Map.class)
    @Select("SELECT * FROM dahc_file_photo where name = #{name}")
    void mybatisStreamQuery(ResultHandler<DahcFilePhoto> handler, @Param("name") String name);

    /*批量新增*/
    int bulkInsertCheck(@Param("dahcFilePhotos") List<DahcFilePhoto> dahcFilePhotos);

    /**
     * 有索引相同的先先删除再新增
     *
     * @return int
     * @author MCY
     * @date 2023/4/26 15:25
     */
    int bulkInsertDelete(@Param("dahcFilePhoto") DahcFilePhoto dahcFilePhoto);

    /**
     * 批量查询
     *
     * @param lists id
     * @return List
     */
    @Select({"<script> " +
            "SELECT * FROM `dahc_file_photo` " +
            "<foreach collection=\"lists\" item=\"item\" open=\"where\" separator=\"or\">" +
            "${attr} = #{item}" +
            "</foreach>" +
            "</script>"})
    List<DahcFilePhoto> getFilePhotoMapper(@Param("lists") List<String> lists, @Param("attr") String attribute);

    @Select("SELECT ifnull(max(page_num),0) FROM `dahc_file_photo` WHERE project_id = #{projectId} and pid=#{pid} ")
    Integer queryTheMaximumNumberOfPages(@Param("projectId") String projectId, @Param("pid") String pid);

    Integer thePictureMovesUpAndDown(@Param("id1") String id1, @Param("id2") String id2);

    /**
     * @return 查询包含当前页下
     * @author MCY
     * @date 2023/4/6 15:45
     */
    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tdahc_file_photo \n" +
            "WHERE\n" +
            "\tproject_id = #{projectId}\n" +
            "\tand pid = #{pid}\n" +
            "\tand \n" +
            "\tpage_num > #{pageNum} ")
    List<DahcFilePhoto> queriesAllDataUnderTheCurrentPage(@Param("projectId") String projectId,
                                                          @Param("pid") String pid,
                                                          @Param("pageNum") Integer pageNum);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tdahc_file_photo \n" +
            "WHERE\n" +
            "\tproject_id = #{projectId}\n" +
            "\tand pid = #{pid}\n" +
            "\tand \n" +
            "\tpage_num >= #{pageNum} ")
    List<DahcFilePhoto> theQueryIsIncludedUnderTheCurrentPage(@Param("projectId") String projectId,
                                                              @Param("pid") String pid,
                                                              @Param("pageNum") Integer pageNum);


    /**
     * 批量修改页数
     *
     * @return
     * @author MCY
     * @date 2023/4/6 16:57
     */
    @Update("UPDATE dahc_file_photo set page_num = page_num + ${size} where id in (#{ids})")
    int bulkModifyTheNumberOfPages(@Param("ids") String ids,
                                   @Param("size") Integer size);


    int modifyTheNumberOfPages(
            @Param("projectId") String projectId,
            @Param("pid") String pid,
            @Param("theNumberOfStartingPages") Integer theNumberOfStartingPages,
            @Param("startPages") Integer startPages,
            @Param("comeToAnEndPages") Integer comeToAnEndPages
    );
}
