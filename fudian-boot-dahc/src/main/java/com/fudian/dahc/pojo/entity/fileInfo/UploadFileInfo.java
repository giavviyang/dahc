package com.fudian.dahc.pojo.entity.fileInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * wenbovo
 * 2023/3/24
 * FD-dahc-project
 */
@Data
@TableName(value = "dahc_file_info")
@ApiModel(value = "上传文件信息")
public class UploadFileInfo implements Serializable {

    private static final long serialVersionUID = 6969462437970901728L;

    //附件编号
    @ApiModelProperty(value = "附件编号")
    private String id;

    //附件名称
    @ApiModelProperty(value = "附件名称")
    private String filename;

    @ApiModelProperty(value = "命名")
    @TableField(exist = false)
    private String nameSearch;

    //附件MD5标识
    @ApiModelProperty(value = "附件MD5标识")
    private String identifier;

    //附件总大小
    @ApiModelProperty(value = "附件总大小")
    private Long totalSize;

    @TableField(exist = false)
    @ApiModelProperty(value = "附件总大小名")
    private String totalSizeName;

    //附件类型
    @ApiModelProperty(value = "附件类型")
    private String type;

    //附件存储地址
    @ApiModelProperty(value = "附件存储地址")
    private String location;

    //删除标志
    @ApiModelProperty(value = "删除标志")
    private String delFlag;

    //文件所属目标（项目、学生、档案等，预留字段）
    @ApiModelProperty(value = "文件所属目标（项目、学生、档案等，预留字段）")
    private String refProjectId;

    //上传人
    @ApiModelProperty(value = "上传人")
    private String uploadBy;

    //上传时间
    @ApiModelProperty(value = "上传时间")
    @TableField(exist = false)
    private Date uploadTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "uploadTimeString")
    private String uploadTimeString;

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
        if (1024 * 1024 > this.totalSize && this.totalSize >= 1024) {
            this.totalSizeName = String.format("%.2f", this.totalSize.doubleValue() / 1024) + "KB";
        } else if (1024 * 1024 * 1024 > this.totalSize && this.totalSize >= 1024 * 1024) {
            this.totalSizeName = String.format("%.2f", this.totalSize.doubleValue() / (1024 * 1024)) + "MB";
        } else if (this.totalSize >= 1024 * 1024 * 1024) {
            this.totalSizeName = String.format("%.2f", this.totalSize.doubleValue() / (1024 * 1024 * 1024)) + "GB";
        } else {
            this.totalSizeName = this.totalSize.toString() + "B";
        }
    }

}
