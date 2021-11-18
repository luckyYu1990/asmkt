package com.asmkt.sample.service;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.utils.AESUtils;
import com.asmkt.sample.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class AsmktRightsService {

    private static final String secretKey = "b22c99226b544386a96743d2827c1705";
    @Autowired
    private HttpApiService apiService;

    public TestResponse getCoupon() {
        log.info("get coupon =====================>");
        String url = "http://testkyxapi.kliwu.com/GiftCoupon";
        String appId = "8d4cddce-36be-4d2b-f822-08d98d2b8c14";
        String activityId = "2f241f77-91a1-418d-a8c4-2dfbd403eff4";
        String productId = "123";
        Integer quantity = 1;
        String phoneNo = "13800138000";
        String businessNo = RandomStringUtils.randomAlphanumeric(18);
        String expirationDate = "2021-11-30";
        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        JSONObject param = new JSONObject();
        param.put("AppId", appId);
        param.put("ActivityId", activityId);
        param.put("ProductId", productId);
        param.put("Quantity", quantity);
        param.put("PhoneNo", phoneNo);
        param.put("BusinessNo", businessNo);
        param.put("ExpirationDate", expirationDate);
        param.put("Timestamp", timeStamp);
        //param.put("UserIdentity", "13800138000");
        String sign = getSign(param);
        param.put("Sign", sign);
        String data = AESUtils.encrypt(param.toJSONString(), secretKey);
        String urlData = getUrlEncodeData(data);
        url = url + "?appId=" + appId + "&data=" + urlData;
        return doGetTestUrl(url);
    }

    private String getUrlEncodeData(String data) {
        String urlData = "";
        try {
            urlData = URLEncoder.encode(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            //
        }
        return urlData;
    }

    private TestResponse doGetTestUrl(String url) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doGet(url);
        stopWatch.stop();
        response.setResponseCost(stopWatch.getTotalTimeMillis() + " millis");
        response.setResponseCostValue(stopWatch.getTotalTimeMillis());
        response.setResponseCostUnit("millis");
        return response;
    }

    private StopWatch getStopWatch() {
        String thName = Thread.currentThread().getName();
        String watchName = "recharge " + thName;
        System.out.println(watchName);
        return new StopWatch(watchName);
    }

    private String getSign(JSONObject param) {
        Map<String, Object> signMap = new LinkedHashMap<>(param.size());
        /*param.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> signMap.put(e.getKey().toLowerCase(), e.getValue()));*/
        signMap.put("appid", param.get("AppId"));
        signMap.put("activityid", param.get("ActivityId"));
        signMap.put("productid", param.get("ProductId"));
        signMap.put("quantity", param.get("Quantity"));
        signMap.put("phoneno", param.get("PhoneNo"));
        signMap.put("businessno", param.get("BusinessNo"));
        signMap.put("expirationdate", param.get("ExpirationDate"));
        signMap.put("timestamp", param.get("Timestamp"));
        //signMap.put("useridentity", param.get("UserIdentity"));
        //signMap.remove("expirationdate");
        signMap.put("secretkey", secretKey);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : signMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.append(key).append("=").append(value).append("&");
        }
        String result = builder.toString();
        result = result.substring(0, result.length() - 1);
        return MD5Utils.getMD5Str(result).toUpperCase();
    }

    public TestResponse thirdLogin() {
        log.info("third login======================>");
        String url = "http://testkyx.kliwu.com/ThirdLogin";
        String appId = "8d4cddce-36be-4d2b-f822-08d98d2b8c14";
        String activityId = "2f241f77-91a1-418d-a8c4-2dfbd403eff4";
        String userIdentity = "13800138000";
        String lotteryIdentification = RandomStringUtils.randomAlphanumeric(12);
        String lotteryNumber = "1";
        String productId = "";
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        JSONObject params = new JSONObject();
        params.put("AppId", appId);
        params.put("ActivityId", activityId);
        params.put("UserIdentity", userIdentity);
        params.put("LotteryIdentification", lotteryIdentification);
        params.put("LotteryNumber", lotteryNumber);
        params.put("ProductId", productId);
        params.put("Timestamp", timestamp);
        String sign = getSign(params);
        params.put("Sign", sign);
        String data = AESUtils.encrypt(params.toJSONString(), secretKey);
        String urlData = getUrlEncodeData(data);
        url = url + "?appId=" + appId + "&data=" + urlData;
        return doGetTestUrl(url);
    }
}
