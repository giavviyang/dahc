package com.fudian.dahc.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询电子档案档案级别", parent = BaseEntity.class)
public class QueryElectronicArchivesDossierLevelDto extends BaseEntity {

    /**
     * 案卷id
     */
    @ApiModelProperty(value = "案卷id", required = true)
    private String filesId;

    /**
     * 案卷档号（名称）
     */
    @ApiModelProperty(value = "案卷档号（名称长度min:2,max:100）")
    private String filesName;

    /**
     * 当前工序id
     */
    @ApiModelProperty(value = "当前工序id", required = true)
    private String procedureId;

    /*项目Id*/
    @ApiModelProperty(value = "项目Id", required = true)
    private String projectId;

    /**
     * 数据存放的表名称
     */
    @ApiModelProperty(value = "数据存放的表名称(长度min:2,max:100)")
    private String archiveLevelName;

    /**
     * 档案类型id
     */
    @ApiModelProperty(value = "档案类型id")
    private String archiveId;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private String isProcedureInspect;

    /**
     * attr字段名称
     */
    @ApiModelProperty(value = "attr字段名称(长度min:2,max:100)")
    private String attrOrder;

    /**
     * 档号名称
     */
    @ApiModelProperty(value = "档号名称(长度min:2,max:100)")
    private String caseNum;

    /**
     * 档案核查结果id
     */
    @ApiModelProperty(value = "档案核查结果id")
    private String recordProcedureFilesId;

    /*批次名称*/
    @TableField(exist = false)
    @ApiModelProperty(value = "批次名称")
    private String batchNo;

    /*是否只查询存疑数据  0-存疑 1-不存疑*/
    @ApiModelProperty(value = "是否只查询存疑数据  0-存疑 1-不存疑", allowableValues = "0,1")
    private Integer isImpeach;

    /*是否开始核查*/
    @TableField(exist = false)
    @ApiModelProperty(value = "是否开始核查")
    private Integer startTheVerification;


}
