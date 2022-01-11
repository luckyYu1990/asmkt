package com.asmkt.sample;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.utils.AESUtils;
import com.asmkt.sample.utils.MD5Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTest {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void testString() {
        System.out.println(RandomStringUtils.randomAlphanumeric(20));
    }

    @Test
    public void testAsync() {
        final TestResult result = new TestResult();
        List<Callable<TestResponse>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i ++ ) {
            tasks.add(() -> {
                System.out.println("===execute===" + Thread.currentThread().getName());
                Thread.sleep(100000);
                TestResponse response = new TestResponse();
                response.setMessage(Thread.currentThread().getName());
                System.out.println("===finish===" + Thread.currentThread().getName());
                return response;
            });
        }
        try {
            List<Future<TestResponse>> futures = executorService.invokeAll(tasks);
            for (Future<TestResponse> future : futures) {
                System.out.println(future.get().getMessage());
            }
        } catch (Exception e) {
            //
        }


    }

    @Test
    public void test2() {
        int a = 3;
        int b = 10;
        double c = (double) b / a;
        System.out.println(c);

        BigDecimal bd = new BigDecimal(c);
        double res = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(res);
    }

    @Test
    public void test3() {
        Instant now = Instant.now();
        System.out.println(now);
      /*  Duration duration = Duration.ofMinutes(1);
        System.out.println(duration);*/
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant now1 = Instant.now();
        System.out.println(Duration.between(now, now1).getSeconds());
        while (true) {
            Instant now2 = Instant.now();
            System.out.println("$$$$$" + now2);
            long seconds = Duration.between(now1, now2).getSeconds();
            if (seconds > 10) {
                break;
            }
        }
        System.out.println(Instant.now());

    }

    @Test
    public void testTimestamp() {
        Long l = System.currentTimeMillis();
        System.out.println(l);
        System.out.println(l.toString().length());
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getYear());
        System.out.println(now.getMonth().getValue());
        System.out.println(now.getDayOfMonth());
        //System.out.println(StringBuildernow.getHour() + now.getMinute());
    }

    @Test
    public void testAes() throws UnsupportedEncodingException {
        String key = "b22c99226b544386a96743d2827c1705";
        String decrypt = AESUtils.decrypt("igmspsF5ebTi+jghxa7D2MmEBXH1aZvGoPG8QnRfM8Tym79py4NT7b8cZKQd+JzohgJQQrGj5QOq9U28IMOzsRPJjDESU5rs0K2xU0XdVUDs82RMB6XelhFQKpbNdrHW/2bmAoocdajqOm9itAo6BEVekgV6p44R8FposevLFYQQ6xh6jsBVsLmEqncdxRFs1FoiQimpJXUPv0k/hYJdOAwlTUtbq5e6wYmh7AcYV9WXhQIk3GBOmwoxjv12I1NpjrBtE2wnDYPEUxPPEZKEkwqDmu5YMzD2s5+LS3J9QM26emliUGWyG9hpHHuuB9kN+D+nHtN5Fy391AoQH3ehozhJTRUQo8KrwF7r+S9KXSumwZZ07tbpcYED2rF/V1T/4fOlgljQoObW3vGTud2d5A==", key);
        System.out.println(decrypt);

        String urlData = "igmspsF5ebTi%2Bjghxa7D2MmEBXH1aZvGoPG8QnRfM8Tym79py4NT7b8cZKQd%2BJzoNm2wqrUZqqimghYQrutKK9VmqeI1Jkgwyx951oqi4oBgyvUSigI%2FaziDgWw%2B3XPnMQZvNV3DcpE%2B0dhOwbXmclGMP%2FFz1SXd%2BMbkxr3dxLCnVbAmHWTFnnLJKQco4%2FoAMi%2FAidsnR3zqrOEbOsm2gdmo1TFv058swdmrOl4yQ7qND%2BAOzJhO7S3eEKADgAC7zcHcXRmajKRb6%2BHyRWM131h9iskf3J5%2BkAIpyeX4Z7WBU1HUXebYE%2FBt5%2BXQsWRk5mXKjh%2FEulprmL5tlYAJb0Z4SdgbK1sR2eOMsIdpB2M8nf6vQAnVDlGlw3XlGTSPGyFTHIVGvfruJfOuuS6H7VqjfDGrfpSUPZq8Veibf%2BLviXbLcEWPD2DvyhWpIPwg";
        String decode = URLDecoder.decode(urlData, "utf-8");
        String dec = AESUtils.decrypt(decode, key);
        System.out.println(dec);
    }

    @Test
    public void testBytes() throws UnsupportedEncodingException {
        byte[] bytes = "0000000000000000".getBytes("utf-8");
        byte[] b2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        System.out.println(bytes[0]);
        System.out.println(new String(bytes));
        System.out.println("=====" + new String(b2) + "^^^^^^^");
    }

    @Test
    public void testMd5() {
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String md5Str= "timestamp=" + timestamp + "&openid=&mobile=13800138000&key=";
        String sign = MD5Utils.getMD5Str(md5Str).toUpperCase();
        System.out.println(sign);
        //String md5Str = "appId=111950457551001&clientid=624509192800130&openid=&mobile=13621632670&themeid=1068&timestamp=1624260387849&sign=838E734568302CB2F7D87A9C7A491DC1";
    }

    @Test
    public void testStream() {
        List<String> collect = Stream.of("1", "2").collect(Collectors.toList());
        Stream.of("1", "2", "3", "4");
    }
}
