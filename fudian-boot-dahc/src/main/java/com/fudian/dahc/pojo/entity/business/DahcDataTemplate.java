package com.fudian.dahc.pojo.entity.business;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fudian.common.annotation.Excel;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@TableName(value = "dahc_file")
@ApiModel(value = "案卷或文件数据表")
public class DahcDataTemplate extends MyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 备份状态(1是|0否) 单独添加的直接设置为1
     */
    @ApiModelProperty(value = "备份状态(1是|0否) 单独添加的直接设置为1", position = 1)
    private String status;

    @NotNull(message = "项目id不能为空", groups = {AddGroup.class})
    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "批次号")
    private String batchNo;

    @TableField(value = "remarks")
    @ApiModelProperty(value = "描述(长度min:0,max:100)")
    private String desc;

    @ApiModelProperty(value = "父id")
    private String pid;

    /**
     * attr0
     */
    @Excel(name = "attr0")
    @ApiModelProperty(value = "attr0", position = 2)
    private String attr0;

    /**
     * attr1
     */
    @Excel(name = "attr1")
    @ApiModelProperty(value = "attr1", position = 3)
    private String attr1;

    /**
     * attr2
     */
    @Excel(name = "attr2")
    @ApiModelProperty(value = "attr2", position = 4)
    private String attr2;

    /**
     * attr3
     */
    @Excel(name = "attr3")
    @ApiModelProperty(value = "attr3", position = 5)
    private String attr3;

    /**
     * attr4
     */
    @Excel(name = "attr4")
    @ApiModelProperty(value = "attr4", position = 6)
    private String attr4;

    /**
     * attr5
     */
    @Excel(name = "attr5")
    @ApiModelProperty(value = "attr5", position = 7)
    private String attr5;

    /**
     * attr6
     */
    @Excel(name = "attr6")
    @ApiModelProperty(value = "attr6", position = 8)
    private String attr6;

    /**
     * attr7
     */
    @Excel(name = "attr7")
    @ApiModelProperty(value = "attr7", position = 9)
    private String attr7;

    /**
     * attr8
     */
    @Excel(name = "attr8")
    @ApiModelProperty(value = "attr8", position = 10)
    private String attr8;

    /**
     * attr9
     */
    @Excel(name = "attr9")
    @ApiModelProperty(value = "attr9", position = 11)
    private String attr9;

    /**
     * attr10
     */
    @Excel(name = "attr10")
    @ApiModelProperty(value = "attr10", position = 12)
    private String attr10;

    /**
     * attr11
     */
    @Excel(name = "attr11")
    @ApiModelProperty(value = "attr11", position = 13)
    private String attr11;

    /**
     * attr12
     */
    @Excel(name = "attr12")
    @ApiModelProperty(value = "attr12", position = 14)
    private String attr12;

    /**
     * attr13
     */
    @Excel(name = "attr13")
    @ApiModelProperty(value = "attr13", position = 15)
    private String attr13;

    /**
     * attr14
     */
    @Excel(name = "attr14")
    @ApiModelProperty(value = "attr14", position = 16)
    private String attr14;

    /**
     * attr15
     */
    @Excel(name = "attr15")
    @ApiModelProperty(value = "attr15", position = 17)
    private String attr15;

    /**
     * attr16
     */
    @Excel(name = "attr16")
    @ApiModelProperty(value = "attr16", position = 18)
    private String attr16;

    /**
     * attr17
     */
    @Excel(name = "attr17")
    @ApiModelProperty(value = "attr17", position = 19)
    private String attr17;

    /**
     * attr18
     */
    @Excel(name = "attr18")
    @ApiModelProperty(value = "attr18", position = 20)
    private String attr18;

    /**
     * attr19
     */
    @Excel(name = "attr19")
    @ApiModelProperty(value = "attr19", position = 21)
    private String attr19;

    /**
     * attr20
     */
    @Excel(name = "attr20")
    @ApiModelProperty(value = "attr20", position = 22)
    private String attr20;

    /**
     * attr21
     */
    @Excel(name = "attr21")
    @ApiModelProperty(value = "attr21", position = 23)
    private String attr21;

    /**
     * attr22
     */
    @Excel(name = "attr22")
    @ApiModelProperty(value = "attr22", position = 24)
    private String attr22;

    /**
     * attr23
     */
    @Excel(name = "attr23")
    @ApiModelProperty(value = "attr23", position = 25)
    private String attr23;

    /**
     * attr24
     */
    @Excel(name = "attr24")
    @ApiModelProperty(value = "attr24", position = 26)
    private String attr24;

    /**
     * attr25
     */
    @Excel(name = "attr25")
    @ApiModelProperty(value = "attr25", position = 27)
    private String attr25;

    /**
     * attr26
     */
    @Excel(name = "attr26")
    @ApiModelProperty(value = "attr26", position = 28)
    private String attr26;

    /**
     * attr27
     */
    @Excel(name = "attr27")
    @ApiModelProperty(value = "attr27", position = 29)
    private String attr27;

    /**
     * attr28
     */
    @Excel(name = "attr28")
    @ApiModelProperty(value = "attr28", position = 30)
    private String attr28;

    /**
     * attr29
     */
    @Excel(name = "attr29")
    @ApiModelProperty(value = "attr29", position = 31)
    private String attr29;

    /**
     * attr30
     */
    @Excel(name = "attr30")
    @ApiModelProperty(value = "attr30", position = 32)
    private String attr30;

    /**
     * attr31
     */
    @Excel(name = "attr31")
    @ApiModelProperty(value = "attr31", position = 33)
    private String attr31;

    /**
     * attr32
     */
    @Excel(name = "attr32")
    @ApiModelProperty(value = "attr32", position = 34)
    private String attr32;

    /**
     * attr33
     */
    @Excel(name = "attr33")
    @ApiModelProperty(value = "attr33", position = 35)
    private String attr33;

    /**
     * attr34
     */
    @Excel(name = "attr34")
    @ApiModelProperty(value = "attr34", position = 36)
    private String attr34;

    /**
     * attr35
     */
    @Excel(name = "attr35")
    @ApiModelProperty(value = "attr35", position = 37)
    private String attr35;

    /**
     * attr36
     */
    @Excel(name = "attr36")
    @ApiModelProperty(value = "attr36", position = 38)
    private String attr36;

    /**
     * attr37
     */
    @Excel(name = "attr37")
    @ApiModelProperty(value = "attr37", position = 39)
    private String attr37;

    /**
     * attr38
     */
    @Excel(name = "attr38")
    @ApiModelProperty(value = "attr38", position = 40)
    private String attr38;

    /**
     * attr39
     */
    @Excel(name = "attr39")

    @ApiModelProperty(value = "attr39", position = 41)
    private String attr39;

    @TableField(exist = false)
    @ApiModelProperty(value = "string类型id", position = 42)
    private String filesId;


}
