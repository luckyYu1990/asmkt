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

    @ApiOperation("query coupon")
    @GetMapping("/concurrent/{thread}")
    public TestResult testQueryCoupon(@PathVariable(value = "thread") Integer thread) {
        return couponTestService.queryCouponConcurrent(thread);
    }

    @ApiOperation("cancel coupon")
    @DeleteMapping("/concurrent/{thread}")
    public TestResult testCancelCoupon(@PathVariable(value = "thread") Integer thread) {
        return couponTestService.cancelCouponConcurrent(thread);
    }
}
