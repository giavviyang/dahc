package com.fudian.dahc.controller.base;


import com.fudian.dahc.common.CommonResult;

/**
 * @author wenbo
 */
public class MyBaseController {

    protected CommonResult<Void> toAxios(int row){

        return row > 0 ? CommonResult.success(): CommonResult.error("操作失败,请重新操作或联系管理员");
    }
}
