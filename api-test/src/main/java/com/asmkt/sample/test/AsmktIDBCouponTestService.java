package com.asmkt.sample.test;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.constant.enums.PresentQueryResultEnum;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktIDBCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 兴业银行
 */
@Service
public class AsmktIDBCouponTestService extends BaseTestService{

    @Autowired
    private AsmktIDBCouponService couponService;

    public TestResult createCouponConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            tasks.add(() -> couponService.createCoupon());
        }
        execute(tasks, result);
        Condition condition = Condition.builder().build();
       //          .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    private void analysisResponse(TestResult result, Condition condition) {
        List<TestResponse> results = result.getResults();
        int success = 0;
        for (TestResponse res : results) {
            Integer resultCode;
            JSONObject jsonObj = null;
            boolean pass = true;
            if (res.getCode() != 200) {
                pass = false;
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
    }

    private void getRate(TestResult result, double success, Integer total) {
        double rate = success / total;
        BigDecimal bd = new BigDecimal(rate);
        double formatRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.setSuccessRate(formatRate * 100);
    }

    public TestResult queryCouponConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            tasks.add(() -> couponService.queryCoupon());
        }
        execute(tasks, result);
        Condition condition = Condition.builder().build();
        //          .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    public TestResult cancelCouponConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0;i < thread; i ++) {
            tasks.add(() -> couponService.cancelCoupon());
        }
        execute(tasks, result);
        Condition condition = Condition.builder().build();
        //          .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }
}
