package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktRightsTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 快营销权益
 */
@RestController
@RequestMapping("asmkt")
public class AsmktRightsController {

    @Autowired
    private AsmktRightsTestService rightsService;

    @ApiOperation("get coupon")
    @GetMapping("coupon/{thread}")
    public TestResult getCoupon(@PathVariable("thread") Integer thread) {
        return rightsService.testGetCoupon(thread);
    }

    @ApiOperation("get coupon csv file")
    @GetMapping("coupon/csv-file/{num}")
    public String getCouponParams(@PathVariable("num") Integer num, @RequestParam("filePath") String filePath) {
        rightsService.getCouponParams(num, filePath);
        return "success";
    }

    @ApiOperation("third login")
    @PostMapping("third-login/{thread}")
    public TestResult login(@PathVariable("thread") Integer thread) {
        return rightsService.thirdLogin(thread);
    }

    @ApiOperation("third login param file")
    @GetMapping("third-login/csv-file/{num}")
    public String generateLoginParams(@PathVariable("num") Integer num, @RequestParam("filePath") String filePath) {
        rightsService.getThirdLoginParams(num, filePath);
        return "success";

    }
}
