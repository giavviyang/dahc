package com.fudian.dahc.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 dahc_project_procedure_trueing
 *
 * @author fudian
 * @date 2023-02-13
 */
@Data
public class DahcProjectProcedureTrueing extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "工序id", example = "1",required = true)
    private String procedureId;

    /**
     * $column.columnComment
     */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty(value = "核查项id", example = "1",required = true)
    private String trueingId;

    @ApiModelProperty(value = "排序", example = "1",required = true)
    private Long sort;

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setTrueingId(String trueingId) {
        this.trueingId = trueingId;
    }

    public String getTrueingId() {
        return trueingId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("procedureId", getProcedureId())
                .append("trueingId", getTrueingId())
                .toString();
    }
}
