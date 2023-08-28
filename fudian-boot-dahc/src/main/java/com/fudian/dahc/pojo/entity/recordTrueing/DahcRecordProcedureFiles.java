package com.fudian.dahc.pojo.entity.recordTrueing;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudian.common.annotation.Excel;
import com.fudian.common.core.domain.BaseEntity;
import com.fudian.dahc.common.group.AddGroup;
import com.fudian.dahc.common.group.UpdateGroup;
import com.fudian.dahc.pojo.entity.base.MyBaseEntity;
import com.fudian.dahc.pojo.query.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.Date;

/**
 * 【请填写功能名称】对象 dahc_record_procedure_files
 *
 * @author fudian
 * @date 2023-02-24
 */
@Accessors(chain = true)
@Data
@ApiModel(value = "记录文件核查工序", parent = BaseEntity.class)
public class DahcRecordProcedureFiles   {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Null(message = "添加时id必须为空", groups = {AddGroup.class})
    @NotNull(message = "修改时id不能为空", groups = {UpdateGroup.class})
    @ApiModelProperty(value = "主键id,添加时id必须为空,修改时id不能为空", required = true)
    private String id;

    /**
     * 案卷id
     */
    @Excel(name = "案卷id")
    @ApiModelProperty(value = "案卷id", required = true)
    private String filesId;

    /**
     * 案卷档号（名称）
     */
    @Excel(name = "案卷档号（名称）")
    @ApiModelProperty(value = "案卷档号（名称）")
    private String filesName;

    /**
     * 当前工序id
     */
    @Excel(name = "当前工序id")
    @ApiModelProperty(value = "当前工序id", required = true)
    private String procedureId;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    @ApiModelProperty(value = "项目id", required = true)
    private String projectId;

    /**
     * 是否已完成当前工序核查
     */
    @Excel(name = "是否已完成当前工序核查 0-未领取，1-已核查 2-已领取")
    @ApiModelProperty(value = "是否已完成当前工序核查 0-未领取，1-已核查 2-已领取", allowableValues = "0,1,2", required = true)
    private String isProcedureInspect;

    /**
     * 核查人id
     */
    @Excel(name = "核查人id")
    @ApiModelProperty(value = "核查人id", required = true)
    private Long inspectId;

    /**
     * 解决存疑人员id
     */
    @Excel(name = "解决存疑人员id")
    @ApiModelProperty(value = "解决存疑人员id", required = true)
    private Long impeachSolveId;

    /**
     * 是否存疑过
     */
    @Excel(name = "是否存疑过")
    @ApiModelProperty(value = "是否存疑过")
    private Long isImpeach;

    /*领取时间*/
    @ApiModelProperty(value = "领取时间")
    private Date getTime;

    /*是否是 ‘返回’保存的数据(0- 不是 1-是)*/
    @ApiModelProperty(value = "是否是 ‘返回’保存的数据(0- 不是 1-是)", allowableValues = "0,1")
    private Long isReturnSave;

    /*是否完成核查（0-是 ， 1-不是）*/
    @ApiModelProperty(value = "是否完成核查（0-是 ， 1-不是）", allowableValues = "0,1")
    private Long isAccomplishInspect;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 完成时间
     */
    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate accomplishTime;

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

    /*档号 存放的数据库表名*/
    @TableField(exist = false)
    @ApiModelProperty(value = "档号 存放的数据库表名")
    private String fileTableName;


    /*档号 存放的数据库表的字段名称*/
    @TableField(exist = false)
    @ApiModelProperty(value = "档号 存放的数据库表的字段名称")
    private String fileTableNameAttr;

    @TableField(exist = false)
    @ApiModelProperty(value = "工序核查范围")
    private String trueingId;

    @TableField(exist = false)
    @ApiModelProperty(value = "按钮名称")
    private String buttonName;

    @TableField(exist = false)
    @ApiModelProperty(value = "核查状态")
    private boolean checkStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "文件id集合")
    private String[] fileIds;


    public DahcRecordProcedureFiles(String filesId, String filesName, String procedureId, String projectId, String isProcedureInspect,Date createTime) {
        this.filesId = filesId;
        this.filesName = filesName;
        this.procedureId = procedureId;
        this.projectId = projectId;
        this.isProcedureInspect = isProcedureInspect;
        this.createTime = createTime;
    }

    public DahcRecordProcedureFiles() {
    }

    public DahcRecordProcedureFiles(String id, String filesId, String filesName, String procedureId, String projectId, String isProcedureInspect, Long inspectId, Long impeachSolveId, Long isImpeach, Date getTime, String fileTableName, String fileTableNameAttr) {
        this.id = id;
        this.filesId = filesId;
        this.filesName = filesName;
        this.procedureId = procedureId;
        this.projectId = projectId;
        this.isProcedureInspect = isProcedureInspect;
        this.inspectId = inspectId;
        this.impeachSolveId = impeachSolveId;
        this.isImpeach = isImpeach;
        this.getTime = getTime;
        this.fileTableName = fileTableName;
        this.fileTableNameAttr = fileTableNameAttr;
    }
}
