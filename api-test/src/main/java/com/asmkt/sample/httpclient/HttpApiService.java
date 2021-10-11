package com.asmkt.sample.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.asmkt.sample.domain.ApiParam;
import com.asmkt.sample.domain.TestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HttpApiService {
    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    public TestResponse doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try (CloseableHttpResponse response = this.httpClient.execute(httpGet)){
            if (response == null) {
                return TestResponse.noResponse();
            }
            Integer code = getResponseCode(response);
            String resultBody = getResultBody(response);
            TestResponse.TestResponseBuilder resBuilder = TestResponse.builder();
            resBuilder.code(code);
            resBuilder.responseContent(resultBody);
            return resBuilder.build();
        } catch (Exception e) {
            log.error("error to get {}", url, e);
            return TestResponse.error(e.getCause().getMessage());
        }
    }

    public TestResponse doGet(String url, List<ApiParam> paramList) {
        if (CollectionUtils.isEmpty(paramList)) {
            //throw new RuntimeException("Param list is empty");
            return doGet(url);
        }
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            for (ApiParam param : paramList) {
                uriBuilder.setParameter(param.getKey(), param.getValue());
            }
            return doGet(uriBuilder.build().toString());
        } catch (URISyntaxException e) {
            log.error("build request uri failed", e);
            return null;
        }
    }

    public TestResponse doPost(String url, List<ApiParam> params) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        generatePostParam(params, httpPost);
        try (CloseableHttpResponse response = this.httpClient.execute(httpPost)) {
            if (response == null) {
                return TestResponse.noResponse();
            }
            Integer code = getResponseCode(response);
            String resultBody = getResultBody(response);
            TestResponse.TestResponseBuilder resBuilder = TestResponse.builder();
            resBuilder.code(code);
            resBuilder.responseContent(resultBody);
            return resBuilder.build();
        } catch (Exception e) {
            log.error("error to post {}", url, e);
            return TestResponse.error(e.getMessage());
        }
    }

    public TestResponse doPostJson(String url, List<ApiParam> params) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        generatePostJsonParam(params, httpPost);
        try (CloseableHttpResponse response = this.httpClient.execute(httpPost)) {
            if (response == null) {
                return TestResponse.noResponse();
            }
            Integer code = getResponseCode(response);
            String resultBody = getResultBody(response);
            TestResponse.TestResponseBuilder resBuilder = TestResponse.builder();
            resBuilder.code(code);
            resBuilder.responseContent(resultBody);
            return resBuilder.build();
        } catch (Exception e) {
            log.error("error to post {}", url, e);
            return TestResponse.error(e.getMessage());
        }
    }

    private void generatePostJsonParam(List<ApiParam> params, HttpPost httpPost) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        JSONObject obj = new JSONObject();
        for (ApiParam param : params) {
            obj.put(param.getKey(), param.getValue());
        }
        StringEntity entity = new StringEntity(obj.toJSONString(), ContentType.APPLICATION_JSON);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
    }


    private void generatePostParam(List<ApiParam> params, HttpPost httpPost) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (ApiParam param : params) {
            list.add(new BasicNameValuePair(param.getKey(), param.getValue()));
        }
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //
        }
        httpPost.setEntity(urlEncodedFormEntity);
    }

    private String getResultBody(CloseableHttpResponse response) {
        String resultBody = "";
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                resultBody = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            log.error("parse result body failed", e);
        }
        return resultBody;
    }

    private Integer getResponseCode(CloseableHttpResponse response) {
        return Optional.ofNullable(response.getStatusLine())
                    .map(StatusLine::getStatusCode)
                    .orElse(null);
    }


}
