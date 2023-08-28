package com.fudian.dahc.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProcedureInspectQuery {

    /*项目id*/
    @ApiModelProperty(value = "项目id")
    private String projectId;
    /*工序id*/
    @ApiModelProperty(value = "工序id")
    private String procedureId;

}
