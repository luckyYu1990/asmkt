package com.asmkt.sample.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse implements Serializable {
    private static final long serialVersionUID = 5744639779048759984L;
    @Excel(name = "返回码", width = 30)
    private Integer code;
    @Excel(name = "响应内容", width = 100)
    private String responseContent;
    @Excel(name = "解码返回数据", width = 100)
    private String decodedReturnData;
    @Excel(name = "期待返回是否成功（result code）", width = 50)
    private boolean assertExpectResultCode;
    @Excel(name = "调用异常信息", width = 100)
    private String message;
    @Excel(name = "响应时间", width = 50)
    private String responseCost;
    private Long responseCostValue;
    private String responseCostUnit;

    public static TestResponse noResponse() {
        TestResponse response = new TestResponse();
        response.setMessage("No response info or connect failed");
        return response;
    }

    public static TestResponse error() {
        TestResponse response = new TestResponse();
        response.setMessage("error execute");
        return response;
    }

    public static TestResponse error(String message) {
        TestResponse response = new TestResponse();
        response.setCode(500);
        response.setMessage("error execute:" + message);
        return response;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }
}
