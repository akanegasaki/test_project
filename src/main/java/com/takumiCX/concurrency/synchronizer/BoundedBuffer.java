package com.takumiCX.concurrency.synchronizer;

/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class BoundedBuffer<E> extends BaseBoundedBuffer<E> {

    public BoundedBuffer(int capacity) {
        super(capacity);
    }

    /**
     * 阻塞并直到 not-full
     * @param e
     * @throws InterruptedException
     */
    public synchronized void put(E e) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(e);
        notifyAll();
    }


    /**
     *
     * 阻塞并直到not-empty
     * @return
     * @throws InterruptedException
     */
    public synchronized E take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }

        E e = doTake();
        notifyAll();
        return e;
    }

}
