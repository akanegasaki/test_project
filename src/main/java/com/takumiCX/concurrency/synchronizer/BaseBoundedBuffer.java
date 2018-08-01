package com.takumiCX.concurrency.synchronizer;

/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class BaseBoundedBuffer<E> {

    private final E[] buf;
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity) {
        this.buf = (E[]) new Object[capacity];
    }

    protected synchronized final void doPut(E e) {
        buf[tail] = e;
        if (++tail == buf.length) {
            tail = 0;
        }
        count++;
    }


    protected synchronized final E doTake() {
        E e = buf[head];
        buf[head] = null;
        if (++head == buf.length) {
            head = 0;
        }
        count--;
        return e;
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }


}
