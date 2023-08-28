package com.fudian.dahc.pojo.entity.fileInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * wenbovo
 * 2023/3/24
 * FD-dahc-project
 */
@Data
@ApiModel(value = "上传结果")
public class UploadResult implements Serializable {

    private static final long serialVersionUID = -9000695051292877324L;

    //是否跳过上传（已上传的可以直接跳过，达到秒传的效果）
    @ApiModelProperty(value = "是否跳过上传（已上传的可以直接跳过，达到秒传的效果）")
    private boolean skipUpload;

    //已经上传的文件块编号，可以跳过，断点续传
    @ApiModelProperty(value = "已经上传的文件块编号，可以跳过，断点续传")
    private ArrayList<Integer> uploadedChunks;

    //返回结果码
    @ApiModelProperty(value = "返回结果码")
    private String status;

    //返回结果信息
    @ApiModelProperty(value = "返回结果信息")
    private String message;

    //已上传完整附件的地址
    @ApiModelProperty(value = "已上传完整附件的地址")
    private String location;

    @ApiModelProperty(value = "是否跳过上传")
    public boolean isSkipUpload() {
        return skipUpload;
    }
}
