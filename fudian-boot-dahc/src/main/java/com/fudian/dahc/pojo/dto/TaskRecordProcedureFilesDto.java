package com.fudian.dahc.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TaskRecordProcedureFilesDto  extends DahcRecordProcedureFiles {

    @ApiModelProperty("集合")
    private List<String> ids;

    @ApiModelProperty("档号名称")
    @TableField(exist = false)
    private String caseNum;

    @ApiModelProperty("档号的attr")
    @TableField(exist = false)
    private String attrOrder;




}
