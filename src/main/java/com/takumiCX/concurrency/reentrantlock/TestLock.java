package com.takumiCX.concurrency.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: takumiCX
 * @create: 2018-08-01
 **/
public class TestLock {

    static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 2; i++) {

            Thread thread = new Thread(new ThreadDemo(i));

            thread.setName(i+"");

            thread.start();

            TimeUnit.MILLISECONDS.sleep(10);

            Thread.interrupted();

        }

        for(int i=0;i<2;i++){
            lock.unlock();
        }


    }


    static class ThreadDemo implements Runnable {

        int i;

        public ThreadDemo(int i) {
            this.i = i;
        }

        @Override
        public void run() {


            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
