package com.fudian.dahc.pojo.entity.trueingManage;


import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

@Data
public class DahcCheckResult extends MyBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id(长度-20)", position = 5)
    private String projectId;

    /**
     * 文件id
     */
    @ApiModelProperty(value = "文件id(长度-20)", position = 6)
    private String fileId;

    /**
     * 页数
     */
    @ApiModelProperty(value = "页数", position = 7)
    private String pageNum;

    /**
     * 档案类型id
     */
    @ApiModelProperty(value = "档案类型id(长度-20)", position = 8)
    private String archiveTypeId;

    /**
     * 档案级别
     */
    @ApiModelProperty(value = "档案级别", position = 9)
    private String archiveTypeLv;

    /**
     * 工序id
     */
    @ApiModelProperty(value = "工序id", position = 10)
    private String procedureId;

    /**
     * 核查项id
     */
    @ApiModelProperty(value = "核查项id", position = 11)
    private String trueingId;

    /**
     * 核查结果
     */
    @ApiModelProperty(value = "核查结果", position = 12)
    private String checkResult;

    /**
     * 核查后处理方式
     */
    @ApiModelProperty(value = "核查后处理方式", position = 13)
    private String checkUpdataType;

    /**
     * 核查修改内容
     */
    @ApiModelProperty(value = "核查修改内容", position = 14)
    private String checkUpdata;

    /**
     * 核查备注
     */
    @ApiModelProperty(value = "核查备注(长度min:0,max:100)", position = 15)
    private String checkDesc;

    /**
     * 是否存疑
     */
    @ApiModelProperty(value = "是否存疑", position = 16)
    private String isQuestion;

    /**
     * 是否原件
     */
    @ApiModelProperty(value = "是否原件", position = 17)
    private String isFielCopy;


    /**
     * 存疑处理人id
     */
    @ApiModelProperty(value = "存疑处理人id", position = 20)
    private String resolveQuestionId;

    /**
     * 存疑处理时间
     */
    @ApiModelProperty(value = "存疑处理时间", position = 21)
    private LocalDateTime resolveQuestionTiem;


}
