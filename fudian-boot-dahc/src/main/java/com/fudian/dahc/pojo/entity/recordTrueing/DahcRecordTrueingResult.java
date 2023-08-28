package com.fudian.dahc.pojo.entity.recordTrueing;

import com.fudian.common.core.domain.BaseEntity;
import com.fudian.common.utils.SecurityUtils;
import com.fudian.common.utils.uuid.IdUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 【请填写功能名称】对象 dahc_record_trueing_result
 *
 * @author fudian
 * @date 2023-02-21
 */
@Data
public class DahcRecordTrueingResult extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增时为空,修改时不为空)", required = true)
    private String id;

    /**
     * 核查项名称
     */
    @ApiModelProperty(value = "核查项名称", required = true)
    private String trueingName;

    /**
     * 卷数据名称
     */
    @ApiModelProperty(value = "卷数据名称")
    private String rollId;

    /*卷数据id*/
    @ApiModelProperty(value = "卷数据id")
    private String fileId;

    /*档案结果记录表Id*/
    @ApiModelProperty(value = "档案结果记录表Id")
    private String recordProcedureFilesId;

    /**
     * 工序id
     */
    @ApiModelProperty(value = "工序id")
    private String procedureId;

    /**
     * 核查项id
     */
    @ApiModelProperty(value = "核查项id")
    private String trueingId;

    /**
     * 核查结果(0:合格|1:不合格|2:存疑|3:已修改)
     */
    @ApiModelProperty(value = "核查结果(0:合格|1:不合格|2:存疑|3:已修改)", allowableValues = "0,1,2")
    private String checkResultState;

    /**
     * 核查后处理方式
     */
    @ApiModelProperty(value = "核查后处理方式")
    private String checkUpdataType;

    /**
     * 核查修改内容
     */
    @ApiModelProperty(value = "核查修改内容")
    private String checkUpdata;

    /**
     * 核查备注
     */
    @ApiModelProperty(value = "核查备注")
    private String checkDesc;

    /**
     * 是否存疑
     */
    @ApiModelProperty(value = "是否存疑")
    private String isQuestion;

    /*是否为原件 0- 原件  1-复印件*/
    @ApiModelProperty(value = "是否为原件 0- 原件  1-复印件", allowableValues = "0,1")
    private String isMasterCopy;

    /*备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

    /*提交核查时间*/
    @ApiModelProperty(value = "提交核查时间")
    private Date inspectTime;

    /*核查人id*/
    @ApiModelProperty(value = "核查人id")
    private Long inspectId;

    /*核查人姓名*/
    @ApiModelProperty(value = "核查人姓名")
    private String inspectName;

    /*核查是否完成*/
    @ApiModelProperty(value = "核查是否完成")
    private Long isInspectAccomplish;

    @ApiModelProperty(value = "案卷数")
    private Long numberOfCases;
    @ApiModelProperty(value = "是案卷数")
    private Integer isNumberOfCases;
    @ApiModelProperty(value = "是文件页数")
    private Integer isPageNumFile;
    @ApiModelProperty(value = "文件页数")
    private Long pageNumFile;
    @ApiModelProperty(value = "检测案件数量")
    private Long impeachNumberOfCases;
    @ApiModelProperty(value = "检测案件数量")
    private Integer impeachIsNumberOfCases;
    @ApiModelProperty(value = "检测是页码文件")
    private Integer impeachIsPageNumFile;
    @ApiModelProperty(value = "检测文件页数")
    private Long impeachPageNumFile;
    /*存疑数据 (0-不是，1-存疑)*/
    @ApiModelProperty(value = "存疑数据 (0-不是，1-存疑)", allowableValues = "0,1")
    private Integer questionableData;


    private void setData() {
        String userId = null;
        try {
            userId = SecurityUtils.getUsername();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            if (id == null) {
                // 如果没有id就是添加
                //IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
                this.id = IdUtils.fastSimpleUUID();
                this.inspectName = userId;
                this.inspectTime = new Date();
            } else {
                // 如果有id就是修改
                //this.updateBy = userId;
                //this.updateTime = LocalDateTime.now();
            }
        }
    }
}
