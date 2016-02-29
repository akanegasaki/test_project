package com.akane.test.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/2/24.
 */

public class TestDeadLock2 implements Runnable{
    private boolean flag;
    private static ReentrantLock lock1=new ReentrantLock();
    private static ReentrantLock lock2=new ReentrantLock();

    public TestDeadLock2(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            if(flag){
                lock1.lock();
                System.out.println(flag + "线程获取了Lock1");
                TimeUnit.SECONDS.sleep(1);
                lock2.lock();
                System.out.println(flag+"线程获取了Lock2");
            }else{
                lock2.lock();
                System.out.println(flag + "线程获取了Lock2");
                TimeUnit.SECONDS.sleep(1);
                lock1.lock();
                System.out.println(flag+"线程获取了Lock1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1=new Thread(new TestDeadLock2(true));
        Thread thread2=new Thread(new TestDeadLock2(false));
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("主线程已结束");
    }
}