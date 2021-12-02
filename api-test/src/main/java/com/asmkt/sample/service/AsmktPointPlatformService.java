package com.asmkt.sample.service;

import com.asmkt.sample.controller.vo.PointsParamVo;
import com.asmkt.sample.domain.TestResponse;

public interface AsmktPointPlatformService {
    TestResponse getPoints(String appId, String userId);

    TestResponse rechargePoints(PointsParamVo vo);

    TestResponse consumePoints(PointsParamVo vo);

    TestResponse refundPoints(PointsParamVo vo);

    TestResponse rechargePoints(PointsParamVo vo, String userId);
}
