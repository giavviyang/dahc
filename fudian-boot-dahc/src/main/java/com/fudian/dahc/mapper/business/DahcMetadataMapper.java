package com.fudian.dahc.mapper.business;

import com.fudian.dahc.mapper.base.MyBaseMapper;
import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataVo;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 2023/2/7
 */
@Mapper
public interface DahcMetadataMapper extends MyBaseMapper<DahcMetadata> {

    @Select(value = "select * from  dahc_business_metadata")
    List<DahcMetadata> queryMetadataSelect();


    /**
     * 查重
     *
     * @param dahcMetadata 元数据类型
     * @return >0 有重复数据 =0没有重复数据
     */
    @Select({"<script> " +
            "select count(*) from dahc_business_metadata a " +
            "where a.metadata_name = #{metadataName}" +
            "<if test=\"id != null and id != ''\">and id != #{id} </if>" +
            "</script>"})
    int selectCountDuplicateChecking(DahcMetadata dahcMetadata);


    /**
     * 查询是否关联档案类型数据
     *
     * @return
     * @author MCY
     * @date 2023/3/28 10:40
     */
    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tdahc_archive_type t\n" +
            "\tINNER JOIN dahc_business_archive_metadata m ON t.id = m.archive_type_id\n" +
            "\tINNER JOIN dahc_business_metadata bm ON bm.id = m.metadata_id \n" +
            "WHERE\n" +
            "bm.id=#{metadataId}")
    List<DahcArchiveTypeAndMetadataVo> queryWhetherToAssociateProfileTypeData(@Param("metadataId") Long metadataId);
}
