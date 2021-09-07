package com.asmkt.sample.utils;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class ResponseAnalysisUtil {

    public static void analysisResponse(TestResult result, Condition condition) {
        List<TestResponse> results = result.getResults();
        int success = 0;
        for (TestResponse res : results) {
            Integer resultCode;
            JSONObject jsonObj = getResJsonObject(res);
            boolean pass = true;
            if (condition.isCheckResultCode()) {
                resultCode = jsonObj.getInteger(condition.getResultKeyName());
                if (resultCode != null && resultCode == condition.getExpectResult().getCode()) {
                    res.setAssertExpectResultCode(true);
                } else {
                    pass = false;
                }
            }
            if (condition.isCheckCostTime()) {
                Long costTime = condition.getExpectCostTime();
                Long actualCost = res.getResponseCostValue();
                if (actualCost > costTime) {
                    pass = false;
                }
            }
            if (pass) {
                success ++;
            }
        }
        result.setSuccessTotal(success);
        Integer total = result.getTotal();
        result.setFailedTotal(total - success);
        getRate(result, success, total);
    }

    public static void analysisRedirectResponse(TestResult result, Condition condition) {
        List<TestResponse> results = result.getResults();
        result.setTotal(results.size());
        int success = 0;
        for (TestResponse res : results) {
            boolean pass = true;
            if (condition.isCheckCostTime()) {
                Long costTime = condition.getExpectCostTime();
                Long actualCost = res.getResponseCostValue();
                if (actualCost > costTime) {
                    pass = false;
                }
            }
            Integer code = res.getCode();
            if (code != 200) {
                pass = false;
            }
            if (pass) {
                success++;
            }
        }
        result.setSuccessTotal(success);
        Integer total = result.getTotal();
        result.setFailedTotal(total - success);
        getRate(result, success, total);
    }

    private static JSONObject getResJsonObject(TestResponse res) {
        JSONObject jsonObj = null;
        try {
            String decodedReturnData = res.getDecodedReturnData();
            jsonObj = JSONObject.parseObject(decodedReturnData);
        } catch (Exception e) {
            log.warn("get result code failed", e);
        }
        if (jsonObj == null) {
            jsonObj = new JSONObject();
        }
        return jsonObj;
    }


    private static void getRate(TestResult result, double success, Integer total) {
        double rate = success / total;
        BigDecimal bd = new BigDecimal(rate);
        double formatRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.setSuccessRate(formatRate * 100);
    }
}
