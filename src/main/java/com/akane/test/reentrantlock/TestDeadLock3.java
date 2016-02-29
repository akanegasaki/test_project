package com.akane.test.reentrantlock;

/**
 * Created by Administrator on 2016/2/24.
 */

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock重入锁实现死锁,方法和使用同步代码块差不多
 */
public class TestDeadLock3 implements Runnable {
    private boolean      flag;
    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();

    public TestDeadLock3(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        try {
            if (flag) {
                //可中断地加锁
                lock1.lockInterruptibly();
                System.out.println(flag + "线程获取了lock1");
                TimeUnit.SECONDS.sleep(1);
                lock2.lockInterruptibly();
                System.out.println(flag + "线程获取了lock2");
            } else {
                lock2.lockInterruptibly();
                System.out.println(flag + "线程获取lock2");
                TimeUnit.SECONDS.sleep(1);
                lock1.lockInterruptibly();
                System.out.println(flag + "线程获取了lock1");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
                System.out.println(flag + "线程释放lock1锁");
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
                System.out.println(flag + "线程释放lock2锁");
            }
            System.out.println(flag + "线程已退出");
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new TestDeadLock3(true));
        Thread thread2 = new Thread(new TestDeadLock3(false));
        thread1.start();
        thread2.start();
        //主线程休眠5秒
        TimeUnit.SECONDS.sleep(5);
        thread1.interrupt();
    }
}
