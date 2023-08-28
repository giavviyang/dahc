package com.fudian.dahc.pojo.entity.business;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "模板表 model")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dahc_business_model")
public class DahcModel extends MyBaseEntity {

    private static final long serialVersionUID = 235342354L;


    /**
     * 模板名
     */
    @Size(message = "模板名长度不符合", max = 30, min = 2, groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "模板名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "模板名", example = "导入模板", notes = "长度(2,30)", position = 1, required = true)
    private String modelName;

    /**
     * 模板类型(导入导出模板)
     */
//    @ApiModelProperty(value = "模板类型(导入导出模板)", position = 2)
//    private String modelType;


    @ApiModelProperty(value = "上一级id")
    private String pid;

    /**
     * 档案类型id
     */
    @NotBlank(message = "档案类型id不能为空", groups = AddGroup.class)
    @ApiModelProperty(value = "档案类型id", position = 3, required = true)
    private String archiveTypeId;

    /**
     * 档案类型名
     */
    @ApiModelProperty(value = "档案类型名(长度min:2,max:100)", position = 4)
    private String archiveTypeName;

    /**
     * 数据表表名
     */
    @NotBlank(message = "数据库表名不能为空", groups = AddGroup.class)
    @ApiModelProperty(value = "数据表表名", position = 5, required = true)
    private String archiveTableName;

    /**
     * 数据表等级
     */
    @Max(value = 2, message = "文件类型只能是1或2", groups = AddGroup.class)
    @Min(value = 1, message = "文件类型只能是1或2", groups = AddGroup.class)
    @ApiModelProperty(value = "数据表等级:案卷或者文件级", example = "1", required = true, allowableValues = "1,2")
    @NotBlank(message = "数据表等级不能为空", groups = AddGroup.class)
    private String archiveTableLevel;


    /**
     * 描述
     */
    @Size(message = "描述长度不符合要求", max = 140)
    @ApiModelProperty(value = "描述",example = "excel导入模板",notes = "限制长度(140)")
    private String modelDesc;


    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<DahcModel> dahcModelLv;


    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private List<DahcBusinessMapper> dahcBusinessMapperList;


}
