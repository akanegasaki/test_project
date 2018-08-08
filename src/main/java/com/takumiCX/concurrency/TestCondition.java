package com.takumiCX.concurrency;


/**
 * @author: takumiCX
 * @create: 2018-08-01
 **/
public class TestCondition {


    public static void main(String[] args) throws InterruptedException {

//        ReentrantLock lock = new ReentrantLock();
//
//        Condition condition = lock.newCondition();
//
//        condition.await();
//
//        condition.signal();

        int t=1;

        System.out.println(t!=(t=7));

        System.out.println(t);


    }
}
