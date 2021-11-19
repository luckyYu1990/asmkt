package com.yu.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("permission")
public class PermissionController {

    @SaCheckPermission("user-update")
    @RequestMapping("update")
    public SaResult update() {
        return SaResult.ok();
    }

    @SaCheckPermission("user-delete")
    @RequestMapping("delete")
    public SaResult delete() {
        return SaResult.ok();
    }

    @SaCheckPermission("user-get")
    @RequestMapping("get")
    public SaResult get() {
        return SaResult.ok();
    }

    @SaCheckPermission("user-add")
    @RequestMapping("add")
    public SaResult add() {
        return SaResult.ok();
    }
}
