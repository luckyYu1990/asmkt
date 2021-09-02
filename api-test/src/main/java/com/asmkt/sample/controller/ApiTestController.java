package com.asmkt.sample.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktPresentTestService;
import com.asmkt.sample.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(tags = "present-api")
@RestController
@RequestMapping("present-api")
public class ApiTestController {

    @Autowired
    private AsmktPresentTestService testService;

    @ApiOperation("normal create order")
    @GetMapping("create-order/normal")
    public TestResult testPresentNormalCreate() {
        return testService.testPresentCreateConcurrent(1);
    }

    @ApiOperation("create order out of stock")
    @GetMapping("create-order/out-of-stock")
    public TestResult testPresentCreateOutOfStock() {
        return testService.testPresentCreateOutOfStock();
    }

    @ApiOperation("create order insufficient account")
    @GetMapping("create-order/insufficient-account")
    public TestResult testPresentCreateInsufficientAccount() {
        return testService.testPresentCreateInsufficientAccount();
    }

    @ApiOperation("create order overbooking")
    @GetMapping("create-order/overbooking")
    public TestResult testPresentCreateOverbooking() {
        return testService.testPresentCreateOverbooking();
    }

    @ApiOperation("create order error")
    @GetMapping("create-order/error")

    public TestResult testPresentCreateError() {
        return testService.testPresentCreateError();
    }

    @ApiOperation("query order normal")
    @GetMapping("query-order/normal")
    public TestResult testPresentQueryNormal() {
        return testService.testPresentQueryConcurrent(1);
    }

    @ApiOperation("query order not exist")
    @GetMapping("query-order/not-exist")
    public TestResult testPresentQueryNotExist() {
        return testService.testPresentQueryNotExist();
    }

    @ApiOperation("query order")
    @GetMapping("query-order/concurrent/{thread}")
    public TestResult testPresentConcurrentQueryApi(@PathVariable(value = "thread") Integer thread) {
        return testService.testPresentQueryConcurrent(thread);
    }

    @ApiOperation("create order")
    @GetMapping("create-order/concurrent/{thread}")
    public TestResult testPresentConcurrentCreateApi(@PathVariable("thread") Integer thread) {
        return testService.testPresentCreateConcurrent(thread);
    }

    @ApiOperation("query order loop")
    @GetMapping("query-order/loop/{timeMinutes}")
    public TestResult testPresentLoopQueryApi(@PathVariable("timeMinutes") Long times) {
        return testService.testPresentQueryLoop(times);
    }

    @ApiOperation("create order loop")
    @GetMapping("create-order/loop/{timeMinutes}")
    public TestResult testPresentLoopCreateApi(@PathVariable("timeMinutes") Long times) {
        return testService.testPresentCreateLoop(times);
    }

    @ApiOperation("query result export")
    @GetMapping("query-order/export/{thread}")
    public void exportPresentQueryApi(@PathVariable("thread") Integer thread, HttpServletResponse response) throws IOException {
        TestResult testResult = testService.testPresentQueryConcurrent(thread);
        List<TestResponse> results = testResult.getResults();

        String fileName = "礼物查询api调用结果-模拟线程数" + thread;
        ExportParams params = new ExportParams();
        params.setCreateHeadRows(true);
        ExcelUtils.exportExcel(results, TestResponse.class, fileName, params, response);
    }

    @ApiOperation("create result export")
    @GetMapping("create-order/export/{thread}")
    public void exportPresentCreateApi(@PathVariable("thread") Integer thread, HttpServletResponse response) throws IOException {
        TestResult testResult = testService.testPresentCreateConcurrent(thread);
        List<TestResponse> results = testResult.getResults();

        String fileName = "礼物创建api调用结果-模拟线程数" + thread;
        ExportParams params = new ExportParams();
        params.setCreateHeadRows(true);
        ExcelUtils.exportExcel(results, TestResponse.class, fileName, params, response);
    }
}
