package com.fudian.dahc.pojo.entity.project;


import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DahcProjectTable extends MyBaseEntity {


    /**
     * 项目名
     */
    @ApiModelProperty(value = "项目名", position = 5, example = "一期项目",required = true)
    private String projectName;

    /**
     * 项目描述
     */
    @ApiModelProperty(value = "项目描述", position = 6, example = "一个一期项目")
    private String projectDesc;

    /**
     * 档案类型
     */
    @ApiModelProperty(value = "档案类型", position = 7, example = "123456",required = true)
    private String archiveTypeId;

    /**
     * 项目描述
     */
    @ApiModelProperty(value = "备注", position = 8)
    private String remarks;

    /**
     * 项目状态
     */
    @ApiModelProperty(value = "项目状态(0-未开始,1-已开始,2-已结束)", position = 9, example = "0", allowableValues = "0,1,2")
    private String projectState;


    /*项目开始时间*/
    @ApiModelProperty(value = "项目创建时间", position = 10, required = true)
    private Date startTime;

    /*项目结束时间*/
    @ApiModelProperty(value = "项目结束时间", position = 11, required = true)
    private Date endTime;

    /*项目结束时间*/
    @ApiModelProperty(value = "项目实际开始时间", position = 12, required = true)
    private Date startPracticalTime;

    /*项目结束时间*/
    @ApiModelProperty(value = "项目实际结束时间", position = 13, required = true)
    private Date endPracticalTime;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<DahcProjectProcedure> dahcProjectProcedureList;

    /*是否是项目  0-项目*/
    @ApiModelProperty("是否是项目(0-项目)")
    @TableField(exist = false)
    private Integer projectAndProcessState;

    @ApiModelProperty(hidden = true)
    @TableField(exist = false)
    private List<DahcProjectProcedure> children;


}
