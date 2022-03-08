package com.asmkt.sample.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;

public interface AsmktVrOrderService {
    TestResponse createOrderBatch(Long batchNum);

    JSONArray getCreateOrderBatchParams(Integer num);
}
