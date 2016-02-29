package com.akane.test.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/2/21.
 */
//使用重入锁，使两个线程轮流输出1-100.
public class TestReentrantLock implements Runnable{
    private static int i=0;
    private ReentrantLock lock=new ReentrantLock();
    @Override
    public void run() {
        if(i<100){
            System.out.println(Thread.currentThread().getName()+i);
        }

    }
}
