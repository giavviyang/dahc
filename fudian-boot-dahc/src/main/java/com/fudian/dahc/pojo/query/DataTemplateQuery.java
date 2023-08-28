package com.fudian.dahc.pojo.query;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 2023/2/20
 */
@Data
public class DataTemplateQuery extends BaseQuery {

    /**
     * 档案类型id
     */
    @TableField(exist = false)
    @NotBlank(message = "档案类型id不能为空")
    @ApiModelProperty(value = "档案类型id不能为空", required = true)
    private Long archiveTypeId;

    /**
     * 档案级别不能为空
     */
    @TableField(exist = false)
    @Max(value = 2, message = "文件类型只能是1或2")
    @Min(value = 1, message = "文件类型只能是1或2")
    @NotBlank(message = "档案级别id不能为空")
    @ApiModelProperty(value = "文件级或案卷级", required = true, allowableValues = "1,2")
    private Integer level;
}
