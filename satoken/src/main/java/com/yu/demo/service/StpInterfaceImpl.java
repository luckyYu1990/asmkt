package com.yu.demo.service;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object o, String s) {
        String[] arrs = {"101", "user-add", "user-delete", "user-update", "article-get"};
        return Arrays.asList(arrs);
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        String[] arrs = {"admin", "super-admin"};
        return Arrays.asList(arrs);
    }
}
