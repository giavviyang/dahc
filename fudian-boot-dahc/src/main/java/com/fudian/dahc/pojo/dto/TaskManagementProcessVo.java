package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskManagementProcessVo extends DahcRecordProcedureFiles {

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述(长度min:0,max:100)", position = 1)
    private String procedureDesc;


    @ApiModelProperty(value = "备注(长度min:0,max:100)", position = 2)
    private String remark;


    //@ApiModelProperty(value = "档案id", position = 3)
    //private String queryFilesId;



}
