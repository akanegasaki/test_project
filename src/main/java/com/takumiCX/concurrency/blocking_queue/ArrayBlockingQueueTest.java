package com.takumiCX.concurrency.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: takumiCX
 * @create: 2018-08-06
 **/
public class ArrayBlockingQueueTest {


    public static void main(String[] args) throws InterruptedException {

       BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1000);

       queue.take();



    }
}
