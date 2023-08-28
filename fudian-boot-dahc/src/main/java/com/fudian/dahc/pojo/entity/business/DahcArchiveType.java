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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 2023/1/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dahc_archive_type")
@ApiModel(value = "档案类型表 archiveType", parent = MyBaseEntity.class)
public class DahcArchiveType extends MyBaseEntity {

    private static final long serialVersionUID = 23534354L;

    /**
     * 档案类型名
     */
    @TableField(value = "archive_name")
    @Size(message = "档案类型名字限制为2-30个字符", min = 2, max = 30, groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "档案类型名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "档案类型名(长度min:2,max:30)", example = "文书档案", position = 10, required = true)
    private String archiveName;

    /**
     * 一级表数据库表名
     */
    @TableField(value = "table_level1_en")
    @ApiModelProperty(hidden = true)
    private String tableLevel1En;


    /**
     * 二级表数据库表名
     */
    @TableField(value = "table_level2_en")
    @ApiModelProperty(hidden = true)
    private String tableLevel2En;

    /**
     * 描述
     */
    @Size(max = 100, groups = {AddGroup.class, UpdateGroup.class})
    @TableField(value = "archive_desc")
    @ApiModelProperty(value = "描述(长度max:100)", example = "文书档案描述", allowEmptyValue = true)
    private String archiveDesc;

}
