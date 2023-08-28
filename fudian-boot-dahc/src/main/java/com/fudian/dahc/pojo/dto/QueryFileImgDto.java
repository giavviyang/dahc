package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询图片", parent = DahcFilePhoto.class)
public class QueryFileImgDto extends DahcFilePhoto {


    /*图片信息*/
    @ApiModelProperty(value = "图片信息")
    private String imgUrl;
    /*总页数*/
    @ApiModelProperty(value = "总页数")
    private String totalPage;
    /*分辨率*/
    @ApiModelProperty(value = "分辨率")
    private String resolution;
}
