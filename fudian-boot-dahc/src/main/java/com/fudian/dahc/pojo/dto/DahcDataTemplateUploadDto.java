package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * wenbovo
 * 2023/4/11
 * FD-dahc-project
 */
@Data
public class DahcDataTemplateUploadDto {

    @ApiModelProperty(value = "文件", required = true)
    private MultipartFile[] file;

    @ApiModelProperty(value = "档案类型id", required = true)
    private String archivesId;
    @ApiModelProperty(value = "模板id", required = true)
    private String id;
    @ApiModelProperty(value = "模板id", required = true)
    private String modelId;
    @ApiModelProperty(value = "项目id", required = true)
    private String projectId;
    @ApiModelProperty(value = "案卷或文件表等级", required = true, allowableValues = "1,2")
    private Integer tableLv;

    @ApiModelProperty(value = "批次号", required = true)
    private List<String> batchNoList;
}
