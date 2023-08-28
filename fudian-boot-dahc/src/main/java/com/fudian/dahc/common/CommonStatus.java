package com.fudian.dahc.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 2023/1/31
 */


@Getter
@AllArgsConstructor
public enum CommonStatus {

    /**
     * 返回状态码
     * 状态码由五个十进制数字组成，第一个十进制数字定义了状态码的类型。
     * 响应分为五类：
     * 信息响应(10000–19900)，
     * 成功响应(20000–29900)，
     * 重定向(30000–39900)，
     * 客户端错误(40000–49900)和服务器错误 (50000–59900)：
     * <p>
     * 分类	分类描述
     * 1****	信息，服务器收到请求，需要请求者继续执行操作
     * 2****	成功，操作被成功接收并处理
     * 3****	重定向，需要进一步的操作以完成请求
     * 4****	客户端错误，请求包含语法错误或无法完成请求
     * 5****	服务器错误，服务器在处理请求的过程中发生了错误
     */

    CONTINUE_EXECUTION(10001, "继续执行"),
    @ApiModelProperty(value = "操作成功")
    OK(20000, "操作成功"),
    ACCEPTED(20200, "请求已经被接受"),
    NO_CONTENT(20400, "操作已经执行成功，但是没有返回数据"),
    PARTIAL_CONTENT(20600, "文件返回部分信息"),
    MOVED_PERM(30100, "资源已被移除"),
    NOT_MODIFIED(30400, "资源没有被修改"),
    BAD_REQUEST(40000, "客户端请求参数错误，服务器无法理解"),
    UNAUTHORIZED(40100, "没有认证"),
    FORBIDDEN(40300, "没有没有访问权限"),
    NOT_FOUND(40400, "服务器找不到资源"),
    METHOD_NOT_ALLOWED(40500, "客户端请求中的方法被禁止"),
    REQUEST_TIME_OUT(40800, "服务器等待客户端发送的请求时间过长，超时"),
    Request_Entity_Too_Large(41300, "请求实体类过大,可能会执行失败"),
    ERROR(50000, "服务器内部错误"),
    NOT_IMPLEMENTED(50100, "接口未实现"),
    BAD_GATEWAY(50200, "无效的响应"),
    NON_COMPLIANCE_WITH_REQUIREMENTS(50001, "不符合条件的请求"),
    DUPLICATE_REQUEST(50002, "重复的请求"),
    ;

    private int code;

    private String msg;

    public void setStatus(int status) {
        this.code = status;
    }

    public void setMessage(String message) {
        this.msg = message;
    }
}