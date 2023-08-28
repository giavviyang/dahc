package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/2/8
 */
@Data
public class ModelQuery extends BaseQuery {

    @ApiModelProperty(value = "模板id", example = "123", position = 11)
    private String id;
    @ApiModelProperty(value = "模板名", example = "导入模板", position = 12)
    private String modelName;
    @ApiModelProperty(value = "描述", example = "导入模板", position = 13)
    private String modelDesc;
    @ApiModelProperty(value = "档案id", example = "153", position = 14)
    private String archiveTypeId;
}
