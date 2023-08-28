package com.fudian.dahc.pojo.dto;

import com.fudian.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "核查结果", parent = BaseEntity.class)
public class RecordTrueingDto extends BaseEntity {

    /*核查结果主键id*/
    @ApiModelProperty(value = "核查结果主键id(新增时id为空,修改时id不为空)")
    private String id;
    /*是否合格*/
    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotEmpty(message = "核查结果不能为空")
    @ApiModelProperty(value = "是否合格", required = true)
    private List<String> checkResult = new ArrayList<>();

    /*是否存疑*/
    @NotEmpty(message = "处理方式不能为空")
    @ApiModelProperty(value = "是否存疑", required = true)
    private List<String> processMode = new ArrayList<>();

    /*更改方式*/
    @NotEmpty(message = "更改内容不能为空")
    @ApiModelProperty(value = "更改方式", required = true)
    private List<String> changeContent = new ArrayList<>();

    /*卷数据id*/
    @ApiModelProperty(value = "卷数据id")
    private String filesId;

    /*卷数据id*/
    @ApiModelProperty(value = "案卷状态")
    private String isProcedureInspect;


  /*  档号名称  */
    @ApiModelProperty(value = "档号名称(长度min:1,max:100)")
    private String fileNumberName;

    /**
     * 档号名称
     */
    @ApiModelProperty(value = "档号名称(长度min:1,max:100)")
    private String fileNumberId;


    /*是否是存疑页面提交的数据*/
    @ApiModelProperty(value = "是否是存疑页面提交的数据")
    private String impeach;

    /**
     * 是否是 ‘返回’保存的数据(0- 不是 1-是)
     */
    @ApiModelProperty(value = "是否是 ‘返回’保存的数据(0- 不是 1-是)", required = true, allowableValues = "0,1")
    private Long isReturnSave;


    /*档案结果记录表Id*/
    @ApiModelProperty(value = "档案结果记录表Id", required = true)
    private String recordProcedureFilesId;

    /*备注*/
    @ApiModelProperty(value = "备注")
    private String remark;

    /*是否展示表头页号*/
    @ApiModelProperty(value = "是否展示表头页号")
    private Integer showPageNumber;

    /*是否展示表头件号*/
    @ApiModelProperty(value = "是否展示表头件号")
    private Integer showPiece;

    /*表格内是否显示页号*/
    @ApiModelProperty(value = "表格内是否显示页号")
    private List<Map<String, Long>> pageNumS = new ArrayList<>();

/*    表格内是否显示件号*/
    @ApiModelProperty(value = "表格内是否显示件号")
    private List<Map<String, Long>> caseNumS = new ArrayList<>();

    /*表格内是否显示页号 存疑*/
    @ApiModelProperty(value = "表格内是否显示页号 存疑")
    private List<Map<String, Long>> impeachPageNumS = new ArrayList<>();

    /*表格内是否显示件号 存疑*/
    @ApiModelProperty(value = "表格内是否显示件号 存疑")
    private List<Map<String, Long>> impeachCaseNumS = new ArrayList<>();

    /*核查标准*/
    @ApiModelProperty(value = "核查标准")
    private String examineStasString;

    /*核查下名称*/
    @ApiModelProperty(value = "核查项名称")
    private String trueingName;


    /*核查下名称*/
    @ApiModelProperty(value = "核查项名称")
    private String caseNum;

    /*核查是否完成*/
    @ApiModelProperty(value = "核查是否完成")
    private Long isInspectAccomplish;

    /**
     * 卷数据id
     */
    @ApiModelProperty(value = "卷数据id")
    private String rollId;

    /**
     * 工序id
     */
    @ApiModelProperty(value = "工序id")
    private String procedureId;
    /*核查范围*/
    @ApiModelProperty(value = "核查范围")
    private String trueingScopeStair;
    /*工序名称*/
    @ApiModelProperty(value = "工序名称")
    private String procedureName;
    /*核查项描述*/
    @ApiModelProperty(value = "核查项描述")
    private String trueingDesc;
    /*项目id*/
    @ApiModelProperty(value = "项目id")
    private String projectId;
/*    项目名称*/
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    /*项目状态*/
    @ApiModelProperty(value = "项目状态")
    private String projectState;

    /**
     * 核查项id
     */
    @ApiModelProperty(value = "核查项id")
    private String trueingId;

    /**
     * 核查结果(0:合格|1:不合格|2:存疑|3:已修改)
     */
    @ApiModelProperty(value = "核查结果(0:合格|1:不合格|2:存疑|3:已修改)", allowableValues = "0,1,2,3")
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


    /*提交核查时间*/
    @ApiModelProperty(value = "提交核查时间")
    private Date inspectTime;

    /*核查人id*/
    @ApiModelProperty(value = "核查人id")
    private Long inspectId;

    /*核查人姓名*/
    @ApiModelProperty(value = "核查人姓名")
    private String inspectName;

    /**
     * 页数
     */
    @ApiModelProperty(value = "页数")
    private Long pageNumFile;
    /**
     * 件数
     */
    @ApiModelProperty(value = "件数")
    private Long numberOfCases;

    /*是否存在件数*/
    @ApiModelProperty(value = "是否存在件数")
    private Integer isNumberOfCases;
    /*是否存在页数*/
    @ApiModelProperty(value = "是否存在页数")
    private Integer isPageNumFile;


    /*存疑件数*/
    @ApiModelProperty(value = "存疑件数")
    private Long impeachNumberOfCases;
    /*是否存在件数 存疑*/
    @ApiModelProperty(value = "是否存在件数 存疑")
    private Integer impeachIsNumberOfCases;
    /*是否存在页数 存疑*/
    @ApiModelProperty(value = "是否存在页数 存疑")
    private Integer impeachIsPageNumFile;
    /*存疑页数*/
    @ApiModelProperty(value = "存疑页数")
    private Long impeachPageNumFile;

    /*存疑数据*/
    @ApiModelProperty(value = "存疑数据")
    private Integer questionableData;

}
