package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ryan
 * @version Id: RCountDownLatchExample, v 0.1 2023/3/28 5:25 PM ryan Exp $
 */
@Slf4j
public class RCountDownLatchExample {

    private final static int threadCount = 200;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(5);
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum,Thread.currentThread().getName());
                } catch (Exception e) {
                    log.error("exception", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        log.info("finish");
        exec.shutdown();
    }

    private synchronized static void test(int threadNum,String threadName) throws Exception {
        Thread.sleep(10);
        log.info("{}-{}", threadNum,threadName);
        Thread.sleep(10);
    }
}
