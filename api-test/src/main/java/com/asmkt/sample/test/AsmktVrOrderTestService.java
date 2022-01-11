package com.asmkt.sample.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.Condition;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktVrOrderService;
import com.asmkt.sample.utils.ResponseAnalysisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class AsmktVrOrderTestService extends BaseTestService {

    @Autowired
    private AsmktVrOrderService vrOrderService;

    public TestResult testCreateOrderBatch(Long batchNum) {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        tasks.add(() -> vrOrderService.createOrderBatch(batchNum));
        execute(tasks, result);
        Condition condition = Condition.builder().build();
        ResponseAnalysisUtil.analysisResponseWithAvg(result, condition);
        return result;
    }
}
