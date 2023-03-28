package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CountDownLatchExample1 {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - startTime);

        long s2 = System.currentTimeMillis();
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            test(threadNum);
        }
        System.out.println(System.currentTimeMillis() - s2);
        log.info("finish");
        exec.shutdown();
    }

    private synchronized static void test(int threadNum) throws Exception {
        Thread.sleep(10);
//        log.info("{}", threadNum);
        Thread.sleep(10);
    }
}
