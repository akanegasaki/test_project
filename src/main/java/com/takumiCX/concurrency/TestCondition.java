package com.takumiCX.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: takumiCX
 * @create: 2018-08-01
 **/
public class TestCondition {


    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        condition.await();

        condition.signal();

    }
}
