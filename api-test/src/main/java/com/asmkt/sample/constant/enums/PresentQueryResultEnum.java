package com.asmkt.sample.constant.enums;

public enum PresentQueryResultEnum implements IEnum{
    SUCCESS(1, "成功"),
    PROCESSING(0, "处理中"),
    FAILED(-1, "失败"),
    NO_SUCH_ORDER(-2, "无此订单"),
    ;

    private Integer code;
    private String desc;

    PresentQueryResultEnum(Integer code, String desc) {
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
