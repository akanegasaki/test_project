package com.takumiCX.concurrency.lock_free;

/**
 * @author: takumiCX
 * @create: 2018-08-06
 **/

import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 基于CAS操作实现的无锁的并发栈
 */
public class LockFreeConcurrentStack<E> extends Stack<E> {

    //指向栈顶的原子引用
    AtomicReference<Node<E>> top = new AtomicReference<>();

    //入栈
    public E push(E e) {

        Node<E> newNode = new Node<>(e);
        Node<E> oldNode;
        do {
            oldNode = top.get();
            newNode.next = oldNode;
        } while (!top.compareAndSet(oldNode, newNode));

        return e;
    }

    //出栈
    public E pop() {

        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));

        return oldHead.item;
    }

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        final LockFreeConcurrentStack<Integer> stack = new LockFreeConcurrentStack<>();

        CountDownLatch latch = new CountDownLatch(100000);


        for (int i = 0; i < 100000; i++) {

            ThreadDemo demo = new ThreadDemo();
            demo.isPush = i % 2 == 0;
            demo.stack = new Stack();
            demo.index = i;
            demo.latch = latch;
            new Thread(demo).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println("执行总时长为:" + (end - start));


    }


    private static class ThreadDemo implements Runnable {

        public CountDownLatch latch;

        public Stack stack;

        public boolean isPush;

        public int index;

        static Object mutetx=new Object();

        @Override
        public void run() {
            if (isPush) {
                synchronized (mutetx) {
                    stack.push(index);
                }
            } else {
                synchronized (mutetx) {
                    stack.pop();
                }
            }
            latch.countDown();
        }
    }

    /**
     * 私有静态内部类
     *
     * @param <E>
     */
    private static class Node<E> {

        public final E item;

        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }


}
