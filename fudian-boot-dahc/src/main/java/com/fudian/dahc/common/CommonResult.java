package com.fudian.dahc.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 2023/1/31
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)//不是null的数据才携带到返回数据中
public class CommonResult<T> {

    /**
     * 响应状态
     */

    @ApiModelProperty(value = "响应状态")
    private int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    private String msg;

    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;


    private CommonResult(CommonStatus commonStatus, T t) {
        this.code = commonStatus.getCode();
        this.msg = commonStatus.getMsg();
        this.data = t;
    }

    private CommonResult(CommonStatus commonStatus, String message, T t) {
        this.code = commonStatus.getCode();
        if (message == null || "".equals(message)) {
            this.msg = commonStatus.getMsg();
        } else {
            this.msg = message;

        }
        this.data = t;
    }

    private static <T> CommonResult<T> getInstance(CommonStatus commonStatus, String message, T t) {
        return new CommonResult<T>(commonStatus, message, t);
    }


    /**
     * 请求成功的方法
     */
    public static <T> CommonResult<T> success() {
        return getInstance(CommonStatus.OK, null, null);
    }

    /**
     * 请求成功的方法
     * 携带数据
     */
    public static <T> CommonResult<T> success(T t) {
        return getInstance(CommonStatus.OK, null, t);
    }

    /**
     * 请求成功的方法
     * 携带数据
     */
    public static <T> CommonResult<T> success(String message, T t) {
        return getInstance(CommonStatus.OK, message, t);
    }

    /**
     * 请求失败的方法
     */
    public static <T> CommonResult<T> error() {
        return getInstance(CommonStatus.ERROR, null, null);
    }

    /**
     * 请求失败的方法
     * 携带数据
     */
    public static <T> CommonResult<T> error(T t) {
        return getInstance(CommonStatus.ERROR, null, t);
    }

    public static <T> CommonResult<T> error(String t) {
        return getInstance(CommonStatus.ERROR, t, null);
    }

    /**
     * 请求失败的方法
     * 携带数据
     */
    public static <T> CommonResult<T> error(String message, T t) {
        return getInstance(CommonStatus.ERROR, message, t);
    }

    /**
     * 请求失败的方法
     * 自定义状态码
     */
    public static <T> CommonResult<T> error(CommonStatus commonStatus) {
        return getInstance(commonStatus, null, null);
    }

    /**
     * 请求失败的方法
     * 自定义状态码并且携带数据
     */
    public static <T> CommonResult<T> error(CommonStatus commonStatus, T t) {
        return getInstance(commonStatus, null, t);
    }

    /**
     * 请求失败的方法
     * 自定义状态码并且携带数据
     */
    public static <T> CommonResult<T> error(CommonStatus commonStatus, String message, T t) {
        return getInstance(commonStatus, message, t);
    }
}
