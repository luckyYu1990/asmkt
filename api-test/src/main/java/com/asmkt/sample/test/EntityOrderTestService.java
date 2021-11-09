package com.asmkt.sample.test;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktEntityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class EntityOrderTestService extends BaseTestService {

    @Autowired
    private AsmktEntityOrderService entityOrderService;

    public TestResult queryEntityOrderConcurrent(Integer thread) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < thread; i++) {
            tasks.add(() -> entityOrderService.queryOrder());
        }
        execute(tasks, result);
        return result;
    }
}
