package com.takumiCX.concurrency.thread;

/**
 * Created by chengxu on 2016/2/20.
 */
public class JoinTest extends Thread {
    private static int i = 0;

    @Override
    public void run() {
        while (i < 100000) {
           i++;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread th = new JoinTest();
        th.start();
        th.join();
        System.out.println(i);
    }
}
