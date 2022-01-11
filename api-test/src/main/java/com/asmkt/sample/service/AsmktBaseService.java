package com.asmkt.sample.service;

import com.alibaba.fastjson.JSONArray;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

@Slf4j
@Service
public class AsmktBaseService {

    @Autowired
    private HttpApiService apiService;

    protected TestResponse doGetTestUrl(String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doGet(url);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    protected TestResponse postJsonWithParams(List<ApiParam> paramList, String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPostJson(url, paramList);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    protected TestResponse postJsonArrayWithParams(JSONArray params, String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPostJsonArray(url, params);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    protected TestResponse postWithParams(List<ApiParam> params, String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPost(url, params);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    private StopWatch getStopWatch() {
        String thName = Thread.currentThread().getName();
        String watchName = "recharge " + thName;
        System.out.println(watchName);
        return new StopWatch(watchName);
    }
}
