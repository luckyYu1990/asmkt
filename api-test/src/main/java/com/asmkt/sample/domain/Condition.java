package com.asmkt.sample.domain;

import com.asmkt.sample.constant.enums.IEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Condition {

    private IEnum expectResult;

    private String resultKeyName;

    private Long expectCostTime;

    private String costTimeKeyName;

    private boolean checkResultCode;

    private boolean checkCostTime;

    public boolean isCheckCostTime() {
        return expectCostTime != null;
    }

    public boolean isCheckResultCode() {
        return expectResult != null;
    }
}
