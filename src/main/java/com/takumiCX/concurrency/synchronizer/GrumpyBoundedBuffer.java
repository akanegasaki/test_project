package com.takumiCX.concurrency.synchronizer;


/**
 * @author: takumiCX
 * @create: 2018-07-31
 **/
public class GrumpyBoundedBuffer<E> extends BaseBoundedBuffer<E> {

    public GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(E e) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(e);
    }


    public synchronized E take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new BufferEmptyException();
        }
        return doTake();
    }

}
