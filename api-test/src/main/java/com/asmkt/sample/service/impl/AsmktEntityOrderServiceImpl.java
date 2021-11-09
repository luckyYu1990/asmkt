package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.AsmktEntityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsmktEntityOrderServiceImpl implements AsmktEntityOrderService {

    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse queryOrder() {
        String appId = "21119980155800002";
        String orderNo = "D21071707592042180y70";
        String subOrderNo = "D21071707592042180y71";
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("appId", appId);
        jsonObj.put("orderNo", orderNo);
        jsonObj.put("subOrderNo", subOrderNo);
        String url = "http://47.100.68.250:9049/api/Order/QueryOrder";

        return null;
    }
}
