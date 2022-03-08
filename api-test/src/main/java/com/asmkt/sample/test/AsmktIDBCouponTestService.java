package com.asmkt.sample.test;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.constant.enums.PresentQueryResultEnum;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktIDBCouponService;
import com.asmkt.sample.utils.CsvUtils;
import com.sun.org.apache.xpath.internal.res.XPATHMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  /*  private void getRate(TestResult result, double success, Integer total) {
        double rate = success / total;
        BigDecimal bd = new BigDecimal(rate);
        double formatRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.setSuccessRate(formatRate * 100);
    }*/

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

    public void generateCreateCouponParamCsv(Integer num, String filePath) {
        List<String[]> params = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            JSONObject param = couponService.getCreateCouponParams();
            params.add(new String[]{
                    param.getString("store_id"), param.getString("encode_params"), param.getString("req_code"),
                    param.getString("pos_id"), param.getString("mem_id"), param.getString("verify_sign"),
                    param.getString("req_serial_no"), param.getString("timestamp"), param.getString("data")
            });
        }
        writeCsv(filePath, params);
    }

    private void writeCsv(String filePath, List<String[]> params) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CsvUtils.writeCsvFile(filePath, null, params);
    }

    public void generateQueryCouponParamCsv(Integer num, String filePath) {
        List<String[]> params = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            JSONObject param = couponService.getQueryCouponParams();
            params.add(new String[]{
                    param.getString("store_id"), param.getString("encode_params"), param.getString("req_code"),
                    param.getString("mem_id"), param.getString("verify_sign"),
                    param.getString("timestamp"), param.getString("data")
            });
        }
        writeCsv(filePath, params);
    }
}
