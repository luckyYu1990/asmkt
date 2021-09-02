package com.asmkt.sample.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommonResult implements Serializable {

    private static final long serialVersionUID = -6733156246241815543L;
    private Integer code;
    private Object result;
    private String errorMsg;

    public static CommonResult SUCCESS(Object obj) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(0);
        commonResult.setResult(obj);
        return commonResult;
    }

    public boolean isSuccess() {
        return this.code == 0;
    }
}
