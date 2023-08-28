package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/2/8
 */
@Data
public class MetadataQuery extends BaseQuery {

    @ApiModelProperty(value = "元数据名", example = "档号", position = 10)
    private String metadataName;

    @ApiModelProperty(value = "元数据类型", example = "字符", position = 11)
    private String metadataType;

    @ApiModelProperty(value = "元数据描述", example = "文件唯一标识", position = 12)
    private String metadataDesc;
}
