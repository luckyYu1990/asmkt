package com.yu.demo.controller;

import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("route-permission")
public class RoutePermissionController {

    @RequestMapping("")
    public SaResult routePermission() {
        
        return SaResult.ok();
    }
}
