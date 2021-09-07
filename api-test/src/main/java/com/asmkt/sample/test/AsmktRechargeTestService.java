package com.asmkt.sample.test;

import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktRechargeService;
import com.asmkt.sample.utils.ResponseAnalysisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class AsmktRechargeTestService {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    @Autowired
    private AsmktRechargeService rechargeService;

    public TestResult testUserAuth() {
        TestResponse response = rechargeService.testUserAuth();
        TestResult testResult = new TestResult();
        testResult.setResults(Collections.singletonList(response));
        Condition condition = Condition.getCostTimeCondition(300L);
        ResponseAnalysisUtil.analysisRedirectResponse(testResult, condition);
        return testResult;
    }

    public TestResult  testUserAuthConcurrent(Long thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i ++) {
            tasks.add(() -> rechargeService.testUserAuth());
        }
        execute(tasks, result);
        Condition condition = Condition.getCostTimeCondition(300L);
        ResponseAnalysisUtil.analysisRedirectResponse(result, condition);
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
}
