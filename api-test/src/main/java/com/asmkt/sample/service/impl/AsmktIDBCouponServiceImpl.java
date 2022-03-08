package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.AsmktIDBCouponService;
import com.asmkt.sample.utils.RSAUtils;
import com.asmkt.sample.utils.SHA1Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AsmktIDBCouponServiceImpl implements AsmktIDBCouponService {

    private final static String publicKey = "305C300D06092A864886F70D0101010500034B00304802410082D44B3266EE3A557B86F3183882208341B5000B7C2271697B27013C10740BDD891711D9B08B414BC8A7EB01BF1A6233E68B9859EB52B2E40FDCE57BC70133410203010001";
    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse createCoupon() {
        log.info("create coupon");
        String url = "https://testkyxpre.kliwu.com/CIB/Home/CreateCoupon";
        String reqCode = "voucher.create";
        String storeId = "ZQD001";
        String memId = "ZQD001";
        String reqSerialNo = RandomStringUtils.randomAlphanumeric(32);
        String encodeParams = getEncodeParams();
        Long timestamp = System.currentTimeMillis();
        String verifySign = getSign(storeId, memId, encodeParams, timestamp.toString());
        String posId = RandomStringUtils.randomAlphanumeric(18);
        JSONObject obj = new JSONObject();
        obj.put("req_code", reqCode);
        obj.put("store_id", storeId);
        obj.put("mem_id", memId);
        obj.put("req_serial_no", reqSerialNo);
        obj.put("encode_params", encodeParams);
        obj.put("timestamp", timestamp);
        obj.put("verify_sign", verifySign);
        obj.put("pos_id", posId);
        List<ApiParam> apiParams = toParamList(obj);
        ApiParam apiParam = new ApiParam();
        apiParam.setKey("data");
        apiParam.setValue(obj.toJSONString());
        apiParams.add(apiParam);
        return postWithParams(apiParams, url);
    }

    @Override
    public JSONObject getCreateCouponParams() {
        log.info("create coupon");
        String url = "https://testkyxpre.kliwu.com/CIB/Home/CreateCoupon";
        String reqCode = "voucher.create";
        String storeId = "ZQD001";
        String memId = "ZQD001";
        String reqSerialNo = RandomStringUtils.randomAlphanumeric(32);
        String encodeParams = getEncodeParams();
        Long timestamp = System.currentTimeMillis();
        String verifySign = getSign(storeId, memId, encodeParams, timestamp.toString());
        String posId = RandomStringUtils.randomAlphanumeric(18);
        JSONObject obj = new JSONObject();
        obj.put("req_code", reqCode);
        obj.put("store_id", storeId);
        obj.put("mem_id", memId);
        obj.put("req_serial_no", reqSerialNo);
        obj.put("encode_params", encodeParams);
        obj.put("timestamp", timestamp);
        obj.put("verify_sign", verifySign);
        obj.put("pos_id", posId);
        obj.put("data", obj.toJSONString());
        return obj;
    }

    @Override
    public JSONObject getQueryCouponParams() {
        log.info("query coupon");
        String url = "https://testkyxpre.kliwu.com/CIB/Home/QueryCoupon";
        String reqCode = "voucher.create";
        String storeId = "ZQD001";
        String memId = "ZQD001";
        String encodeParams = getQueryEncodeParams();
        Long timestamp = System.currentTimeMillis();
        String verifySign = getSign(storeId, memId, encodeParams, timestamp.toString());
        JSONObject obj = new JSONObject();
        obj.put("req_code", reqCode);
        obj.put("store_id", storeId);
        obj.put("mem_id", memId);
        obj.put("encode_params", encodeParams);
        obj.put("timestamp", timestamp);
        obj.put("verify_sign", verifySign);
        obj.put("data", obj.toJSONString());
        return obj;
    }

    @Override
    public TestResponse queryCoupon() {
        log.info("query coupon");
        String url = "https://testkyxpre.kliwu.com/CIB/Home/QueryCoupon";
        String reqCode = "voucher.create";
        String storeId = "ZQD001";
        String memId = "ZQD001";
        String encodeParams = getQueryEncodeParams();
        Long timestamp = System.currentTimeMillis();
        String verifySign = getSign(storeId, memId, encodeParams, timestamp.toString());
        JSONObject obj = new JSONObject();
        obj.put("req_code", reqCode);
        obj.put("store_id", storeId);
        obj.put("mem_id", memId);
        obj.put("encode_params", encodeParams);
        obj.put("timestamp", timestamp);
        obj.put("verify_sign", verifySign);
        List<ApiParam> apiParams = toParamList(obj);
        ApiParam apiParam = new ApiParam();
        apiParam.setKey("data");
        apiParam.setValue(obj.toJSONString());
        apiParams.add(apiParam);
        return postWithParams(apiParams, url);
    }

    @Override
    public TestResponse cancelCoupon() {
        log.info("cancel coupon");
        String url = "https://testkyxpre.kliwu.com/CIB/Home/CancelCoupon";
        String reqCode = "voucher.create";
        String storeId = "ZQD001";
        String memId = "ZQD001";
        String encodeParams = getQueryEncodeParams();
        Long timestamp = System.currentTimeMillis();
        String verifySign = getSign(storeId, memId, encodeParams, timestamp.toString());
        JSONObject obj = new JSONObject();
        obj.put("req_code", reqCode);
        obj.put("store_id", storeId);
        obj.put("mem_id", memId);
        obj.put("encode_params", encodeParams);
        obj.put("timestamp", timestamp);
        obj.put("verify_sign", verifySign);
        List<ApiParam> apiParams = toParamList(obj);
        ApiParam apiParam = new ApiParam();
        apiParam.setKey("data");
        apiParam.setValue(obj.toJSONString());
        apiParams.add(apiParam);
        return postWithParams(apiParams, url);
    }

    private String getQueryEncodeParams() {
        String voucherId = "121193110056736";
        String voucherCode = "21121708155800002";
        String mobileNo = "12345678901";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("voucher_id", voucherId);
        jsonObject.put("voucher_code", voucherCode);
        jsonObject.put("mobile_no", mobileNo);
        return RSAUtils.encryptByPublicKey(jsonObject.toJSONString(), publicKey);
    }

    private TestResponse postWithParams(List<ApiParam> params, String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPost(url, params);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    private TestResponse postJsonWithParams(List<ApiParam> paramList, String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPostJson(url, paramList);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    private StopWatch getStopWatch() {
        String thName = Thread.currentThread().getName();
        String watchName = "query order " + thName;
        System.out.println(watchName);
        return new StopWatch(watchName);
    }

    private List<ApiParam> toParamList(Map<String, String> params) {
        List<ApiParam> paramList = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            ApiParam param = new ApiParam();
            param.setKey(entry.getKey());
            param.setValue(entry.getValue());
            paramList.add(param);
        }
        return paramList;
    }

    private List<ApiParam> toParamList(JSONObject jsonObj) {
        List<ApiParam> paramList = new ArrayList<>();
        jsonObj.forEach((key, value) -> {
            ApiParam param = new ApiParam();
            param.setKey(key);
            param.setValue(value.toString());
            paramList.add(param);
        });
        return paramList;
    }

    private String getSign(String storeId, String memId, String encodeParams, String timestamp) {
        return SHA1Util.encrypt(storeId + memId + encodeParams + timestamp);
    }

    private String getEncodeParams() {
        String voucherId = "121193110056736";
        Integer voucherNum = 1;
        String mobileNo = "12345678901";
        String sendMsg = "1111";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("voucher_id", voucherId);
        jsonObject.put("voucher_num", voucherNum);
        jsonObject.put("mobile_no", mobileNo);
        jsonObject.put("send_msg", sendMsg);
        return RSAUtils.encryptByPublicKey(jsonObject.toJSONString(), publicKey);
    }
}
