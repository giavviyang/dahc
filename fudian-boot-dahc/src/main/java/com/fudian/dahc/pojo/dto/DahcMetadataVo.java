package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcMetadata;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/3/15
 */
@Data
public class DahcMetadataVo extends DahcMetadata {

    /**
     * 创建人名
     */
    @ApiModelProperty(value = "创建人名")
    private String createByName;

    /**
     * 更新人名
     */
    @ApiModelProperty(value = "更新人名")
    private String updateByName;

    /**
     * 元数据在档案中数据库字段序号
     */
    @ApiModelProperty(value = "元数据在档案中数据库字段序号")
    private Long attrOrder;

    /**
     * 档案数据表名
     */
    @ApiModelProperty(value = "档案数据表名(长度min:2,max:100)")
    private String archiveLevelName;


    @ApiModelProperty(value = "单元格是否可编辑")
    private Boolean disabled = false;

    @ApiModelProperty(value = "元数据id")
    private String metadataId;
}
