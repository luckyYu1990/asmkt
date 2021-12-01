package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.controller.vo.PointsParamVo;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.AsmktBaseService;
import com.asmkt.sample.service.AsmktPointPlatformService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsmktPointPlatformServiceImpl  extends AsmktBaseService implements AsmktPointPlatformService {

    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse getPoints(String appId, String userId) {
        String url = "https://nudgbank.kliwu.com/point/getPointTotal";
        url = url + "?appId=" + appId + "&userId=" + userId;
        return doGetTestUrl(url);
    }

    @Override
    public TestResponse rechargePoints(PointsParamVo vo) {
        String url = "https://nudgbank.kliwu.com/point/addPoint";
        JSONObject obj = new JSONObject();
        obj.put("userId", vo.getUserId());
        obj.put("point", vo.getPoint());
        obj.put("appId", vo.getAppId());
        obj.put("reason", "recharge");
        obj.put("expiredTime", vo.getExpiredTime());
        obj.put("createTime", "2021-11-09 17:05:50");
        obj.put("uniqueInfo", RandomStringUtils.randomAlphanumeric(10));
       // obj.put("uniqueInfo", "1");
        List<ApiParam> apiParams = ApiParam.withParamJson(obj);
        return postJsonWithParams(apiParams, url);
    }

    @Override
    public TestResponse consumePoints(PointsParamVo vo) {
        String url = "https://nudgbank.kliwu.com/point/costPoint";
        JSONObject obj = new JSONObject();
        obj.put("userId", vo.getUserId());
        obj.put("point", vo.getPoint());
        obj.put("appId", vo.getAppId());
        obj.put("reason", "consume");
        obj.put("uniqueInfo", RandomStringUtils.randomAlphanumeric(10));
        // obj.put("uniqueInfo", "1");
        List<ApiParam> apiParams = ApiParam.withParamJson(obj);
        return postWithParams(apiParams, url);
    }

    @Override
    public TestResponse refundPoints(PointsParamVo vo) {
        String url = "https://nudgbank.kliwu.com/point/refundPoint";
        JSONObject obj = new JSONObject();
        obj.put("userId", vo.getUserId());
        obj.put("point", vo.getPoint());
        obj.put("appId", vo.getAppId());
        obj.put("reason", "refund");
        obj.put("uniqueInfo", RandomStringUtils.randomAlphanumeric(10));
        //obj.put("uniqueInfo", "2");
        obj.put("operationId", vo.getOperationId());
        List<ApiParam> apiParams = ApiParam.withParamJson(obj);
        return postWithParams(apiParams, url);
    }

}
