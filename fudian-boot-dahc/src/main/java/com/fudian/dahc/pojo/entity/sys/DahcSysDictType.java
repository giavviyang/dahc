package com.fudian.dahc.pojo.entity.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 字典类型对象 dahc_sys_dict_type
 *
 * @author fudian
 * @date 2023-02-08
 */
@Data
@TableName(value = "dahc_sys_dict_type")
public class DahcSysDictType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @TableId("dict_id")
    @ApiModelProperty(value = "主键id", position = 0)
    private String dictId;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称", position = 1)
    @Excel(name = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @ApiModelProperty(value = "字典类型", position = 2)
    @Excel(name = "字典类型")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态 0=正常,1=停用", position = 3, allowableValues = "0,1")
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictType() {
        return dictType;
    }




    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictId", getDictId())
                .append("dictName", getDictName())
                .append("dictType", getDictType())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
