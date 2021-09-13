package com.asmkt.sample.service.impl;

import com.asmkt.sample.constant.AsmktRechargeConstant;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.service.AsmktVehicleService;
import com.asmkt.sample.utils.MD5Utils;
import com.asmkt.sample.utils.SignUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsmktVehicleServiceImpl implements AsmktVehicleService {


    @Override
    public TestResponse testExternalLogin() {
        List<ApiParam> paramList = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("appid", "");
        params.put("tstp", "");
        params.put("mobile", "");
        String sign = generateSign(params);
        params.put("sign", sign);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ApiParam param = ApiParam.withKeyValue(entry.getKey(), entry.getValue());
            paramList.add(param);
        }
        return doGetTestUrl(paramList);
    }

    private TestResponse doGetTestUrl(List<ApiParam> paramList) {
        return null;
    }

    private String generateSign(Map<String, String> params) {
        Map<String, String> signMap = new HashMap<>(params);
        signMap.remove("sign");
        //signMap.put()
        String signUrl = SignUtils.getSignUrl(signMap, AsmktRechargeConstant.USER_AUTH_APPSECRET);
        return MD5Utils.getMD5Str(signUrl).toUpperCase();
    }
}
