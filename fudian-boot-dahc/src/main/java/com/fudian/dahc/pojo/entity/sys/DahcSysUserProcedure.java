package com.fudian.dahc.pojo.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 dahc_sys_user_procedure
 *
 * @author fudian
 * @date 2023-02-16
 */
@Data
public class DahcSysUserProcedure extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId
    @Excel(name = "用户id", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "用户id", position = 0)
    private String userId;

    /** $column.columnComment */
    @ApiModelProperty(value = "工序id", position = 1)
    @Excel(name = "工序id", readConverterExp = "$column.readConverterExp()")
    private String procedureId;

    @ApiModelProperty(value = "项目id", position = 1)
    @Excel(name = "项目id", readConverterExp = "$column.readConverterExp()")
    private String projectId;

}
