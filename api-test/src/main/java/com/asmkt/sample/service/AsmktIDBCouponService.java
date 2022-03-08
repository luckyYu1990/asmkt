package com.asmkt.sample.service;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;

public interface AsmktIDBCouponService {

    TestResponse createCoupon();

    TestResponse queryCoupon();

    TestResponse cancelCoupon();

    JSONObject getCreateCouponParams();

    JSONObject getQueryCouponParams();

}
