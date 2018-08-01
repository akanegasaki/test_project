package com.takumiCX.concurrency.synchronizer;

import java.util.concurrent.TimeUnit;

/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class SleepyBoundedBuffer<E> extends BaseBoundedBuffer<E> {

    public SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(E e) throws InterruptedException {
        while (true){
            synchronized (this){
                if(!isFull()){
                    doPut(e);
                    return;
                }
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    public E take() throws InterruptedException {
        while (true){
            synchronized (this){
                if(!isEmpty()){
                    return doTake();
                }
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

}
