package com.asmkt.sample.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class PointsParamVo {
    private String userId;
    private String point;
    private int appId;
    private String expiredTime;
    private String operationId;
    private List<String> userIds;
}
