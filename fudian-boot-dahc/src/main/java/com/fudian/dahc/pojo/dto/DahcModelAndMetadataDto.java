package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcBusinessMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * wenbovo
 * 2023/4/10
 * FD-dahc-project
 */
@Data
public class DahcModelAndMetadataDto {

    @ApiModelProperty(value = "模板id", required = true)
    private String id;

    @ApiModelProperty(value = "数据映射集合")
    private List<DahcBusinessMapper> dahcBusinessMapperList;
}
