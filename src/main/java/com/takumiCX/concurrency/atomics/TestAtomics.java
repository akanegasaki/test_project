package com.takumiCX.concurrency.atomics;

/**
 * Created by Administrator on 2016/2/28.
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试使用原子类是否会产生多线程问题
 *
 */
public class TestAtomics implements Runnable{
    private static AtomicInteger count=new AtomicInteger();


    @Override
    public void run() {
        for(int i=0;i<1000000;i++){
            count.addAndGet(1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestAtomics ta=new TestAtomics();
        Thread th1=new Thread(ta);
        Thread th2=new Thread(ta);
        th1.start();
        th2.start();
        th1.join();
        th2.join();
        System.out.println(count.get());
    }
}
