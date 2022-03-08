package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.service.AsmktBaseService;
import com.asmkt.sample.service.AsmktVrOrderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class AsmktVrOrderServiceImpl extends AsmktBaseService implements AsmktVrOrderService {

    @Override
    public TestResponse createOrderBatch(Long batchNum) {
        String url = "http://47.100.68.250:9104/api/VirtualOrder/v3/BatchCreate";
        JSONArray paramArray = new JSONArray();
        for (int i = 0; i < batchNum; i++) {
            JSONObject obj = new JSONObject();
            obj.put("AppId", "522273113600002");
            obj.put("ClientOrderId", RandomStringUtils.randomAlphanumeric(10));
            obj.put("ProductCode", "115242413115936");
            obj.put("AccountNo", "13800138001");
            paramArray.add(obj);
        }
        return postJsonArrayWithParams(paramArray, url);
    }

    @Override
    public JSONArray getCreateOrderBatchParams(Integer num) {
        JSONArray paramArray = new JSONArray();
        for (int i = 0; i < num; i++) {
            JSONObject obj = new JSONObject();
            obj.put("AppId", "522273113600002");
            obj.put("ClientOrderId", RandomStringUtils.randomAlphanumeric(10));
            obj.put("ProductCode", "115242413115936");
            obj.put("AccountNo", "13800138001");
            paramArray.add(obj);
        }
        return paramArray;
    }
}
