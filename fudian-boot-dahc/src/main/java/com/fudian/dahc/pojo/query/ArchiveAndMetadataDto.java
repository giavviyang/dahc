package com.fudian.dahc.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 2023/2/8
 */
@Data
public class ArchiveAndMetadataDto {

    @ApiModelProperty(value = "档案类型id", position = 1)
    private String archiveTypeId;
    @ApiModelProperty(value = "表名", position = 2)
    private String archiveLevelName;
    @ApiModelProperty(value = "元数据类型", position = 3)
    private String metadataType;
    @ApiModelProperty(value = "元数据长度", position = 4)
    private String metadataLong;
    @ApiModelProperty(value = "元数据备注", position = 5)
    private String metadataDesc;
    @ApiModelProperty(value = "元数据id", position = 6)
    private String metadataId;
    @ApiModelProperty(value = "排序", position = 7)
    private Integer sort;
    @ApiModelProperty(value = "元数据名称", required = true)
    private String metadataName;
}
