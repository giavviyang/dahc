package com.fudian.dahc.util.common;

import com.fudian.dahc.common.CommonException;
import com.fudian.dahc.common.CommonStatus;

/**
 */
public class AssertUtil {
    /**
     * 服务调用异常
     * @param expression
     * @param message
     */
    public static void isTrueServiceInvoke(boolean expression, String message) {
        if (!expression) {
            throw new CommonException(message);
        }
    }
    public static void isTrueServiceInvoke(boolean expression, CommonStatus commonStatus) {
        if (!expression) {
            throw new CommonException(commonStatus);
        }

    }public static void isTrueServiceInvoke(boolean expression, CommonStatus commonStatus,String msg) {
        if (!expression) {
            throw new CommonException(commonStatus,msg);
        }
    }
}