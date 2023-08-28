package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 2023/2/8
 */
@Data
public class ArchiveTypeQuery extends BaseQuery {

    @ApiModelProperty(value = "档案类型id", required = true)
    private Long id;
    @ApiModelProperty(value = "档案类型名")
    private String archiveTypeName;
    @ApiModelProperty(value = "档案类型描述")
    private String archiveTypeDesc;

    @Max(value = 2, message = "文件类型只能是1或2")
    @Min(value = 1, message = "文件类型只能是1或2")
    @ApiModelProperty(value = "案卷或者文件级", required = true, allowableValues = "1,2")
    private Integer level;
}
