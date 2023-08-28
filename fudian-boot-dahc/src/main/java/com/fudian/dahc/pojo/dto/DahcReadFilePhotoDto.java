package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 2023/2/21
 */
@Data
public class DahcReadFilePhotoDto {


    @NotBlank(message = "档案id不能为空")
    @ApiModelProperty(value = "档案id", required = true)
    private String archivesId;


    @NotBlank(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id(长度-20)", required = true)
    private String projectId;

    /**
     * 文件地址
     */
    @NotBlank(message = "文件地址不能为空")
    @ApiModelProperty(value = "文件地址(长度min:1,max:100)", position = 2, required = true)
    private String filePath;
}
