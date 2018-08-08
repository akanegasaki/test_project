package com.takumiCX.concurrency.lock_free;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: takumiCX
 * @create: 2018-08-07
 **/
public class LockFreeConcurrentQueue<E> {


    private AtomicReference<Node<E>> head = new AtomicReference<>(null);

    private AtomicReference<Node<E>> tail = new AtomicReference<>(null);

    public LockFreeConcurrentQueue() {

    }


    public boolean enqueue(E e) {

        if (e == null) throw new NullPointerException();
        Node<E> newNode = new Node<>(e);
        for (; ; ) {
            Node<E> taild = tail.get();
            if (taild == null) {
                if (head.compareAndSet(null, newNode)) {
                    tail.set(newNode);
                    return true;
                }

            } else {

                newNode.pre = taild;
                if (tail.compareAndSet(taild, newNode)) {
                    taild.next = newNode;
                    return true;
                }

            }
        }

    }

    public E dequeue() {

        for (; ; ) {
            Node<E> tailed = tail.get();
            Node<E> headed = head.get();

            if (tailed == null) {
                return null;
            } else if (headed == tailed) {
                if (tail.compareAndSet(tailed, tailed.next)) {
                    head.compareAndSet(headed, null);
                    return tailed.item;
                }

            } else {

                Node preNode = tailed.pre;

                if (tail.compareAndSet(tailed, preNode)) {
                    preNode.next = null;  //这一步不能少
//                    tailed.pre=null;
                    return tailed.item;
                }

            }
        }
    }


    public static void main(String[] args) throws InterruptedException {

        List<String> list=Collections.synchronizedList(new ArrayList<>());

        LockFreeConcurrentQueue<String> queue = new LockFreeConcurrentQueue<>();

        CountDownLatch latch = new CountDownLatch(100);

        for(int i=0;i<100;i++){
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j=0;j<100;j++){
                        if(finalI %2==0){
                            queue.enqueue(finalI+"-"+j);
                        }else{
                            String s = queue.dequeue();
                            list.add(s);
                            System.out.println(s);
                        }
                    }
                    latch.countDown();
                }
            }).start();
        }
        latch.await();

        System.out.println(list.size());

//        long sum = 0L;
//        for (int i = 0; i < 100; i++) {
//            sum += testMyConcurrentQueue();
////            sum += testJDK();//178.49
//        }

//        System.out.println(sum * 1.0 / 100);
//        testJDK();
//        System.out.println(queue.list.size());

//        testMyConcurrentQueue();

    }

    private static long testJDK() throws InterruptedException {

        long start = System.currentTimeMillis();

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int j = 0; j < 100; j++) {
                        queue.add(finalI + "-" + j);
                    }
                    latch.countDown();

                }
            }).start();
        }
        latch.await();
        long end = System.currentTimeMillis();
        return (end - start);

    }

    private static long testMyConcurrentQueue() throws InterruptedException {

        long start = System.currentTimeMillis();


        LockFreeConcurrentQueue<String> queue = new LockFreeConcurrentQueue<>();

        CountDownLatch latch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int j = 0; j < 100; j++) {
                        queue.enqueue(finalI + "-" + j);
                    }
                    latch.countDown();

                }
            }).start();
        }
        latch.await();

        Node<String> head = queue.head.get();
        while (head.next != null) {
            System.out.println(head.next);
            head = head.next;
        }
        long end = System.currentTimeMillis();
        return end - start;
    }


    private static class Node<E> {

        public volatile Node pre;

        public volatile Node next;

        public volatile E item;

        public Node(E item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }
}
