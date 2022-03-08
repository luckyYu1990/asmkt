package com.asmkt.sample.service;

import com.asmkt.sample.domain.TestResponse;

public interface AsmktPresentService {

    TestResponse createOrder();

    TestResponse queryOrder();

    TestResponse createOrder(String clientOrderId);

    TestResponse createOrderError();

    TestResponse queryOrder(Long clientOrderId);

    String getCreateOrderParam();

    String getQueryOrderParam();

    String getProductListParam();

    String getQueryBalanceParam();
}
