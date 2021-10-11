package com.asmkt.sample.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ApiParam> withParamJson(JSONObject paramJson) {
        List<ApiParam> params = new ArrayList<>();
        if (paramJson == null) {
            return params;
        }
        for (String key : paramJson.keySet()) {
            ApiParam param = ApiParam.withKeyValue(key, paramJson.getString(key));
            params.add(param);
        }
        return params;
    }
}
