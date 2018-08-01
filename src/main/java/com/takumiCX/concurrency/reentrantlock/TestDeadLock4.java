package com.takumiCX.concurrency.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by akanegasakisora on 2016/2/24.
 */
public class TestDeadLock4 implements Runnable{
    private boolean      flag;
    static ReentrantLock lock1 = new ReentrantLock();
    static ReentrantLock lock2 = new ReentrantLock();
    //统计发生死锁的次数
    private static int count;

    public TestDeadLock4(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if(flag){
            while (true) {
                if(lock1.tryLock()){
                    System.out.println(flag+"线程获得了lock1");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                        try {
                            if(lock2.tryLock()){
                                System.out.println(flag+"获得了lock2");
                            }
                        } finally {
                            //同时获得Lock1和lock2，没有发生死锁，任务完成，退出循环
                            if(lock1.isHeldByCurrentThread()&&lock2.isHeldByCurrentThread()){
                                System.out.println(flag+"线程执行完毕"+"---------------------");
                                lock1.unlock();
                                lock2.unlock();
                                break;
                            }else{
                                //说明发生了死锁，只需要释放lock1
                                //统计变量也要注意多线程问题
                                synchronized (TestDeadLock4.class) {
                                    count++;
                                    System.out.println("发生了"+count+"次死锁");
                                }
                                lock1.unlock();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            while (true) {
                if(lock2.tryLock()){
                    System.out.println(flag+"线程获得了lock2");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                        try {
                            if(lock1.tryLock()){
                                System.out.println(flag+"线程获得了lock1");
                            }
                        } finally {
                            if(lock1.isHeldByCurrentThread()&&lock2.isHeldByCurrentThread()){
                                System.out.println(flag+"线程执行完毕"+"---------------------");
                                lock1.unlock();
                                lock2.unlock();
                                break;
                            }else{
                                synchronized (TestDeadLock4.class) {
                                    count++;
                                    System.out.println("发生了"+count+"次死锁");
                                }
                                lock2.unlock();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new TestDeadLock4(true));
        Thread thread2 = new Thread(new TestDeadLock4(false));
        thread1.start();
        thread2.start();
    }
}
