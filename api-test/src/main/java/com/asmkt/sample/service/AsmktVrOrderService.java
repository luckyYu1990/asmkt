package com.asmkt.sample.service;

import com.asmkt.sample.domain.TestResponse;

public interface AsmktVrOrderService {
    TestResponse createOrderBatch(Long batchNum);
}
