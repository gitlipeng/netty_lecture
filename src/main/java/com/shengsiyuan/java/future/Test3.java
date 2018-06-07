package com.shengsiyuan.java.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test3 {
    public static void main(String[] args) throws Exception, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new Runnable() {
            public void run() {
                //异步执行
                System.out.println("Asynchronous task" + Thread.currentThread().getId());
            }
        });

        Future future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("submit Runnable");
            }
        });

        System.out.println(future.get());

        //submit Callable会阻塞等待
        Future<String> future2 = executorService.submit(()->{
            Thread.sleep(2000);
            System.out.println("executorService task" + Thread.currentThread().getId());
            return "executorService";
        });

        System.out.println(future2.get());




        executorService.shutdown();
    }
}
