package com.shengsiyuan.java.future;

import java.util.concurrent.Executor;

public class Test2 {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getId());
        Executor executor = new MyExecutor();
        new Thread(()->{
            executor.execute(()->System.out.println("aaa" + Thread.currentThread().getId()));
        }).start();

    }
}
class MyExecutor implements Executor{

    @Override
    public void execute(Runnable command) {
        System.out.println(Thread.currentThread().getId());
        new Thread(command).start();
//        command.run();
    }
}