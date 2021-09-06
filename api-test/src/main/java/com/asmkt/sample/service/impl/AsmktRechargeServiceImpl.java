package com.asmkt.sample.service.impl;

import com.asmkt.sample.constant.AsmktRechargeConstant;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.AsmktRechargeService;
import com.asmkt.sample.utils.MD5Utils;
import com.asmkt.sample.utils.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsmktRechargeServiceImpl implements AsmktRechargeService {

    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse testUserAuth() {
        List<ApiParam> paramList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("appid", AsmktRechargeConstant.USER_AUTH_APPID);
        params.put("uniqueid", "");
        params.put("mobile", "");
        params.put("themeid", "");
        params.put("actcode", AsmktRechargeConstant.USER_AUTH_ACTCODE);
        String sign = generateSign(params);
        params.put("sign", sign);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ApiParam param = ApiParam.withKeyValue(entry.getKey(), entry.getValue());
            paramList.add(param);
        }
        TestResponse response = apiService.doGet(AsmktRechargeConstant.USER_AUTH_TEST_URL, paramList);
        System.out.println(response);
        return response;
    }

    private String generateSign(Map<String, String> params) {
        Map<String, String> signMap = new HashMap<>(params);
        signMap.remove("sign");
        String signUrl = SignUtils.getSignUrl(signMap, AsmktRechargeConstant.USER_AUTH_APPSECRET);
        return MD5Utils.getMD5Str(signUrl).toUpperCase();
    }
}
