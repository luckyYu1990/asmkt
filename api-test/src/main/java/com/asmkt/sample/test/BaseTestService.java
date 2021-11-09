package com.asmkt.sample.test;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class BaseTestService {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    void execute(List<Callable<TestResponse>> tasks, TestResult result) {
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
