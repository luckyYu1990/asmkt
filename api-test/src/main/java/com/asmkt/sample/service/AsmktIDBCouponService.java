package com.asmkt.sample.service;

import com.asmkt.sample.domain.TestResponse;

public interface AsmktIDBCouponService {

    TestResponse createCoupon();

    TestResponse queryCoupon();

    TestResponse cancelCoupon();
}
