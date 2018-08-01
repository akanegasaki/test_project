package com.takumiCX.concurrency.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class CountDownLatchTest {


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(10);

        countDownLatch.await();

    }
}
