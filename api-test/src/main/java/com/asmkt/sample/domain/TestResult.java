package com.asmkt.sample.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.List;

@Data
public class TestResult {
    @Excel(name = "总共", width = 30, needMerge = true)
    private Integer total;
    @Excel(name = "总成功数", width = 30, needMerge = true)
    private Integer successTotal;
    @Excel(name = "总失败数", width = 30, needMerge = true)
    private Integer failedTotal;
    @Excel(name = "成功比例", width = 30, needMerge = true)
    private Double successRate;
    @Excel(name = "结果", width = 30, needMerge = true)
    private List<TestResponse> results;
}
