package com.fudian.dahc.pojo.entity.log;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 dahc_check_record_log
 *
 * @author fudian
 * @date 2023-05-24
 */
@Data
@NoArgsConstructor
public class DahcCheckRecordLog
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 操作按钮名称 */
    @Excel(name = "操作按钮名称")
    private String buttonName;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目id */
    @Excel(name = "项目id")
    private String projectId;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String procedureName;

    /** 工序id */
    @Excel(name = "工序id")
    private String procedureId;

    /** 案卷操作数据表名称 */
    @Excel(name = "案卷操作数据表名称")
    private String caseTableName;

    /** 案件操作数据表名称 */
    @Excel(name = "案件操作数据表名称")
    private String fileTableName;

    /** 案卷档号 */
    @Excel(name = "案卷档号")
    @TableField(value = "case_file_number1")
    private String caseFileNumber1;

    /** 案件序号 */
    @Excel(name = "案件序号")
    @TableField(value = "case_number2")
    private String caseNumber2;

    /** 案卷id */
    @Excel(name = "案卷id")
    private String fileTableNameId;

    /** 案件id */
    @Excel(name = "案件id")
    private String caseNumberId;

    /** 操作的是否是图片数据（0-是 1-不是） */
    @Excel(name = "操作的是否是图片数据", readConverterExp = "0=是,1=不是")
    private Integer isImg;

    /** 图片页号 */
    @Excel(name = "图片页号")
    private String imgPageNumber;

    /** 源图片本地保存地址 */
    @Excel(name = "源图片本地保存地址")
    private String sourcePicturePath;

    /** 源图片名称 */
    @Excel(name = "源图片名称")
    private String sourcePictureName;

    /** 源图片数据保存表id */
    @Excel(name = "源图片数据保存表id")
    private String sourceFilePhotoId;

    /** 新增图片本地保存地址 */
    @Excel(name = "新增图片本地保存地址")
    private String insertSourcePicturePath;

    /** 新增图片名称 */
    @Excel(name = "新增图片名称")
    private String insertSourcePictureName;

    /** 新增图片数据保存表id */
    @Excel(name = "新增图片数据保存表id")
    private String insertFilePhotoId;

    /** 提交按钮（0-正常提交，1-存疑） */
    @Excel(name = "提交按钮", readConverterExp = "0=正常提交，1存疑")
    private Integer submitButton;

    /** 源数据库json数据 */
    @Excel(name = "源数据库json数据")
    private String sourceDataJson;

    /** 现数据库json数据 */
    @Excel(name = "现数据库json数据")
    private String nowDataJson;

    /**
     * 创建者
     */
    @Excel(name = "创建者")
    @ApiModelProperty(value = "创建者", hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    @Excel(name = "核查状态", readConverterExp = "0=正常提交，1存疑")
    @ApiModelProperty(value = "核查状态", hidden = true)
    private Integer checkStatus;

    /**
     * 页容量
     */
    @ApiModelProperty(value = "页容量")
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    @TableField(exist = false)
    private Integer pageNum;

    public DahcCheckRecordLog(
            String buttonName, String projectName, String projectId, String procedureName,
            String procedureId, String caseTableName, String fileTableName, String caseFileNumber,
            String caseNumber, String fileTableNameId, String caseNumberId, Integer isImg,
            String sourceDataJson, String nowDataJson, Integer submitButton, Integer checkStatus
    ) {
        this.buttonName = buttonName;
        this.projectName = projectName;
        this.projectId = projectId;
        this.procedureName = procedureName;
        this.procedureId = procedureId;
        this.caseTableName = caseTableName;
        this.fileTableName = fileTableName;
        this.caseFileNumber1 = caseFileNumber;
        this.caseNumber2 = caseNumber;
        this.fileTableNameId = fileTableNameId;
        this.caseNumberId = caseNumberId;
        this.isImg = isImg;
        this.sourceDataJson = sourceDataJson;
        this.nowDataJson = nowDataJson;
        this.submitButton = submitButton;
        this.checkStatus = checkStatus;

    }
    public DahcCheckRecordLog(
            String buttonName, String projectName, String projectId, String procedureName,
            String procedureId, String caseTableName, String fileTableName, String caseFileNumber,
            String caseNumber, String fileTableNameId, String caseNumberId, Integer isImg,
            String sourceDataJson, String nowDataJson,String imgPageNumber,String sourcePicturePath,
            String sourcePictureName, String sourceFilePhotoId,String insertSourcePicturePath,String insertSourcePictureName,
            String insertFilePhotoId,Integer checkStatus
    ) {
        this.buttonName = buttonName;
        this.projectName = projectName;
        this.projectId = projectId;
        this.procedureName = procedureName;
        this.procedureId = procedureId;
        this.caseTableName = caseTableName;
        this.fileTableName = fileTableName;
        this.caseFileNumber1 = caseFileNumber;
        this.caseNumber2 = caseNumber;
        this.fileTableNameId = fileTableNameId;
        this.caseNumberId = caseNumberId;
        this.isImg = isImg;
        this.sourceDataJson = sourceDataJson;
        this.nowDataJson = nowDataJson;
        this.imgPageNumber = imgPageNumber;
        this.sourcePicturePath = sourcePicturePath;
        this.sourcePictureName = sourcePictureName;
        this.sourceFilePhotoId = sourceFilePhotoId;
        this.insertSourcePicturePath = insertSourcePicturePath;
        this.insertSourcePictureName = insertSourcePictureName;
        this.insertFilePhotoId = insertFilePhotoId;
        this.checkStatus = checkStatus;

    }
}
