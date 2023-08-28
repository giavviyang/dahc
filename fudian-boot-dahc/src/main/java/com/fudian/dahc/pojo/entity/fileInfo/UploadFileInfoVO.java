package com.fudian.dahc.pojo.entity.fileInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * wenbovo
 * 2023/3/24
 * FD-dahc-project
 */
@Data
@ApiModel(value = "上传文件信息信息层")
public class UploadFileInfoVO implements Serializable {
    private static final long serialVersionUID = -668666237985927833L;

    //附件编号
    @ApiModelProperty(value = "附件编号")
    private String id;

    //附件类型
    @ApiModelProperty(value = "附件类型")
    private String fileType;

    //附件名称
    @ApiModelProperty(value = "附件名称")
    private String name;

    //附件总大小
    @ApiModelProperty(value = "附件总大小")
    private Long size;

    @ApiModelProperty(value = "档案类型id")
    private String archivesId;

    //附件地址
    @ApiModelProperty(value = "附件地址")
    private String relativePath;

    //附件MD5标识
    @ApiModelProperty(value = "附件MD5标识")
    private String uniqueIdentifier;

    //附件所属项目ID
    @ApiModelProperty(value = "附件所属项目ID")
    private String refProjectId;

    @TableField(exist = false)
    @ApiModelProperty(value = "案件名称")
    private String caseFileNumber;

    @TableField(exist = false)
    @ApiModelProperty(value = "案件Id")
    private String pid;

    @TableField(exist = false)
    @ApiModelProperty(value = "案卷名称")
    private String keyName;

    @TableField(exist = false)
    @ApiModelProperty(value = "工序ID")
    private String procedureId;

    @TableField(exist = false)
    @ApiModelProperty(value = "核查状态")
    private boolean checkStatus;


}
