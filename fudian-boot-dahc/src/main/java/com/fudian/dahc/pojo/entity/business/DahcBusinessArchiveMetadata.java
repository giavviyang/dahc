package com.fudian.dahc.pojo.entity.business;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 【请填写功能名称】对象 dahc_business_archive_metadata
 *
 * @author fudian
 * @date 2023-03-01
 */
@Data
@ApiModel(value = "档案类型关联元数据表", parent = BaseEntity.class)
public class DahcBusinessArchiveMetadata extends BaseEntity {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "档案类型id", required = true)
    private String archiveTypeId;


    @ApiModelProperty(value = "档案类型name(长度min:2,max:100)")
    private String archiveLevelName;

    @ApiModelProperty(value = "案卷级或文件级", required = true, allowableValues = "1,2")
    private String archiveTypeLevel;

    @ApiModelProperty(value = "序号", required = true)
    private String attrOrder;

    @ApiModelProperty(value = "元数据id", required = true)
    private String metadataId;

    @ApiModelProperty(value = "排序", required = true)
    private Integer sort;

    @TableId(value = "archive_key")
    private String archiveKey;

}
