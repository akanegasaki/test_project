package com.akane.test.thread;

/**
 * Created by Administrator on 2016/2/20.
 */
/*
 * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行
 * chengxu 2016年2月20日
 */
public class OrderThread extends Thread {
    private static int i   = 3;
    private Object     obj = new Object();

    @Override
    public void run() {
        while (i > 1) {
            OrderThread th = new OrderThread();
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("t" + i--);
    }

    public static void main(String[] args) {

        new OrderThread().start();
    }
}