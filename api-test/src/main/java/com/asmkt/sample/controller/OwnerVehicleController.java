package com.asmkt.sample.controller;

import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.test.AsmktVehicleTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "owner-vehicle-api")
@RestController
@RequestMapping("vehicle-api")
public class OwnerVehicleController {
    @Autowired
    private AsmktVehicleTestService testService;

    @ApiOperation("vehicle login")
    @GetMapping("external-login")
    public TestResult testLogin() {
        return testService.testExternalLogin();
    }


}
