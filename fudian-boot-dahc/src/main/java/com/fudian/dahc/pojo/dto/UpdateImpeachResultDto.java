package com.fudian.dahc.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Data
public class UpdateImpeachResultDto {

    @ApiModelProperty(value = "案件编号结果Id", position = 2)
    private String impeachCaseNumResultsIds;

    @ApiModelProperty(value = "案件编号结果", position = 2)
    private Long impeachPageNumResults;
}
