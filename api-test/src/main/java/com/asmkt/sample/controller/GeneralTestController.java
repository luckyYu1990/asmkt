package com.asmkt.sample.controller;

import com.asmkt.sample.common.UrlAccessParam;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktGeneralTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("general-test")
public class GeneralTestController {

    @Autowired
    private AsmktGeneralTestService generalTestService;

    @ApiOperation("get url")
    @PostMapping("get-url/concurrent/{thread}")
    public TestResult testConcurrentGet(@PathVariable("thread") Long thread, @RequestBody UrlAccessParam accessParam) {
        return generalTestService.testGetConcurrent(thread, accessParam.getUrl(), accessParam.getJsonParamString());
    }

    @ApiOperation("get url")
    @PostMapping("get-url/concurrent/{thread}/{loop}")
    public TestResult testConcurrentLoopGet(@PathVariable("thread") Long thread, @PathVariable("loop") Long loop, @RequestBody UrlAccessParam accessParam) {
        return generalTestService.testGetConcurrentLoop(thread, loop, accessParam.getUrl(), accessParam.getJsonParamString());
    }

    @ApiOperation("get url")
    @PostMapping("get-url/concurrent")
    public TestResult testConcurrentGetUrls(@RequestBody List<String> urls) {
        return generalTestService.testGetConcurrent(urls);
    }

    @ApiOperation("post url")
    @PostMapping("post-url/concurrent/{thread}")
    public TestResult testConcurrentPost(@PathVariable("thread") Long thread, @RequestBody UrlAccessParam accessParam) {
        return generalTestService.testPostFormConcurrent(thread, accessParam.getUrl(), accessParam.getJsonParamString());
    }
}
