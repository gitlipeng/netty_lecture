package com.shengsiyuan.java.future;

import com.sun.javafx.runtime.async.BackgroundExecutor;
import com.sun.org.apache.xpath.internal.functions.FuncTrue;

import java.util.concurrent.*;

/**
 *  * interface ArchiveSearcher { String search(String target); }
 * class App {
 *   ExecutorService executor = ...
 *   ArchiveSearcher searcher = ...
 *   void showSearch(final String target)
 *       throws InterruptedException {
 *     Future<String> future
 *       = executor.submit(new Callable<String>() {
 *         public String call() {
 *             return searcher.search(target);
 *         }});
 *     displayOtherThings(); // do other things while searching
 *     try {
 *       displayText(future.get()); // use future
 *     } catch (ExecutionException ex) { cleanup(); return; }
 *   }
 * }}</pre>
 */

public class FutureTest1 {
    ExecutorService executor = Executors.newCachedThreadPool();
    ArchiveSearcher searcher = new ArchiveSearcher();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTest1 test1 = new FutureTest1();
        test1.showSearch("lipeng");

        test1.showSearch("zhangli");
    }

    public void showSearch(final String target) throws InterruptedException, ExecutionException {
        Future<String> future = executor.submit(()->{
                System.out.println("doing search" + Thread.currentThread().getId());
                return searcher.search(target);
        });

        System.out.println("do anthone things" + Thread.currentThread().getId());

        System.out.println(future.get());

        System.out.println("over anthone things" + Thread.currentThread().getId());

//        FutureTask<String> futureTask = new FutureTask<>(()->searcher.search(target) );
//        executor.execute(futureTask);
//
//        System.out.println(futureTask.get());
    }
}

class  ArchiveSearcher {
    public String search(String target){
        try {
            Thread.sleep(5000);
            return target + "serach result";
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "error";
    }
}

























