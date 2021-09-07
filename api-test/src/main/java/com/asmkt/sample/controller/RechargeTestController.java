package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktRechargeTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "recharge-api")
@RestController
@RequestMapping("recharge-api")
public class RechargeTestController {

    @Autowired
    private AsmktRechargeTestService testService;

    @ApiOperation("user auth")
    @GetMapping("user-auth")
    public TestResult testUserAuth() {
        return testService.testUserAuth();
    }

    @ApiOperation("user auth concurrent")
    @GetMapping("user-auth/concurrent/{thread}")
    public TestResult testUserAuthConcurrent(@PathVariable("thread") Long thread) {
        return testService.testUserAuthConcurrent(thread);
    }

}
