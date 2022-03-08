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

@Slf4j
@Service
public class AsmktRightsService {

    private static final String secretKey = "e7972b9937a64f1d8d901a43548431d9";
    private static final String appId = "32247cb6-a0cb-461c-502f-08d9dfa3462c";
    private static final String activityId = "5d87db34-79e2-48cf-c3ca-08d9fbf8cd3c";
    @Autowired
    private HttpApiService apiService;

    public TestResponse getCoupon() {
        log.info("get coupon =====================>");
        String url = "http://testkyxapi.kliwu.com/GiftCoupon";
        /*String appId = "8d4cddce-36be-4d2b-f822-08d98d2b8c14";
        String activityId = "2f241f77-91a1-418d-a8c4-2dfbd403eff4";*/
        String productId = "";
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
        String sign = getCouponSign(param);
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

    private String getCouponSign(JSONObject param) {
        String result = "appid=" + param.get("AppId") + '&' +
                "activityid=" + param.get("ActivityId") + '&' +
                "productid=" + param.get("ProductId") + '&' +
                "quantity=" + param.get("Quantity") + '&' +
                "phoneno=" + param.get("PhoneNo") + '&' +
                "businessno=" + param.get("BusinessNo") + '&' +
                "timestamp=" + param.get("Timestamp") + '&' +
                "secretkey=" + secretKey;
        return MD5Utils.getMD5Str(result).toUpperCase();
    }

    public TestResponse thirdLogin() {
        log.info("third login======================>");
        String url = "http://testkyxapi.kliwu.com/ThirdLogin";
        /*String appId = "32247cb6-a0cb-461c-502f-08d9dfa3462c";
        String activityId = "2d1c1d2a-a1a7-4126-17dc-08d9defec6e1";*/
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
        String sign = getLoginSign(params);
        params.put("Sign", sign);
        params.put("Extend1", "");
        params.put("Extend2", "");
        params.put("Extend3", "");
        String data = AESUtils.encrypt(params.toJSONString(), secretKey);
        String urlData = getUrlEncodeData(data);
        url = url + "?appId=" + appId + "&data=" + urlData;
        return doGetTestUrl(url);
    }

    private String getLoginSign(JSONObject param) {
        String result = "appid=" + param.get("AppId") + '&' +
                "activityid=" + param.get("ActivityId") + '&' +
                "useridentity=" + param.get("UserIdentity") + '&' +
                "lotteryidentification=" + param.get("LotteryIdentification") + '&' +
                "lotterynumber=" + param.get("LotteryNumber") + '&' +
                "productid=" + param.get("ProductId") + '&' +
                "extend1=&" +
                "extend2=&" +
                "extend3=&" +
                "timestamp=" + param.get("Timestamp") + '&' +
                "secretkey=" + secretKey;
        return MD5Utils.getMD5Str(result).toUpperCase();
    }

    public JSONObject getCouponParams() {
        log.info("get coupon =====================>");
        /*String appId = "32247cb6-a0cb-461c-502f-08d9dfa3462c";
        String activityId = "5d87db34-79e2-48cf-c3ca-08d9fbf8cd3c";*/
        String productId = "";
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
        String sign = getCouponSign(param);
        param.put("Sign", sign);
        String data = AESUtils.encrypt(param.toJSONString(), secretKey);
        String urlData = getUrlEncodeData(data);
        //url = url + "?appId=" + appId + "&data=" + urlData;
        JSONObject json = new JSONObject();
        json.put("appId", appId);
        json.put("data", urlData);
        return json;
    }

    public JSONObject getThirdLoginParams() {
        log.info("third login======================>");
        String url = "http://testkyxapi.kliwu.com/ThirdLogin";
        /*String appId = "32247cb6-a0cb-461c-502f-08d9dfa3462c";
        String activityId = "2d1c1d2a-a1a7-4126-17dc-08d9defec6e1";*/
        //String userIdentity = "13800138000";
        String userIdentity = RandomStringUtils.randomNumeric(11);
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
        String sign = getLoginSign(params);
        params.put("Sign", sign);
        params.put("Extend1", "");
        params.put("Extend2", "");
        params.put("Extend3", "");
        String data = AESUtils.encrypt(params.toJSONString(), secretKey);
        String urlData = getUrlEncodeData(data);
        JSONObject obj = new JSONObject();
        obj.put("appId", appId);
        obj.put("data", urlData);
        return obj;
    }
}
