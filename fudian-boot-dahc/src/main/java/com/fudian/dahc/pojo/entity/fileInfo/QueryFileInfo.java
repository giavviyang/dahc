package com.fudian.dahc.pojo.entity.fileInfo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * wenbovo
 * 2023/3/24
 * FD-dahc-project
 */
@Data
@ApiModel(value = "查询文件信息")
public class QueryFileInfo {

    @ApiModelProperty(value = "文件名")
    private String nameSearch;
    @ApiModelProperty(value = "页")
    private int pageIndex;
    @ApiModelProperty(value = "页大小")
    private int pageSize;

}
