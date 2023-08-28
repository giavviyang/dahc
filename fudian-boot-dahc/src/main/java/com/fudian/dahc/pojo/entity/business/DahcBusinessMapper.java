package com.fudian.dahc.pojo.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value = "模板映射元数据表 mapper")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dahc_business_mapper")
public class DahcBusinessMapper extends MyBaseEntity {

    private static final long serialVersionUID = 23453453L;


    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id(长度:20)", position = 1, required = true)
    private String modelId;

    /**
     * 字段名
     */
    @NotNull(message = "字段名不可为空", groups = AddGroup.class)
    @ApiModelProperty(value = "字段名(长度min:1,max:100)", position = 2, required = true)
    private String sourceName;

    /**
     * 字段序号
     */
//    @NotNull(message = "字段序号不可为空", groups = AddGroup.class)
//    @ApiModelProperty(value = "字段序号(长度min:1,max:100)", position = 3, required = true)
//    private String sourceOrder;

    /**
     * 表中attr序号
     */
    @NotNull(message = "数据库表中序号为空!", groups = AddGroup.class)
    @ApiModelProperty(value = "表中attr序号(长度min:1,max:100)", position = 8, required = true)
    @TableField(value = "attr_ordinal")
    private Long attrOrdinal;

    /**
     * 元数据id
     */
    @NotNull(message = "元数据id不可为空", groups = AddGroup.class)
    @ApiModelProperty(value = "元数据id(长度-20)", position = 9, required = true)
    private String metadataId;

    /**
     * 元数据名
     */
    @ApiModelProperty(value = "元数据名(长度min:1,max:100)", position = 10, required = true)
    private String metadataName;

    /**
     * 元数据类型
     */
    @ApiModelProperty(value = "元数据类型", position = 11, required = true)
    private String metadataType;

    /**
     * 元数据长度
     */
    @ApiModelProperty(value = "元数据长度", position = 12)
    private String metadataLong;


    @ApiModelProperty(value = "前端展示宽度(长度min:10,max:300)", position = 13)
    private String metadataWidth;

}
