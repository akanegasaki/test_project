package com.akane.test.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/2/21.
 */
public class ThreadPoolDemo {
    public static class MyTast implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ":thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTast myTast = new MyTast();
//        ExecutorService executorService=Executors.newFixedThreadPool(5);
//        for (int i = 0; i <10 ; i++) {
//            executorService.submit(myTast);
//        }
       ExecutorService executorService=Executors.newCachedThreadPool();
        for (int i = 0; i <10 ; i++) {
            executorService.execute(myTast);
        }
    }
}
