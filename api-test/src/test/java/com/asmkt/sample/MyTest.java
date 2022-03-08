package com.asmkt.sample;

import com.asmkt.sample.domain.TestResponse;
import com.asmkt.sample.domain.TestResult;
import com.asmkt.sample.utils.AESUtils;
import com.asmkt.sample.utils.CsvUtils;
import com.asmkt.sample.utils.MD5Utils;
import com.google.common.base.Supplier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.security.sasl.SaslServer;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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

    @Test
    public void testCsv() {
        CsvUtils.readCsvFile("D:\\asmkt\\mywork\\data.csv");
        String[] data = {"123", "456"};
        String[] data2 = {"789", "101112"};
        List<String[]> list = new ArrayList<>();
        list.add(data);
        list.add(data2);
        CsvUtils.writeCsvFile("D:\\asmkt\\mywork\\data1.csv", null ,list);
    }

    @Test
    public void testString2() {
        String a = "123";
        System.out.println(a == "123");
        String b = new String("456");
        System.out.println("456" == b);
        String c = new String("567");
        String d = new String("567");
        System.out.println(c == d);
    }

    @Test
    public void maoPao() {
        int[] arr = {1, 3, 2, 5, 4, -1};
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }

    /**
     * 严蔚敏版《数据结构》中对选择排序的基本思想描述为：
     * 每一趟在n-i+1(i=1,2,...,n-1)个记录中选取关键字最小的记录作为有序序列中第i个记录。
     * 具体来说，假设长度为n的数组arr，要按照从小到大排序，那么先从n个数字中找到最小值min1，如果最小值min1的位置不在数组的最左端(也就是min1不等于arr[0])，
     * 则将最小值min1和arr[0]交换，接着在剩下的n-1个数字中找到最小值min2，如果最小值min2不等于arr[1]，则交换这两个数字，依次类推，直到数组arr有序排列。算法的时间复杂度为O(n^2)。
     * ————————————————
     * 版权声明：本文为CSDN博主「ispurs」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/liang_gu/article/details/80627548
     */
    @Test
    public void xuanze() {
        int[] arr = {1, 3, 2, 5, 4, -1};
        int len = arr.length;
        int index,temp;
        for (int i = 0; i < len; i++) {
            index = i; //min index
            for (int j = i + 1; j < len; j ++) {
                if (arr[j] < arr[index]) {
                    index = j; //找到最小的那个索引
                }
                if (index != i) {
                    temp = arr[i];
                    arr[i] = arr[index];
                    arr[index] = temp;
                }
            }
        }
        for (int a : arr) {
            System.out.println(a);
        }
    }

    Lock lock = new ReentrantLock(); //可重入锁
    public void lock() {
        lock.lock();
        try {
            System.out.println("get the lock");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("release the lock");
        }
    }

    @Test
    public void testLock() {
       /* Runnable run = this::lock;
        Thread t = new Thread(run);
        t.start();
        run.run();
        Runnable runnable = () -> {
            System.out.println("");
        };*/
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(this::lock);
            t.start();
        }
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHashMap() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("a", "v");
        Map<String, String> hashMap = new HashMap<>();
    }

    @Test
    public void testYouYi() {
        int a = 1 << 4;
        // 0001
        System.out.println(a);
    }

    @Test
    public void testSql() {
        String sql = "select a.name,b.no from A a left join B on a.id = b.id";
        String sql2 = "select ";
        int test = 1 ^ 2;
        System.out.println(test);
    }
}
