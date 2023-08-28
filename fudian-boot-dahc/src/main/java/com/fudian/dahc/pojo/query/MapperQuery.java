package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/2/8
 */
@Data
public class MapperQuery extends BaseQuery {

    @ApiModelProperty(value = "映射名")
    private String mapperName;
    @ApiModelProperty(value = "映射描述")
    private String mapperDesc;
    @ApiModelProperty(value = "模板id")
    private String modelId;
}
