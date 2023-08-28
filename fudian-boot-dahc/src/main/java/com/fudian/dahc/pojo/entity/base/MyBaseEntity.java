package com.fudian.dahc.pojo.entity.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.dahc.common.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 2023/1/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
//    @Null(message = "添加时id必须为空", groups = {AddGroup.class})
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id,添加时id必须为空,修改时id不能为空", example = "123456789456", position = 1, required = true)
    private String id;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value = "创建者", position = 60, hidden = true)
    private Long createBy;

    @ApiModelProperty(value = "创建者", position = 66, hidden = true)
    @TableField(exist = false)
    private String createByName;

    @ApiModelProperty(value = "按钮名称", position = 69, hidden = true)
    @TableField(exist = false)
    private String buttonName;

    @ApiModelProperty(value = "按钮名称", position = 69, hidden = true)
    @TableField(exist = false)
    private boolean checkStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", position = 61, hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新者", position = 62, hidden = true)
    private Long updateBy;

    @ApiModelProperty(value = "更新者", position = 65, hidden = true)
    @TableField(exist = false)
    private String updateByName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", position = 63, hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    private void setData() {
        Long userId = 0L;
        try {
            userId = SecurityUtils.getUserId();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (id == null || "".equals(id)) {
                this.createBy = userId;
                this.createTime = LocalDateTime.now();
            } else {
                // 如果有id就是修改
                this.updateBy = userId;
                this.updateTime = LocalDateTime.now();
            }
        }
    }


}
