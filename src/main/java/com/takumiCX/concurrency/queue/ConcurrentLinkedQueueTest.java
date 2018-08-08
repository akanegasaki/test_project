package com.takumiCX.concurrency.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author: takumiCX
 * @create: 2018-08-02
 **/
public class ConcurrentLinkedQueueTest {


    public static void main(String[] args) throws InterruptedException {


        ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();

        Object o = new Object();


        queue.add(o);

        /**
         * Inserts the specified element at the tail of this queue.
         * As the queue is unbounded, this method will never return {@code false}.
         *
         * @return {@code true} (as specified by {@link Queue#offer})
         * @throws NullPointerException if the specified element is null
         */

        queue.offer(o);


    }
}
