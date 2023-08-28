package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 2023/2/17
 */
@Data
public class DahcCheckResultQuery extends BaseQuery {

    @ApiModelProperty(value = "档案类型id", required = true)
    private Long archiveTypeId;
    @Max(value = 2, message = "文件类型只能是1或2")
    @Min(value = 1, message = "文件类型只能是1或2")
    @ApiModelProperty(value = "文件级或案卷级", required = true, allowableValues = "1,2")
    private Integer level;
    @ApiModelProperty(value = "id", required = true)
    private String id;
}
