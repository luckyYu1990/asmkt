package com.asmkt.klw.controller;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult implements Serializable {

    private static final long serialVersionUID = -5689875703757156509L;
    private Integer code;
    private Object result;
    private String errorMsg;

    public static CommonResult SUCCESS(Object obj) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(0);
        commonResult.setResult(obj);
        return commonResult;
    }

    public static CommonResult SUCCESS() {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(0);
        return commonResult;
    }
}
