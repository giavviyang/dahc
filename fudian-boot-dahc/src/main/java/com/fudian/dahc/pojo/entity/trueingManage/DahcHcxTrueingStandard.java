package com.fudian.dahc.pojo.entity.trueingManage;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 dahc_hcx_trueing_standard
 *
 * @author fudian
 * @date 2023-02-09
 */
@Data
@TableName(value = "dahc_hcx_trueing_standard")
@ApiModel(value = "核查项核查标准")
public class DahcHcxTrueingStandard {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id(长度-20)")
    private String id;

    /**
     * 关联核查项主键id
     */
    @Excel(name = "关联核查项主键id")
    @ApiModelProperty(value = "关联核查项主键id(长度-20)", required = true)
    private String trueingId;

    /**
     * 核查标准
     */
    @Excel(name = "核查标准")
    @ApiModelProperty(value = "核查标准(长度min:0,max:100)", required = true)
    private String trueingStandard;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTrueingId(String trueingId) {
        this.trueingId = trueingId;
    }

    public String getTrueingId() {
        return trueingId;
    }

    public void setTrueingStandard(String trueingStandard) {
        this.trueingStandard = trueingStandard;
    }

    public String getTrueingStandard() {
        return trueingStandard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("trueingId", getTrueingId())
                .append("trueingStandard", getTrueingStandard())
                .toString();
    }
}
