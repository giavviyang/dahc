package com.fudian.dahc.mapper.business;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 2023/2/7
 */
@Mapper
public interface DahcModelMapper extends MyBaseMapper<DahcModel> {

    /**
     * 查重
     * @param dahcModel 模板
     * @return >0 有重复数据 =0没有重复数据
     */
    @Select({"<script> " +
            "select count(*) from dahc_business_model a " +
            "where a.archive_type_id = #{archiveTypeId} AND model_name = #{modelName} " +
            "<if test=\"id != null and id != ''\">and id != #{id} </if>" +
            "</script>"})
    int selectCountDuplicateChecking(DahcModel dahcModel);


    @Select("SELECT * FROM `dahc_business_model` m\n" +
            "inner join  dahc_business_mapper ma on m.id = ma.model_id\n" +
            "WHERE m.archive_type_id = #{archiveTypeId}\n" +
            "and m.archive_table_name = #{modelName}")
    List<DahcBusinessMapper> queryTemplateBindingData(@Param("archiveTypeId")Long archiveTypeId,
                                                      @Param("modelName")String modelName
                                                      );
}
