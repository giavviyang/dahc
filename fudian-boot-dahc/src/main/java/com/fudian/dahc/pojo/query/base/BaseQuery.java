package com.fudian.dahc.pojo.query.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

/**
 * 2023/2/7
 */
@Data
public class BaseQuery {

    @ApiModelProperty(value = "当前页", example = "1", position = 1)
    private Integer pageNum = 1;

    @ApiModelProperty(value = "数据大小", example = "20", position = 2)
    private Integer pageSize = 20;

    @PastOrPresent
    @ApiModelProperty(value = "开始时间", position = 3)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @PastOrPresent
    @ApiModelProperty(value = "结束时间", position = 3)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
