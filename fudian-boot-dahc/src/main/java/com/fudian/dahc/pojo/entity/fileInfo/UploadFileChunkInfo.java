package com.fudian.dahc.pojo.entity.fileInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * wenbovo
 * 2023/3/24
 * FD-dahc-project
 */
@Data
@TableName(value = "dahc_file_chunk_info")
@ApiModel(value = "上传文件分片信息")
public class UploadFileChunkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", required = true)
    private String id;
    /**
     * 当前文件块，从1开始
     */
    @ApiModelProperty(value = "当前文件块，从1开始", required = true)
    private Integer chunkNumber;
    /**
     * 每块大小
     */
    @ApiModelProperty(value = "每块大小", required = true)
    private Long chunkSize;
    /**
     * 当前分块大小
     */
    @ApiModelProperty(value = "当前分块大小", required = true)
    private Long currentChunkSize;
    /**
     * 总大小
     */
    @ApiModelProperty(value = "总大小", required = true)
    private Long totalSize;
    /**
     * 文件标识
     */
    @ApiModelProperty(value = "文件标识", required = true)
    private String identifier;
    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名", required = true)
    private String filename;
    /**
     * 相对路径
     */
    @ApiModelProperty(value = "相对路径", required = true)
    private String relativePath;
    /**
     * 总块数
     */
    @ApiModelProperty(value = "总块数", required = true)
    private Integer totalChunks;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String type;

    /**
     * 块内容
     */
    @ApiModelProperty(value = "块内容", required = true)
    private transient MultipartFile upfile;


}
