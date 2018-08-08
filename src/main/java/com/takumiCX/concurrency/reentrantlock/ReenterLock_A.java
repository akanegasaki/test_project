package com.takumiCX.concurrency.reentrantlock;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/2/21.
 */

/**
 *
 */
public class ReenterLock_A implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;
    @Override
    public void run() {
        for(int j=0;j<10000000;j++){
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();//finally代码块里的不管是否异常都会执行
            }
        }

        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        map.put("1","2");
        map.get("2");
    }
    public static void main(String[] args) throws InterruptedException {
        Thread th1=new Thread(new ReenterLock_A());
        Thread th2=new Thread(new ReenterLock_A());
        th1.start();
        th2.start();
        th1.join();//join的目的是为了在打印i之前两个线程的累加操作都能做完
        th2.join();
        System.out.println(i);
    }
}
