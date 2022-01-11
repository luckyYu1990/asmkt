package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktVrOrderTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/virtual-order")
public class VirtualOrderApiTestController {

    @Autowired
    private AsmktVrOrderTestService vrOrderTestService;

    @GetMapping("create-order-batch/{batchNum}")
    public TestResult testCreatingOrderBatch(@PathVariable("batchNum") Long batchNum) {
        return vrOrderTestService.testCreateOrderBatch(batchNum);
    }
}
