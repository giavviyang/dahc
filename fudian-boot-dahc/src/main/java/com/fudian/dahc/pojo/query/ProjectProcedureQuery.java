package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/2/8
 */
@Data
public class ProjectProcedureQuery extends BaseQuery {

    @ApiModelProperty(value = "工序名")
    private String projectProcedureName;
    @ApiModelProperty(value = "工序描述")
    private String projectProcedureDesc;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value = "id")
    private String id;
}
