package com.fudian.dahc.util;

/**
 * List复制copyProperties工具类CallBack
 *
 * @author wangbin
 */
@FunctionalInterface
public interface ListBeanUtilsCallBack<S, T> {

    void callBack(S t, T s);

}

