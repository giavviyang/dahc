package com.fudian.dahc.pojo.query;

import com.fudian.dahc.pojo.dto.DahcArchiveTypeAndMetadataDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 2023/2/8
 */
@Data
@Accessors(chain = true)
public class ArchiveAndMetadataVO {

    @ApiModelProperty(value = "档案类型id", required = true)
    private Long archiveTypeId;
    @ApiModelProperty(value = "数据库表名", required = true)
    private String archiveLevelName;

    @Size(min = 1, message = "元数据集合为空,请重新设置")
    @ApiModelProperty(value = "元数据集合")
    private List<DahcArchiveTypeAndMetadataDto> metadataDtos;

}
