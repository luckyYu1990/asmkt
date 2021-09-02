package com.asmkt.sample.constant.enums;

public enum PresentCreateResultEnum implements IEnum{
    SUCCESS(1, "处理成功"),
    PARAM_ERROR(-10, "参数错误"),
    INSUFFICIENT_BALANCE(-40, "余额不足"),
    INTERNAL_ERROR(-50, "内部异常"),
    OVERBOOKING(-60, "重复下单"),
    INSUFFICIENT_INVENTORY(-70, "库存不足"),
    ORDER_ERROR(-80, "下单商品错误"),
    ORDER_FAILED_MULTIPLE_TIMES(-90, "下单失败多次，纳入黑名单")
    ;

    private Integer code;
    private String desc;

    PresentCreateResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return desc;
    }
}
