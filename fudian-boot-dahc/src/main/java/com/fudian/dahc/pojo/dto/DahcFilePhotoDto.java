package com.fudian.dahc.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fudian.dahc.pojo.entity.business.DahcFilePhoto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 2023/2/21
 */
@Data
@ApiModel(value = "图片信息关联表", parent = DahcFilePhoto.class)
public class DahcFilePhotoDto extends DahcFilePhoto {

    /**
     * 图片ID
     */
    @ApiModelProperty(value = "图片ID1")
    @TableField(exist = false)
    private String id1;

    /**
     * 图片ID
     */
    @ApiModelProperty(value = "图片ID2")
    @TableField(exist = false)
    private String id2;


    @ApiModelProperty(value = "案卷表名称")
    @TableField(exist = false)
    private String archiveLevelName;

    @ApiModelProperty(value = "项目名称")
    @TableField(exist = false)
    private String projectName;

    @ApiModelProperty(value = "档号在那个attr")
    @TableField(exist = false)
    private String attrOrder;

    @ApiModelProperty(value = "案件档号")
    @TableField(exist = false)
    private String caseFileNumber;


    @ApiModelProperty(value = "状态,0-加在本页前方 1-本页后方", allowableValues = "0,1")
    /*状态 0-加在本页前方 1-本页后方*/
    private String radio;

    /*当前页数*/
    @ApiModelProperty(value = "当前页数")
    private Integer theCurrentNumberOfPages;

    @TableField(exist = false)
    @ApiModelProperty(value = "工序id")
    private String procedureId;


    @TableField(exist = false)
    @ApiModelProperty(value = "图片id")
    private String filePhotoId;


    @TableField(exist = false)
    @ApiModelProperty(value = "按钮名称")
    private String buttonName;


    @TableField(exist = false)
    @ApiModelProperty(value = "修改范围-起始页数")
    private Integer startPages;

    @TableField(exist = false)
    @ApiModelProperty(value = "修改范围-结束页数")
    private Integer comeToAnEndPages;


    @TableField(exist = false)
    @ApiModelProperty(value = "起始页数")
    private Integer theNumberOfStartingPages;

    @TableField(exist = false)
    @ApiModelProperty(value = "核查状态")
    private boolean checkStatus;


    @TableField(exist = false)
    @ApiModelProperty(value = "案卷ID集合")
    private List<String> fileIds;

}
