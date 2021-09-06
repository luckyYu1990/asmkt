package com.asmkt.sample.test;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.service.AsmktRechargeService;
import com.asmkt.sample.utils.ResponseAnalysisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AsmktRechargeTestService {

    @Autowired
    private AsmktRechargeService rechargeService;

    public TestResult testUserAuth() {
        TestResponse response = rechargeService.testUserAuth();
        TestResult testResult = new TestResult();
        testResult.setResults(Collections.singletonList(response));
        ResponseAnalysisUtil.analysisRedirectResponse(testResult);
        return testResult;
    }
}
