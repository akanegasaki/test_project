package com.akane.test.synchronize;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/21.
 */
public class EventStorage {
    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize=10;
        storage=new LinkedList<Date>();
    }
    public synchronized void set(){
        while(storage.size()==maxSize){
            try {
                //只有锁对象可以用wiat方法，因为非静态同步方法的锁就是对象本身，该对象继承
                //object,而object方法的wait方法是公有的，可以继承。
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storage.add(new Date());
            System.out.printf("set:%d",storage.size());
            super.notifyAll();
        }
    }
    public synchronized void get(){
        while(storage.size()==0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Get:");
        }
    }
}
