package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcArchiveType;
import com.fudian.dahc.pojo.entity.business.DahcModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 2023/3/15
 */
@Data
public class DahcArchiveTypeAndModelVo extends DahcArchiveType {

    @ApiModelProperty(value = "项目名(长度min:2,max:100)")
    private String projectName;

    /**
     * 创建人名
     */
    @ApiModelProperty(value = "创建人名")
    private String createByName;

    /**
     * 更新人名
     */
    @ApiModelProperty(value = "更新人名")
    private String updateByName;

    /**
     * 模板集合
     */
    @ApiModelProperty(value = "模板集合")
    private List<DahcModel> dahcModelLv;
}
