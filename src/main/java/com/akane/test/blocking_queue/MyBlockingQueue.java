package com.akane.test.blocking_queue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MyBlockingQueue<E>{
    private ReentrantLock lock     = new ReentrantLock();
    private Condition     notEmpty = lock.newCondition();
    private Condition     notFull  = lock.newCondition();
    private List<E>       list     = new LinkedList<E>();
    private int           capacity;

    public MyBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    //get
    public E get() {
        lock.lock();
        try {
            if (list.isEmpty()) {
                notEmpty.await();
            } else {
                notFull.signal();
                return list.get(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public boolean set(E e) {
        lock.lock();
        try {
            if (list.size() >= capacity) {
                notFull.await();
            } else {
                list.add(e);
                notEmpty.signal();
                return true;
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public static void main(String[] args) {
        MyBlockingQueue<Integer> queue=new MyBlockingQueue(20);
        for(int i=0;i<100;i++){
            queue.set(i);
        }
    }
}

class producer implements Runnable{
    private MyBlockingQueue queue;
    private int threadNum;

    public producer(MyBlockingQueue queue, int threadNum) {
        this.queue = queue;
        this.threadNum=threadNum;
    }

    @Override
    public void run() {
       for(int i=0;i<100;i++){
           queue.set("set"+i);
           if(i%10==0){
               Thread.yield();
           }
       }
    }
}
class consumer implements Runnable{

    @Override
    public void run() {

    }
}