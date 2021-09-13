package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import java.util.List;

@Service
public class GeneralServiceImpl implements GeneralService {

    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse get(String url, String params) {
        JSONObject paramJson = JSONObject.parseObject(params);
        List<ApiParam> paramList = ApiParam.withParamJson(paramJson);
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doGet(url, paramList);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    @Override
    public TestResponse postForm(String url, String params) {
        JSONObject paramJson = JSONObject.parseObject(params);
        List<ApiParam> paramList = ApiParam.withParamJson(paramJson);
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPost(url, paramList);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    private StopWatch getStopWatch() {
        String thName = Thread.currentThread().getName();
        String watchName = "general " + thName;
        System.out.println(watchName);
        return new StopWatch(watchName);
    }
}
