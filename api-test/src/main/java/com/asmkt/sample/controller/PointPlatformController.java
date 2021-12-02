package com.asmkt.sample.controller;

import com.asmkt.sample.controller.vo.PointsParamVo;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktPointPlatformTestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("point")
public class PointPlatformController {
    @Autowired
    private AsmktPointPlatformTestService ppTestService;

    @ApiOperation("get points")
    @GetMapping("{appId}/{userId}/{thread}")
    public TestResult getPoint(@PathVariable("appId") String appId, @PathVariable("userId") String userId,
                               @PathVariable("thread") Integer  thread) {
        return ppTestService.testGetPoints(appId, userId, thread);
    }

    @ApiOperation("recharge points")
    @PostMapping("recharge/{thread}")
    public TestResult rechargePoints(@PathVariable("thread") int thread, @RequestBody PointsParamVo vo) {
        return ppTestService.testRechargePoints(vo, thread);
    }

    @ApiOperation("consume points")
    @PostMapping("consume/{thread}")
    public TestResult consumePoints(@PathVariable("thread") int thread, @RequestBody PointsParamVo vo) {
        return ppTestService.testConsumePoints(vo, thread);
    }

    @ApiOperation("refund points")
    @PostMapping("refund/{thread}")
    public TestResult refundPoints(@PathVariable("thread") int thread, @RequestBody PointsParamVo vo) {
        return ppTestService.testRefundPoints(vo, thread);
    }

    @ApiOperation("recharge users points")
    @PostMapping("recharge/users")
    public TestResult rechargePointsUsers(@RequestBody PointsParamVo vo) {
        return ppTestService.testRefundPointsUsers(vo);
    }
}
