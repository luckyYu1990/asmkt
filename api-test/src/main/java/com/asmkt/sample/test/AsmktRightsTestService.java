package com.asmkt.sample.test;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktRightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class AsmktRightsTestService extends BaseTestService {

    @Autowired
    private AsmktRightsService rightsService;

    public TestResult testGetCoupon(Integer thread) {
        TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            tasks.add(() -> rightsService.getCoupon());
        }
        execute(tasks, result);
        analysisResponse(result, getEmptyCondition());
        return result;
    }

    private void analysisResponse(TestResult result, Condition condition) {
        List<TestResponse> results = result.getResults();
        int success = 0;
        Long totalResCost = 0L;
        Long totalResSuc = 0L;
        for (TestResponse res : results) {
            Integer resultCode;
            JSONObject jsonObj = null;
            boolean pass = true;
            if (res.getCode() != 200) {
                pass = false;
            } else {
                totalResCost = totalResCost + res.getResponseCostValue();
                totalResSuc++;
            }
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
        result.setAverageCost(getValueRetain2DecimalPlaces(totalResCost, totalResSuc));
    }


    public TestResult thirdLogin(Integer thread) {
        TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            tasks.add(() -> rightsService.thirdLogin());
        }
        execute(tasks, result);
        analysisResponse(result, getEmptyCondition());
        return result;
    }
}
