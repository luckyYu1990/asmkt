package com.asmkt.sample;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
}
