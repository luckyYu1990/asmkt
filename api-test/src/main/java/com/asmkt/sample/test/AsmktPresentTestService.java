package com.asmkt.sample.test;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.client.AsmktKlwClient;
import com.asmkt.sample.common.CommonResult;
import com.asmkt.sample.constant.AsmktPresentConstant;
import com.asmkt.sample.constant.enums.PresentCreateResultEnum;
import com.asmkt.sample.constant.enums.PresentQueryResultEnum;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktPresentService;
import com.asmkt.sample.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

@Service
@Slf4j
public class AsmktPresentTestService {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private AsmktPresentService presentService;
    @Autowired
    private AsmktKlwClient klwClient;

    public TestResult testPresentCreateConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i ++) {
            tasks.add(() -> presentService.createOrder());
        }
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.SUCCESS)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    /*private void analysisResponse(TestResult result, IEnum assertEnum, String resultKeyName) {
        List<TestResponse> results = result.getResults();
        int success = 0;
        for (TestResponse res : results) {
            Integer resultCode = -10000;
            try {
                String decodedReturnData = res.getDecodedReturnData();
                JSONObject jsonObj = JSONObject.parseObject(decodedReturnData);
                resultCode = jsonObj.getInteger(resultKeyName);
            } catch (Exception e) {
                log.warn("get result code failed", e);
            }
            if (resultCode != null && resultCode == assertEnum.getCode()) {
                res.setAssertExpectResultCode(true);
                success++;
            }
        }
        result.setSuccessTotal(success);
        Integer total = result.getTotal();
        result.setFailedTotal(total - success);
        getRate(result, success, total);
    }*/

    private void analysisResponse(TestResult result, Condition condition) {
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

    private JSONObject getResJsonObject(TestResponse res) {
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


    private void getRate(TestResult result, double success, Integer total) {
        double rate = success / total;
        BigDecimal bd = new BigDecimal(rate);
        double formatRate = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        result.setSuccessRate(formatRate * 100);
    }

    public TestResult testPresentQueryConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i ++) {
            tasks.add(() -> presentService.queryOrder());
        }
        execute(tasks, result);
        //analysisResponse(result, PresentQueryResultEnum.SUCCESS, "Status");
        Condition condition = Condition.builder().expectResult(PresentQueryResultEnum.SUCCESS)
                .resultKeyName("Status")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    private void execute(List<Callable<TestResponse>> tasks, TestResult result) {
        try {
            List<Future<TestResponse>> futures = executorService.invokeAll(tasks);
            List<TestResponse> responses = new ArrayList<>();
            for (Future<TestResponse> future: futures) {
                TestResponse res = future.get();
                responses.add(res);
            }
            result.setResults(responses);
            result.setTotal(responses.size());
        } catch (Exception e) {
            log.error("exec tasks failed", e);
        }
    }

    @Async
    public TestResult testPresentQueryLoop(Long minutes) {
        final TestResult result = new TestResult();
        List<TestResponse> responses = new ArrayList<>();
        loop(minutes, responses, () -> presentService.queryOrder());
        result.setResults(responses);
        result.setTotal(responses.size());
        //analysisResponse(result, PresentQueryResultEnum.SUCCESS, "Status");
        Condition condition = Condition.builder().expectResult(PresentQueryResultEnum.SUCCESS)
                .resultKeyName("Status")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        try {
            String fileName = "my-test-query-" + System.currentTimeMillis() + ".xls";
            File file = new File("D:\\" + fileName);
            ExportParams params = new ExportParams();
            params.setCreateHeadRows(true);
            ExcelUtils.exportToFile(responses, TestResponse.class, params, file);
        } catch (Exception e) {
            log.error("导出到文件失败", e);
        }
        return result;
    }

    private void loop(Long minutes, List<TestResponse> responses, Supplier<TestResponse> supplier) {
        Instant now = Instant.now();
        while (true) {
            TestResponse response = supplier.get();
            //TestResponse response = presentService.queryOrder();
            responses.add(response);
            Instant now1 = Instant.now();
            long seconds = Duration.between(now, now1).getSeconds();
            if (seconds > (minutes * 60)) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public TestResult testPresentCreateOutOfStock() {
        CommonResult commonResult = klwClient.updateGoodCanStock(AsmktPresentConstant.GOOD_ID, 0);
        if (!commonResult.isSuccess()) {
            throw new RuntimeException("update good stock 0 failed");
        }
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        tasks.add(() -> presentService.createOrder());
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.INSUFFICIENT_INVENTORY)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        klwClient.updateGoodCanStock(AsmktPresentConstant.GOOD_ID, 999);
        return result;
    }

    public TestResult testPresentCreateInsufficientAccount() {
        CommonResult commonResult = klwClient.updateClientAccountByGoodId(AsmktPresentConstant.GOOD_ID, 0);
        if (!commonResult.isSuccess()) {
            throw new RuntimeException("update client account 0 failed");
        }
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        tasks.add(() -> presentService.createOrder());
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.INSUFFICIENT_BALANCE)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        klwClient.updateClientAccountByGoodId(AsmktPresentConstant.GOOD_ID, 9999999);
        return result;
    }


    public TestResult testPresentCreateOverbooking() {
        TestResponse response = presentService.createOrder();
        if (response.getCode() != 200) {
            throw new RuntimeException("create order failed");
        }
        String decodedReturnData = response.getDecodedReturnData();
        JSONObject jsonObject = JSONObject.parseObject(decodedReturnData);
        String clientOrderId = jsonObject.getString("ClientOrderId");
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        tasks.add(() -> presentService.createOrder(clientOrderId));
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.OVERBOOKING)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    public TestResult testPresentCreateError() {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        tasks.add(() -> presentService.createOrderError());
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.ORDER_ERROR)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    public TestResult testPresentQueryNotExist() {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        Long clientOrderId = 123456L;
        tasks.add(() -> presentService.queryOrder(clientOrderId));
        execute(tasks, result);
        Condition condition = Condition.builder().expectResult(PresentQueryResultEnum.NO_SUCH_ORDER)
                .resultKeyName("Status")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        return result;
    }

    public TestResult testPresentCreateLoop(Long minutes) {
        final TestResult result = new TestResult();
        List<TestResponse> responses = new ArrayList<>();
        loop(minutes, responses, () -> presentService.createOrder());
        result.setResults(responses);
        result.setTotal(responses.size());
        Condition condition = Condition.builder().expectResult(PresentCreateResultEnum.SUCCESS)
                .resultKeyName("ResultCode")
                .expectCostTime(300L).build();
        analysisResponse(result, condition);
        try {
            String fileName = "my-test-create-" + System.currentTimeMillis() + ".xls";
            File file = new File("D:\\" + fileName);
            ExportParams params = new ExportParams();
            params.setCreateHeadRows(true);
            ExcelUtils.exportToFile(responses, TestResponse.class, params, file);
        } catch (Exception e) {
            log.error("导出到文件失败", e);
        }
        return result;
    }
}
