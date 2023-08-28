package com.fudian.dahc.mapper.business;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.dto.DahcModelAndMetadataDto;
import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import org.apache.ibatis.annotations.*;


/**
 * 2023/2/7
 */
@Mapper
public interface DahcMapperMapper extends MyBaseMapper<DahcBusinessMapper> {


    /**
     * 查重
     *
     * @param dahcBusinessMapper 映射关系
     * @return >0 有重复数据 =0没有重复数据
     */
    @Select({"<script> " +
            "select count(*) from dahc_business_mapper a " +
            "where a.metadata_id = #{metadataId} AND a.model_id = #{modelId} " +
            "<if test=\"id != null and id != ''\">and id != #{id} </if>" +
            "</script>"})
    int selectCountDuplicateChecking(DahcBusinessMapper dahcBusinessMapper);


    @Insert({"<script> " +
            "replace into dahc_business_mapper(id,model_id,source_name,attr_ordinal,metadata_id,metadata_name,metadata_type,metadata_long,metadata_width) value" +
            "<foreach collection=\"dahcBusinessMapperList\" item=\"item\" separator=\", \">" +
            "(#{item.id}," +
            "#{id},#{item.sourceName},#{item.attrOrdinal}," +
            "#{item.metadataId},#{item.metadataName},#{item.metadataType},#{item.metadataLong},#{item.metadataWidth})" +
            "</foreach>" +
            "</script>"})
    int bulkAdditions(DahcModelAndMetadataDto dahcModel);

    @Delete("Delete FROM dahc_business_mapper WHERE model_id=#{id}")
    int deleteByModelId(String id);
}
