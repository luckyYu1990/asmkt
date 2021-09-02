package com.asmkt.sample.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SignUtils {

    public static String getSignUrl(Map<String, String> params, String appSecret) {
        Map<String, String> signMap = new LinkedHashMap<>(params.size());
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> signMap.put(e.getKey().toLowerCase(), e.getValue()));
        signMap.put("appsecret", appSecret);
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : signMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key).append("=").append(value).append("&");
        }
        String result = builder.toString();
        return result.substring(0, result.length() - 1);
    }
}
