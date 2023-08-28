package com.fudian.dahc.pojo.entity.trueingManage;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】对象 dahc_hcx_trueing_management
 *
 * @author fudian
 * @date 2023-02-09
 */
@Data
@TableName(value = "dahc_hcx_trueing_management")
public class DahcHcxTrueingManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Null(message = "新增时主键id不为空", groups = AddGroup.class)
    @NotNull(message = "修改时主键id不能为空", groups = UpdateGroup.class)
    @ApiModelProperty(value = "主键id(新增时主键id不为空,修改时主键id不能为空)", required = true)
    private String id;

    /**
     * 核查项名称
     */
    @NotBlank(message = "核查项名称不能为空")
    @ApiModelProperty(value = "核查项名称(长度min:2,max:100)", required = true)
    @Excel(name = "核查项名称")
    private String trueingName;

    /**
     * 档案类型
     */
    @ApiModelProperty(value = "档案类型")
    @Excel(name = "档案类型")
    private String tableType;


    /**
     * 核查项详情
     */
    @ApiModelProperty(value = "核查项描述(长度min:0,max:100)")
    @Excel(name = "核查项描述")
    private String trueingDesc;

    /**
     * 核查项备注
     */
    @ApiModelProperty(value = "核查项备注(长度min:0,max:100)")
    @Excel(name = "核查项备注")
    private String trueingRemark;

    /**
     * 判断展示那个页面
     */
    @ApiModelProperty(value = "判断展示那个页面", required = true)
    @Excel(name = "判断展示那个页面")
    private String trueingType;

    /**
     * 是否展示件号输入框(0-展示，1-不展示)
     */
    @ApiModelProperty(value = "是否展示件号输入框(0-展示，1-不展示)", allowableValues = "0,1", required = true)
    @Excel(name = "是否展示件号输入框(0-展示，1-不展示)")
    private String showPiece;

    /**
     * 是否展示著录框（0-展示，1-不展示）
     */
    @ApiModelProperty(value = "是否展示著录框(0-展示，1-不展示)", allowableValues = "0,1", required = true)
    @Excel(name = "是否展示著录框", readConverterExp = "0-展示，1-不展示")
    private String showRecord;

    /**
     * 核查项范围（treeId)
     */
    @ApiModelProperty(value = "核查项范围(长度min:2,max:100)", required = true)
    @Excel(name = "核查项范围", readConverterExp = "核查项范围（treeId)")
    private String trueingScopeStair;

    /**
     * 是否展示页号框（0-展示，1-不展示）
     */
    @ApiModelProperty(value = "是否展示页号框(0-展示，1-不展示)", allowableValues = "0,1", required = true)
    @Excel(name = "是否展示页号框", readConverterExp = "0-展示，1-不展示")
    private String showPageNumber;

    /**
     * 档案级别（0-一级 1-二级 2-三级）
     */
    @ApiModelProperty(value = "档案级别(0-一级,1-二级,2-三级)", allowableValues = "0,1,2", required = true)
    @Excel(name = "档案级别", readConverterExp = "0-一级,1-二级,2-三级")
    private Long trueingTreeRank;

    @ApiModelProperty("检查Stas(List)")
    @TableField(exist = false)
    private List<Map<Object, String>> examineStas;

    @ApiModelProperty("检查Stas字符串(String)")
    @TableField(exist = false)
    private String examineStasString;

    @ApiModelProperty("转换Id(String[])")
    @TableField(exist = false)
    private String[] transitionIds;

}
