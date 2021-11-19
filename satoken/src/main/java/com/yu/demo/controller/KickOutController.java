package com.yu.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kick-out")
public class KickOutController {

    @RequestMapping("kick-out")
    public SaResult kickOut() {
        StpUtil.kickout(10001);
        return SaResult.ok();
    }

    @RequestMapping("disable")
    public SaResult disable() {
        StpUtil.disable(10001, 86400);
        return SaResult.ok();
    }
}
