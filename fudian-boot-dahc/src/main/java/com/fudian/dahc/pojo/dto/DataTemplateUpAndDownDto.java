package com.fudian.dahc.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class DataTemplateUpAndDownDto extends DahcDataTemplateDto {


    @ApiModelProperty(value = "id1")
    private String id1;
    @ApiModelProperty(value = "id2")
    private String id2;

    /*表名称*/
    @ApiModelProperty(value = "表名称(长度min:2,max:100)")
    private String caseTableName;

    /*档案类型*/
    //@ApiModelProperty(value = "档案类型")
    //private String archiveTypeId;

    /*元数据名称*/
    @ApiModelProperty(value = "元数据名称(长度min:2,max:100)")
    private String  metadataName;

    @TableField(exist = false)
    @ApiModelProperty(value = "工序ID")
    private String procedureId;
}
