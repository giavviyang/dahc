package com.fudian.dahc.pojo.entity.business;


import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author fudian
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "元数据表 metadata")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dahc_business_metadata")
public class DahcMetadata extends MyBaseEntity {

    private static final long serialVersionUID = 235334354L;


    @Size(message = "元数据名长度不符合要求", min = 2, max = 20, groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "元数据名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "元数据名(长度min:2,max:255)", example = "档号", required = true)
    private String metadataName;

    @NotBlank(message = "元数据类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "元数据类型", example = "int", allowableValues = "int,char,date")
    private String metadataType;

    @Size(message = "元数据长度不符合要求", min = 1, max = 3, groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "元数据长度", example = "255")
    private String metadataLong;

    @DecimalMax(value = "300", groups = {AddGroup.class, UpdateGroup.class})
    @Size(message = "元数据前端显示宽度不符合要求", min = 2, max = 3, groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "元数据前端显示宽度(min:10,max:300)", example = "200", required = true)
    private String metadataWidth;


    @ApiModelProperty(value = "描述(长度max:255)", example = "档号是文件唯一id")
    private String metadataDesc;


    @ApiModelProperty(value = "是否是默认字段(0-不是 1-是)", example = "是否是默认字段", hidden = true)
    private Integer defaultField;

}
