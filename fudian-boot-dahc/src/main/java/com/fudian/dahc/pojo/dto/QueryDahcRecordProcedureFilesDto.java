package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.recordTrueing.DahcRecordProcedureFiles;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/3/15
 */
@Data
public class QueryDahcRecordProcedureFilesDto extends DahcRecordProcedureFiles {


    @ApiModelProperty(value = "是否合格（unqualified-不合格  qualified-合格）", required = true)
    private String checkResultState;

    @ApiModelProperty(value = "是否存疑（change-更改 impeach-存疑）", required = true)
    private String isQuestion;



}
