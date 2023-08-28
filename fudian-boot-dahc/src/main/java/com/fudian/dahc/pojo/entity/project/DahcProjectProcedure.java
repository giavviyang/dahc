package com.fudian.dahc.pojo.entity.project;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import com.fudian.dahc.pojo.entity.trueingManage.DahcHcxTrueingManagement;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DahcProjectProcedure extends MyBaseEntity {

    /**
     * 工序名
     */
    @ApiModelProperty(value = "工序名", position = 5, example = "案卷目录核查工序", required = true)
    private String procedureName;

    /**
     * 工序类型
     */
    @ApiModelProperty(value = "工序类型", position = 6, example = "123", required = true)
    private Long procedureType;

    /**
     * 核查项页面分类
     */
    @ApiModelProperty(value = "核查项页面分类", position = 7, required = true)
    private Long trueingType;

    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id", position = 8, required = true)
    private String projectId;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述", position = 9)
    private String procedureDesc;

    /**
     * 核查项Id
     */
    @ApiModelProperty(value = "核查项Id", position = 10, required = true)
    private String trueingId;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", position = 11)
    private Long sort;

    /**
     * 排序
     */
    @ApiModelProperty(value = "创建人", position = 12, hidden = true)
    private String createByString;

    /**
     * 绑定核查类型的跳转页面路径
     */
    @ApiModelProperty(value = "绑定核查类型的跳转页面路径", position = 13, hidden = true)
    private String trueingPagePath;

    /**
     * 绑定核查类型的跳转页面路径
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "核查范围二级节点id", position = 14, hidden = true)
    private String trueingPagePathId;


    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<DahcHcxTrueingManagement> dahcHcxTrueingManageList;

    /*未核查案卷数量*/
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Integer theNumberOfFilesNotVerified;

    /*numberOfFilesVerified*/
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Integer numberOfFilesVerified;


    /*核查人数*/
    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private Integer numberOfPeopleVerified;


}
