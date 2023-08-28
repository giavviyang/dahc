package com.fudian.dahc.pojo.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】对象 dahc_sys_dict_data
 *
 * @author fudian
 * @date 2023-02-08
 */
@Data
@TableName(value = "dahc_sys_dict_data")
public class DahcSysDictData
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @ApiModelProperty(value = "主键id", position = 0)
    private String id;

    /** 代码类型 */
    @ApiModelProperty(value = "代码类型", position = 1)
    @Excel(name = "代码类型")
    private String dictType;

    /** 代码 */
    @Excel(name = "代码")
    @ApiModelProperty(value = "代码", position = 2)
    private String dictCode;

    /** 父级代码 */
    @ApiModelProperty(value = "父级代码", position = 3)
    @Excel(name = "父级代码")
    private String eparentCode;

    /** 代码全称 */
    @ApiModelProperty(value = "代码全称", position = 4)
    @Excel(name = "代码全称")
    private String fullName;

    /** 代码简称 */
    @ApiModelProperty(value = "代码简称", position = 5)
    @Excel(name = "代码简称")
    private String nickerName;

    /** 英文简称 */
    @ApiModelProperty(value = "英文简称", position = 6)
    @Excel(name = "英文简称")
    private String pinyin;

    /** 代码属性 */
    @ApiModelProperty(value = "代码属性", position = 7)
    @Excel(name = "代码属性")
    private String codeProperty;

    /** 代码属性 */
    @ApiModelProperty(value = "备注", position = 10)
    @Excel(name = "备注")
    private String remark;

    /** 状态（0正常 1停用） */
    @ApiModelProperty(value = "状态（0正常 1停用）", position = 8)
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private Long codeAttr;

    /** 级别M-父节点，C-一级子节点，B-二级子节点） */
    @ApiModelProperty(value = "级别M-父节点，C-一级子节点，B-二级子节点）", position = 9)
    @Excel(name = "级别M-父节点，C-一级子节点，B-二级子节点）")
    private Long type;

    @TableField(exist = false)
    private List<DahcSysDictData> children;


    /*是否为档案类型新增的数据*/
    @TableField(exist = false)
    private Integer statr;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty(value = "更新者", hidden = true)
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


    /**
     * 页容量
     */
    @ApiModelProperty(value = "页容量")
    @TableField(exist = false)
    private Integer pageSize;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    @TableField(exist = false)
    private Integer pageNum;


    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getDictType()
    {
        return dictType;
    }
    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public String getDictCode()
    {
        return dictCode;
    }
    public void setEparentCode(String eparentCode)
    {
        this.eparentCode = eparentCode;
    }

    public String getEparentCode()
    {
        return eparentCode;
    }
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getFullName()
    {
        return fullName;
    }
    public void setNickerName(String nickerName)
    {
        this.nickerName = nickerName;
    }

    public String getNickerName()
    {
        return nickerName;
    }
    public void setPinyin(String pinyin)
    {
        this.pinyin = pinyin;
    }

    public String getPinyin()
    {
        return pinyin;
    }
    public void setCodeProperty(String codeProperty)
    {
        this.codeProperty = codeProperty;
    }

    public String getCodeProperty()
    {
        return codeProperty;
    }
    public void setCodeAttr(Long codeAttr)
    {
        this.codeAttr = codeAttr;
    }

    public Long getCodeAttr()
    {
        return codeAttr;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("dictType", getDictType())
            .append("dictCode", getDictCode())
            .append("eparentCode", getEparentCode())
            .append("fullName", getFullName())
            .append("nickerName", getNickerName())
            .append("pinyin", getPinyin())
            .append("codeProperty", getCodeProperty())
            .append("codeAttr", getCodeAttr())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("type", getType())
            .toString();
    }
}
