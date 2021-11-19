package com.yu.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/session")
@RestController
public class SessionController {

    @RequestMapping("/get")
    public SaResult session() {
        StpUtil.getSession().set("user", "123");
        System.out.println(StpUtil.getSession().get("user") + "============>");
        return SaResult.ok();
    }
}
