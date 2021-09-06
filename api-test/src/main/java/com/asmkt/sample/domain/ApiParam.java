package com.asmkt.sample.domain;

import lombok.Data;

@Data
public class ApiParam {
    private String key;
    private String value;

    public static ApiParam withKeyValue(String key, String value) {
        ApiParam param = new ApiParam();
        param.setKey(key);
        param.setValue(value);
        return param;
    }
}
