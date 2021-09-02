package com.asmkt.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.constant.AsmktPresentConstant;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.httpclient.HttpApiService;
import com.asmkt.sample.service.AsmktPresentService;
import com.asmkt.sample.utils.SHA256Utils;
import com.asmkt.sample.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 在赢端快礼物平台接口测试
 */
@Service
@Slf4j
public class AsmktPresentServiceImpl implements AsmktPresentService {

    //private final String testUrl = "http://testapi.kuailiwu.com/openapiV3/api/Request";
    private final String testUrl = "http://192.168.1.13/openapiV3/api/Request";

    private final String privatePkcs8 = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC+ZoCVkZb6T6PZ" +
            "msHrmC5KxhXk5uPmles4aPqKe6/eDjOysk74PuSwrQMlPepdAasNYADeX6ZwfoDD" +
            "cDSz7iyUQzzIxl6l8i4k4qcGwrNy5wVhBhvloflTxovXYlExTsBAD9dwsdTWThEm" +
            "IhI9/MPoOq52OYR/4DHfkuu4dztITe2QHH8rvlEloaVoD/EzpOX505Ip+6obF8QF" +
            "ShY0e/3h7P3p8yRdE1Fl/zyLe8JUs+0fPhfWesGiKus1MoL3k2AUCD4qroetLPas" +
            "4VbaIXdw03/bL5YjVv5vK2RTi0y5eO1HNV/DisTA5W9y0zVOT8BHbms3OhZ7VREU" +
            "5T1BXi/NAgMBAAECggEBAITb+8/ipsqF9hyrC7Q9HdhZfv+aSd8mL9vODT3C70Zw" +
            "/IwuNkKtiGDsaWx8VuSNfHG50wFZF0gaTfmSe4Qup3bRsEGdgoAjjSV57vkdtaBx" +
            "KmS4oZDMRLG7susZlPRmMmrBYyElRhJAqWDFl5sfnQiYohKnMhkVNVgAKnIu6PQb" +
            "EIa/e8utFb5Q+acyfI/6anUlXLgPrZhq+Xvb1B9b4fnFjkup9zIWH9RL0ywT+hni" +
            "/9Bi5KSQacW3oeRFBXhVCiaZDP9hTWHsyILqRJryzxp2BmMirXybdRgiXaA71CxI" +
            "wEKkhzz8EedDqK9ZJO/DnZ8xudatV+h6FTpkRQe2akECgYEA4M1+g6mkL17FxTwX" +
            "Jdd1jwNH19O9FNe+hwWBnr2d9L9kgYulO6SdLNLI1SfacfG0noxKdKx3VIi0gTe3" +
            "iVaOF8WlupMw4tnbX/3KGy7aF65zs2KGrftM6Bn0tdH8tahyYATI2/W2/BA5hMvy" +
            "xDfGin9N3VkXFovOQ/ab0EljcNECgYEA2NLObJG1uBc90VBXSgcpz+R7cyU1s3an" +
            "/xYzfQnxPBsvh0LBXh1spMQNDgS13PO7WAbdVFB7ccR2Ae+agRYWeJcvYQWg/Cy2" +
            "JGdGnelz9LPXWXXiAhLF6VPeFY4x0vsTkdBiMWHp74hOofITMmFZetKGtgZyUcjJ" +
            "7Y5IQ7eq7j0CgYEAtd7qtOBhGYXmIPzNiWr4C/zOedkvBxbIaPpWdLPZf/H8ASaW" +
            "RluWYXYizviQLVEsHH1Jm30Qic6TolsOXMku6iKYQwrkKROx/MYZZcaQ4UndB0r1" +
            "xPojrT+akCLT/a/K4M3eJr5zDRifQEW0IXUyZkg6GZMDL9VpyehRBMDq95ECgYEA" +
            "2LbbW+jHmJiiUPPcS0GdqQzV1uD6K8YNZAjsra2iUFFbz/YJD+iZDpZl9fz/STjT" +
            "p8g0sHFA8RVMXeL+qsdI7n8t++uEXIjUBM605mDQZWTbea85lpjs96m78A+P/TC2" +
            "/qcpffI/wAqdjYBHb7OdnAWQX59R5Cysp6Pu1sBdOIECgYEAm/KNwy39gjbAwCXS" +
            "uJitq6kYMF4yhj9ankty1Q9iBPrRSrQUnXNObsI9Kzx6q2T0gLK3YDitI745PN0D" +
            "KM/Z9skUDO3AL2gBkIdsbj/zWh4HdrCrxUJjM1FR7uGXhxDGaznghS5yvNoKxrW1" +
            "pMi68jop7LaUms0kAzuBaFSi33Y=";

    @Autowired
    private HttpApiService apiService;

    @Override
    public TestResponse createOrder() {
        return createOrder(null, null);
    }

    @Override
    public TestResponse createOrder(String clientOrderId) {
        return createOrder(clientOrderId, null);
    }

    public TestResponse createOrder(String clientOrderId, Long productCode) {
        log.info("===========================create order=====================================");
        Map<String, String> params = getPublicParams();
        params.put("Method", "Asmkt.OpenApi.Order.Create");
        JSONObject json = new JSONObject();
        Long pCode = productCode != null ? productCode : AsmktPresentConstant.GOOD_ID;
        json.put("ProductCode", pCode);
        String randomOrderId = clientOrderId == null ? RandomStringUtils.randomNumeric(20) : clientOrderId.toString();
        json.put("ClientOrderId", randomOrderId);
        json.put("AccountNo", "111112");
        json.put("CallbackUrl", "");
        getParamRequestData(params, json);
        String sign = generateSign(params);
        params.put("Sign", sign);
        List<ApiParam> paramList = toParamList(params);
        TestResponse response = postJsonWithParams(paramList);
        getResponseReturnData(response);
        return response;
    }

    @Override
    public TestResponse createOrderError() {
        return createOrder(null, 1234L);
    }

    @Override
    public TestResponse queryOrder(Long clientOrderId) {
        log.info("===========================query order=====================================");
        Map<String, String> params = getPublicParams();
        params.put("Method", "Asmkt.OpenApi.Order.Query");
        JSONObject json = new JSONObject();
        Long coi = clientOrderId != null ? clientOrderId : AsmktPresentConstant.CLIENT_ORDER_ID;
        json.put("ClientOrderId", coi);
        getParamRequestData(params, json);
        String sign = generateSign(params);
        params.put("Sign", sign);
        List<ApiParam> paramList = toParamList(params);
        TestResponse response = postJsonWithParams(paramList);
        getResponseReturnData(response);
        return response;
    }

    private void getParamRequestData(Map<String, String> params, JSONObject json) {
        String requestData = new String(Base64.getEncoder().encode(json.toJSONString().getBytes()), StandardCharsets.UTF_8);
        params.put("RequestData", requestData);
    }

    @Override
    public TestResponse queryOrder() {
        return queryOrder(null);
    }

    private void getResponseReturnData(TestResponse response) {
        String responseContent = response.getResponseContent();
        if (StringUtils.isBlank(responseContent)) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(responseContent);
        String decodedData = new String(Base64.getDecoder().decode(jsonObject.getString("ReturnData")));
        JSONObject obj = JSONObject.parseObject(decodedData);
        response.setDecodedReturnData(obj.toString());
    }

    private TestResponse postJsonWithParams(List<ApiParam> paramList) {
        StopWatch stopWatch = getStopWatch();
        stopWatch.start();
        TestResponse response = apiService.doPostJson(testUrl, paramList);
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

    private Map<String, String> getPublicParams() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("AppId", "112738245207001");
        paramMap.put("InterfaceId", "1");
        paramMap.put("Version", "3.0");
        paramMap.put("NonceString", RandomStringUtils.randomAlphanumeric(24));
        paramMap.put("SignMode", "pkcs8");
        return paramMap;
    }

    private String generateSign(Map<String, String> params) {
        Map<String, String> signMap = new HashMap<>(params);
        signMap.remove("SignMode");
        String signUrl = SignUtils.getSignUrl(signMap, "ade0641358114820b99b597b3469d1b8");
        return SHA256Utils.sign(privatePkcs8, signUrl);
    }

    private ApiParam makeParam(String key, String val) {
        ApiParam apiParam = new ApiParam();
        apiParam.setKey(key);
        apiParam.setValue(val);
        return apiParam;
    }
}
