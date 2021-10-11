package com.asmkt.sample.test;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class AsmktGeneralTestService {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    private GeneralService generalService;

    public TestResult testGetConcurrent(Long thread, String url, String params) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i ++) {
            tasks.add(() -> generalService.get(url, params));
        }
        execute(tasks, result);
        return result;
    }

    public TestResult testGetConcurrent(List<String> urls) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (String url : urls) {
            tasks.add(() -> generalService.get(url, null));
        }
        execute(tasks, result);
        return result;
    }

    public TestResult testPostFormConcurrent(Long thread, String url, String params) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i ++) {
            tasks.add(() -> generalService.postForm(url, params));
        }
        execute(tasks, result);
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
