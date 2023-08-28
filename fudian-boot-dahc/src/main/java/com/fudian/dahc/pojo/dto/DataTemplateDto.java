package com.fudian.dahc.pojo.dto;

import com.fudian.dahc.pojo.entity.business.DahcDataTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * wenbovo
 * 2023/3/29
 * FD-dahc-project
 */
@Accessors(chain = true)
@Data
public class DataTemplateDto {

    /**
     * 档案id
     */
    @ApiModelProperty(value = "档案id")
    private String archivesId;

    /**
     * 档案类型id
     */
    @ApiModelProperty(value = "档案类型id")
    private Long archiveTypeId;

    /**
     * 数据级别
     */
    @ApiModelProperty(value = "数据级别 1-案卷 2-案件")
    private Integer level;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 项目id
     */
    @ApiModelProperty(value = "项目id")
    private String projectId;
    /**
     * 模板id
     */
    @ApiModelProperty(value = "模板id")
    private String modelId;
    /**
     * excel页
     */
    @ApiModelProperty(value = "excel页")
    private Integer sheetNum;
    /**
     * 表等级
     */
    @ApiModelProperty(value = "表等级")
    private Integer tableLv;
    /**
     * 下载地址
     */
    @ApiModelProperty(value = "下载地址(长度min:1,max:100)")
    private String url;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名(长度min:2,max:100)")
    private String fileName;
    /**
     * 分页数
     */
    @ApiModelProperty(value = "分页数")
    private Integer pageNum;
    /**
     * 分页量
     */
    @ApiModelProperty(value = "分页量")
    private Integer pageSize;
    /**
     * 表名
     */
    @ApiModelProperty(value = "表名(长度min:2,max:100)")
    private String tableName;
    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private String status;
    /**
     * 元数据名
     */
    @ApiModelProperty(value = "元数据名(长度min:1,max:100)")
    private String attrName;
    /**
     * 元数据数据名
     */
    @ApiModelProperty(value = "元数据数据名(长度min:1,max:100)")
    private String attr0;

    @ApiModelProperty(value = "批次号集合")
    private List<String> batchNoList;

    //    private List<String> batchNos;
    @ApiModelProperty(value = "档案id")
    private String batchNo;

    @ApiModelProperty(value = "描述(长度max:100)")
    private String desc;

    private List<RuleSerialNumberDto> list = new ArrayList<>();

    private List<DahcDataTemplate> dahcDataTemplateList;

    @ApiModelProperty(value = "案卷Id集合")
    private List<String> fileIds;


}
