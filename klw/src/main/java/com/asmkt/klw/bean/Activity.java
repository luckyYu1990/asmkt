package com.asmkt.klw.bean;

import lombok.Data;

@Data
public class Activity {
    private Long id;
    private String activityName;
    private Long clientId;
    private Long clientAccountId;
}
