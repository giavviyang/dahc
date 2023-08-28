package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/2/8
 */
@Data
public class ProjectTableQuery extends BaseQuery {
    @ApiModelProperty(value = "项目名")
    private String projectTableName;
    @ApiModelProperty(value = "项目描述")
    private String projectTableDesc;
    @ApiModelProperty(value = "项目状态")
    private Integer projectState;
    @ApiModelProperty(value = "表名称")
    private Integer archiveLevelName;

}
