package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktVrOrderTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/virtual-order")
public class VirtualOrderApiTestController {

    @Autowired
    private AsmktVrOrderTestService vrOrderTestService;

    @GetMapping("create-order-batch/{batchNum}")
    public TestResult testCreatingOrderBatch(@PathVariable("batchNum") Long batchNum) {
        return vrOrderTestService.testCreateOrderBatch(batchNum);
    }

    @GetMapping("create-order-batch/csv-file/{num}")
    public String getCreateOrderBatchV3Csv(@PathVariable("num") Integer num, @RequestParam("filePath") String filePath) {
        vrOrderTestService.generateCreateOrderBatch(num, filePath);
        return "success";
    }
}
