package com.asmkt.sample.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("entity")
public class EntityOrderTestController {

    /**
     * 下单
     */
    @ApiOperation("place order")
    @PostMapping("place-order/concurrent/{thread}")
    public void testPlaceOrder(@PathVariable(value = "thread") Integer thread) {

    }

    /**
     * 订单详情
     */
    @ApiOperation("order detail")
    @GetMapping("detail/concurrent/{thread}")
    public void testOrderDetail(@PathVariable(value = "thread") Integer thread) {

    }

    /**
     * 快递信息
     */
    @ApiOperation("order express")
    @GetMapping("express/concurrent/{thread}")
    public void testOrderExpress(@PathVariable(value = "thread") Integer thread) {

    }

    /**
     * 退单
     */
    @ApiOperation("charge back")
    @PostMapping("charge-back/concurrent/{thread}")
    public void testChargeBack(@PathVariable(value = "") Integer thread) {

    }

    /**
     * 开票
     */
    @ApiOperation("")
    @PostMapping("invoice/concurrent/{thread}")
    public void testInvoice() {

    }
}
