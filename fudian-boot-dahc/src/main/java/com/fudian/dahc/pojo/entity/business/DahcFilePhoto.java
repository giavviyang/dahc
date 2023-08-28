package com.fudian.dahc.pojo.entity.business;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.utils.uuid.IdUtils;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Accessors(chain = true)
@ApiModel(value = "图片信息关联表 businessBulkHook")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "dahc_file_photo")
public class DahcFilePhoto implements Serializable {


    private static final long serialVersionUID = 1L;

    @NotNull(message = "新增时主键不为空", groups = AddGroup.class)
    @NotNull(message = "修改时主键不能为空", groups = UpdateGroup.class)
    @ApiModelProperty(value = "主键(新增时主键不为空,修改时主键不能为空)", position = 0, required = true)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 例如:280-XG-2020-永久-0012-004
     */
    @ApiModelProperty(value = "例如:280-XG-2020-永久-0012-004", position = 1)
    private String keyName;

    @NotBlank(message = "项目id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "项目id", required = true)
    private String projectId;

    /**
     * 关联id
     */
    @NotBlank(message = "关联id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "关联id", required = true)
    private String pid;


    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名(长度min:1,max:255)")
    private String fileName;

    /**
     * 文件格式
     */
    @ApiModelProperty(value = "文件格式")
    private String fileType;


    /**
     * 文件地址
     */
    @NotBlank(message = "关联id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "文件地址(长度min:1,max:255)", position = 2, required = true)
    private String filePath;

    /**
     * 宽
     */
    @ApiModelProperty(value = "宽", position = 3)
    private Double width;

    /**
     * 高
     */
    @ApiModelProperty(value = "高", position = 4)
    private Double height;

    /**
     * 图片规格
     */
    @ApiModelProperty(value = "图片规格", position = 5)
    private Double photoSize;

    /**
     * 页数
     */
    @ApiModelProperty(value = "页数(min:0)", position = 6)
    private Long pageNum;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小", position = 7)
    private Long fileSize;

    /**
     * 文件规格(A3,A4)
     */
    @ApiModelProperty(value = "文件规格(A3,A4)", position = 8)
    private String fileSpec;

    @ApiModelProperty(value = "文件状态")
    private Integer fileStatus;

    /*是否删除 0-存在 1-删除*/
    @ApiModelProperty(value = "是否删除 0-存在 1-删除", allowableValues = "0,1")
    private Long fileDelete;

    @ApiModelProperty(value = "档案类型id")
    @TableField(exist = false)
    private String archivesId;

    public DahcFilePhoto(String keyName, String pid, String projectId, String fileName, String fileType, String filePath, Double width, Double height, Double photoSize, Long pageNum, Long fileSize, String fileSpec, Integer fileStatus) {
        this.keyName = keyName;
        this.pid = pid;
        this.projectId = projectId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.photoSize = photoSize;
        this.pageNum = pageNum;
        this.fileSize = fileSize;
        this.fileSpec = fileSpec;
        this.fileStatus = fileStatus;
        //this.fileDelete = fileDelete;
    }

    public DahcFilePhoto(String id,String keyName, String pid, String projectId, String fileName, String fileType, String filePath, Double width, Double height, Double photoSize, Long pageNum, Long fileSize, String fileSpec, Integer fileStatus) {
        this.id = id;
        this.keyName = keyName;
        this.pid = pid;
        this.projectId = projectId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
        this.photoSize = photoSize;
        this.pageNum = pageNum;
        this.fileSize = fileSize;
        this.fileSpec = fileSpec;
        this.fileStatus = fileStatus;
        //this.fileDelete = fileDelete;
    }
}
