package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 2023/3/15
 */
@ApiModel(parent = DahcArchiveType.class)
@Data
public class DahcArchiveTypeAndMetadataVo extends DahcArchiveType {


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


    /*元数据名称*/
    @ApiModelProperty(value = "元数据名称")
    private String metadataName;

    /**
     * 元数据集合
     */
    @ApiModelProperty(value = "元数据集合")
    private List<DahcMetadataVo> dahcMetadataList;


}
