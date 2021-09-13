package com.asmkt.sample.service;

import com.asmkt.sample.domain.TestResponse;

public interface GeneralService {

    TestResponse get(String url, String param);

    TestResponse postForm(String url, String params);
}
