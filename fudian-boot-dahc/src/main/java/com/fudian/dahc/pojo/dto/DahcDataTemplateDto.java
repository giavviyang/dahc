package com.fudian.dahc.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

/**
 * 2023/2/21
 */
@Data
@ApiModel(value = "案卷或文件数据", parent = DahcDataTemplate.class)
public class DahcDataTemplateDto extends DahcDataTemplate {

    /**
     * 档案类型id
     */
    @TableField(exist = false)
    @NotNull(message = "档案类型id不能为空", groups = {AddGroup.class,UpdateGroup.class})
    @ApiModelProperty(value = "档案类型id", required = true)
    private Long archiveTypeId;

    /**
     * 档案级别不能为空
     */
    @TableField(exist = false)
    @Max(value = 2, message = "文件类型只能是1或2")
    @Min(value = 1, message = "文件类型只能是1或2")
    @NotNull(message = "档案级别id不能为空")
    @ApiModelProperty(value = "文件级或案卷级", required = true, allowableValues = "1,2")
    private Integer level;


    @TableField(exist = false)
    @ApiModelProperty(value = "文件id")
    private String fileId;

    /**
     * id集合
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "id集合", required = true)
    private List<Long> ids;

    /**
     * 一级表数据库表名
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "一级表数据库表名")
    private String tableLevel1En;


    @TableField(exist = false)
    @ApiModelProperty(value = "案卷档号")
    private String caseFileNumber;


    @TableField(exist = false)
    @ApiModelProperty(value = "案卷顺序号")
    private String caseNumber;

    /**
     * 二级表数据库表名
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "二级表数据库表名")
    private String tableLevel2En;


    @TableField(exist = false)
    @ApiModelProperty(value = "工序ID")
    private String procedureId;

    @TableField(exist = false)
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @TableField(exist = false)
    @ApiModelProperty(value = "项目名称")
    private String procedureName;
    /**
     * 文件级数据
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "文件级数据")
    private List<DahcDataTemplate> dahcDataTemplateList = new LinkedList<>();
}
