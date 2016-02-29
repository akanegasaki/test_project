package com.akane.test.futrue;

import java.util.concurrent.*;

/**
 * Created by Administrator on 2016/2/29.
 */
public class CallableTask implements Callable<String>{
    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(10);
        return "好好学习天天向上！";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor=Executors.newFixedThreadPool(1);
        Future<String> stringFuture = executor.submit(new CallableTask());
        while (true){
            System.out.println(stringFuture.isDone());
        }
    }
}
