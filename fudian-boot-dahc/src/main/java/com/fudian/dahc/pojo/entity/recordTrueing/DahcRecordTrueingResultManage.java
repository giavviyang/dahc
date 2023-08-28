package com.fudian.dahc.pojo.entity.recordTrueing;

import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 【请填写功能名称】对象 dahc_record_trueing_result_manage
 *
 * @author fudian
 * @date 2023-02-21
 */
@Data
public class DahcRecordTrueingResultManage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 档案类型id
     */
    @Excel(name = "档案类型id")
    @ApiModelProperty(value = "档案类型id", required = true)
    private String archiveTypeId;

    /**
     * 档案类型的表名
     */
    @Excel(name = "档案类型的表名")
    @ApiModelProperty(value = "档案类型的表名")
    private String archiveTypeName;

    /**
     * 档案级别（案卷-0，案件-1）
     */
    @Excel(name = "档案级别", readConverterExp = "案=卷-0，案件-1")
    @ApiModelProperty(value = "档案级别（案卷-0，案件-1）", allowableValues = "0,1")
    private Long archiveTypeLv;

    /**
     * 核查数据id
     */
    @Excel(name = "核查数据id")
    @ApiModelProperty(value = "核查数据id", required = true)
    private String fileId;

    /**
     * 页数
     */
    @Excel(name = "页数")
    @ApiModelProperty(value = "页数")
    private String pageNumFile;

    /**
     * 著录项
     */
    @Excel(name = "著录项")
    @ApiModelProperty(value = "著录项")
    private String record;

    /**
     * 件数
     */
    @Excel(name = "件数")
    @ApiModelProperty(value = "件数")
    private String numberOfCases;

    /**
     * 核查结果id
     */
    @Excel(name = "核查结果id")
    @ApiModelProperty(value = "核查结果id", required = true)
    private String inspectResultId;

}
