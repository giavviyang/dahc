package com.fudian.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 〈表格通用返回对象〉
 *
 * @author Mr.Hou
 * @create 2019/6/28
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonGridResult {

    /**
     * 默认的code值
     */
    @ApiModelProperty(value = "响应状态")
    private  final  int  code=20000;

    /**
     * 当前是第几页
     */
    @ApiModelProperty(value = "当前页")
    private int pageNum;


    /**
     * 每一页的个数
     */
    @ApiModelProperty(value = "每一页的个数")
    private int pageSize;

    /**
     * 是否还有前一页
     */
    @ApiModelProperty(value = "是否还有前一页")
    private boolean hasPrev;

    /**
     * 是否还有后一页
     */
    @ApiModelProperty(value = "是否还有后一页")
    private boolean hasNext;

    /**
     * 总的页数
     */
    @ApiModelProperty(value = "总的页数")
    private int totalPage;


    /**
     * 总条数
     */
    @ApiModelProperty(value = "总条数")
    private long total;



    /**
     * 列表数据
     */
    @ApiModelProperty(value = "列表数据")
    private List<?> data;



    public CommonGridResult(int pageNum, int pageSize, long total, List<?> data){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;

    }
    public CommonGridResult(long total, List<?> data){
        this.total = total;
        this.data = data;
    }

    public static <T> CommonGridResult initData(int pageNum, int pageSize, long total, List<?> data){
        return new CommonGridResult(pageNum,pageSize,total,data);
    }
}
