package com.takumiCX.concurrency.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: takumiCX
 * @create: 2018-08-01
 **/
public class NoFairLockTest {


    public static void main(String[] args) {

        //创建费公平锁
        ReentrantLock lock = new ReentrantLock(false);

        ReentrantLock lock1 = new ReentrantLock(true);

        lock1.lock();

        try {

            //加锁
            lock.lock();


            lock1.lock();

            lock.lockInterruptibly();

            //模拟业务处理用时
            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            //释放锁
            lock.unlock();
        }

    }
}



