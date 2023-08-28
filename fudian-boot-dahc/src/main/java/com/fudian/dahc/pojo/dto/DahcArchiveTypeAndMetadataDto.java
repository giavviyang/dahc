package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 2023/3/15
 */
@Data
public class DahcArchiveTypeAndMetadataDto {


    /**
     * 元数据id
     */
    @NotNull(message = "元数据id不能为空")
    @ApiModelProperty(value = "元数据id", required = true)
    private Long metadataId;

    @ApiModelProperty(value = "元数据名称", required = true)
    private String metadataName;

    /**
     * 排序
     */
    @Pattern(regexp = "\\d*")
    @NotNull(message = "展示排序有空值")
    @ApiModelProperty(value = "元数据排序", required = true)
    private Integer sort;

    /*默认字段排序*/
    @ApiModelProperty(value = "元数据排序", required = true)
    private String attrOrder;

}
