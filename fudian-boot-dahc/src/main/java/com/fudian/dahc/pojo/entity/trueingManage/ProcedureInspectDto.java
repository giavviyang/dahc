package com.fudian.dahc.pojo.entity.trueingManage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 核查数据-----核查项显示
 *
 * @author MCY
 * @return
 * @date 2023/2/20 18:24
 */
@Data
public class ProcedureInspectDto {


    /*项目id*/
    @ApiModelProperty(value = "项目id", required = true)
    private String projectId;

    /*项目id*/
    @ApiModelProperty(value = "项目名(长度min:2,max:100)")
    private String projectName;

    /*项目状态*/
    @ApiModelProperty(value = "项目状态")
    private String projectState;

    /*工序id*/
    @ApiModelProperty(value = "工序id", required = true)
    private String procedureId;

    /*工序名称*/
    @ApiModelProperty(value = "工序名称(长度min:2,max:100)")
    private String procedureName;

    /**
     * 核查项主键id
     */
    @ApiModelProperty(value = "核查项主键id", required = true)
    private String trueingId;

    /**
     * 核查项名称
     */
    @ApiModelProperty(value = "核查项名称(长度min:2,max:100)")
    private String trueingName;

    /**
     * 档案类型
     */
    @ApiModelProperty(value = "档案类型")
    private String tableType;

    /**
     * 核查项详情
     */
    @ApiModelProperty(value = "核查项详情(长度min:0,max:100)")
    private String trueingDesc;

    /**
     * 核查项备注
     */
    @ApiModelProperty(value = "核查项备注(长度min:0,max:100)")
    private String trueingRemark;

    /**
     * 判断展示那个页面
     */
    @ApiModelProperty(value = "判断展示那个页面", required = true)
    private String trueingType;

    /**
     * 是否展示件号输入框(0-展示，1-不展示)
     */
    @ApiModelProperty(value = "是否展示件号输入框(0-展示，1-不展示)", required = true, allowableValues = "0,1")
    private String showPiece;

    /**
     * 是否展示著录框（0-展示，1-不展示）
     */
    @ApiModelProperty(value = "是否展示著录框（0-展示，1-不展示）", required = true, allowableValues = "0,1")
    private String showRecord;

    /**
     * 核查项范围（treeId)
     */
    @ApiModelProperty(value = "核查项范围（treeId)")
    private String trueingScopeStair;

    /**
     * 是否展示页号框（0-展示，1-不展示）
     */
    @ApiModelProperty(value = "是否展示页号框（0-展示，1-不展示）", required = true, allowableValues = "0,1")
    private String showPageNumber;

    /**
     * 档案级别（0-一级 1-二级 2-三级）
     */
    @ApiModelProperty(value = "档案级别（0-一级 1-二级 2-三级）", required = true, allowableValues = "0,1,2")
    private Long trueingTreeRank;
}
