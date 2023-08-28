package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * wenbovo
 * 2023/3/31
 * FD-dahc-project
 */
@Data
@ApiModel(value = "匹配规则序列集合")
public class RuleSerialNumberDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer order;

    /**
     * 字段序号
     */
    @ApiModelProperty(value = "字段序号")
    private Integer sourceOrder;

    /**
     * 字段名字
     */
    @ApiModelProperty(value = "字段名字")
    private String name;



}
