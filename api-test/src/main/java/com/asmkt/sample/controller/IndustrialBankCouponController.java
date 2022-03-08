package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktIDBCouponTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("idb")
public class IndustrialBankCouponController {

    @Autowired
    private AsmktIDBCouponTestService couponTestService;

    @ApiOperation("create coupon")
    @PostMapping("create-coupon/concurrent/{thread}")
    public TestResult testCreateCoupon(@PathVariable(value = "thread") Integer thread) {
        return couponTestService.createCouponConcurrent(thread);
    }

    @ApiOperation("get create coupon csv file")
    @GetMapping("create-coupon/csv-file/{num}")
    public String generateCreateCouponParamCsv(@PathVariable("num") Integer num, @RequestParam("filePath") String filePath) {
        couponTestService.generateCreateCouponParamCsv(num, filePath);
        return "success";
    }

    @ApiOperation("query coupon")
    @GetMapping("/concurrent/{thread}")
    public TestResult testQueryCoupon(@PathVariable(value = "thread") Integer thread) {
        return couponTestService.queryCouponConcurrent(thread);
    }

    @ApiOperation("query coupon csv")
    @GetMapping("/query/csv-file/{num}")
    public String generateQueryCouponParamCsv(@PathVariable("num") Integer num, @RequestParam("filePath") String filePath) {
        couponTestService.generateQueryCouponParamCsv(num, filePath);
        return "success";
    }

    @ApiOperation("cancel coupon")
    @DeleteMapping("/concurrent/{thread}")
    public TestResult testCancelCoupon(@PathVariable(value = "thread") Integer thread) {
        return couponTestService.cancelCouponConcurrent(thread);
    }
}
