package com.fudian.dahc.common;

import com.fudian.common.exception.ServiceException;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@EqualsAndHashCode(callSuper = true)

public class CommonException extends ServiceException {

    private static final Logger log = LoggerFactory.getLogger(CommonException.class);

    @ApiModelProperty(value = "响应状态")
    private Integer code;
    @ApiModelProperty(value = "响应信息")
    private String message;

    public CommonException() {
    }

    public CommonException(String msg) {
        super(msg);
    }

    public CommonException(CommonStatus commonStatus) {
        this.code = commonStatus.getCode();
        this.message = commonStatus.getMsg();
//        this.message = "未知异常,请联系管理员";
        log.error(this.message, commonStatus);
    }

    public CommonException(CommonStatus commonStatus, String msg) {
        this.code = commonStatus.getCode();
        if (msg == null) {
            this.message = commonStatus.getMsg();
        } else {
            this.message = msg;
        }
        log.warn(this.message, commonStatus);
    }

    public static CommonException runtimeException() {
        return new CommonException();
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ServiceException setMessage(String message) {
        this.message = message;
        return new CommonException();
    }
}
