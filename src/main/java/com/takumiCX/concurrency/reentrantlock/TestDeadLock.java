package com.takumiCX.concurrency.reentrantlock;

/**
 * Created by Administrator on 2016/2/24.
 */

import java.util.concurrent.TimeUnit;
public class TestDeadLock implements Runnable {
    //开关
    private boolean       flag;
    //锁1
    private static Object lock1 = new Object();
    //锁2
    private static Object lock2 = new Object();

    public TestDeadLock(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag) {
            synchronized (lock1) {
                System.out.println(flag + "线程拿到了lock1");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                    System.out.println(flag + "线程拿到了lock2");
                }
            }
        } else {
            synchronized (lock2) {
                System.out.println(flag + "线程拿到了lock2");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock1) {
                    System.out.println(flag + "线程拿到了lock1");
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new TestDeadLock(true));
        Thread thread2 = new Thread(new TestDeadLock(false));
        thread1.start();
        thread2.start();
    }
}