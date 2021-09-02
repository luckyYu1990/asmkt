package com.asmkt.klw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan(basePackages = {
		"com.asmkt.klw.mapper"})
@SpringBootApplication
@EnableDiscoveryClient
public class KlwApplication {

	public static void main(String[] args) {
		SpringApplication.run(KlwApplication.class, args);
	}

}
