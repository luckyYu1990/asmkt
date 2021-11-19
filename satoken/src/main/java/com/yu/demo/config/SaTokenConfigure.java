package com.yu.demo.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaAnnotationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/do-login");
        /*registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            SaRouter.match("/user/**", () -> StpUtil.checkPermission("user"));
            SaRouter.match("/goods/**", () -> StpUtil.checkPermission("goods"));
            SaRouter.match("/student/**", () -> StpUtil.checkPermission("student"));
            SaRouter.match("/**", "/user/do-login", StpUtil::checkLogin);
            SaRouter.match("/admin/**", () -> StpUtil.checkRoleOr("admin", "super-admin"));
        }));*/

    }
}
