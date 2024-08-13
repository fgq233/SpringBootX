package com.example.demo.java8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureTest {


    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        test1();
//        test2();
//        test3();
//        test4();
//        test5();
//        test6();
    }

    private static void test1() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Integer> future1 = init(1);
        CompletableFuture<Void> future2 = init2();
        System.out.println(future1.join());
        System.out.println(future2.join());

        System.out.println(init(6).get(800, TimeUnit.MILLISECONDS));

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = init(1);

        CompletableFuture<Void> thenRun = future.thenRun(() -> System.out.println("thenRun"));
        CompletableFuture<Void> thenAccept = future.thenAccept(integer -> System.out.println("thenAccept:" + integer));
        CompletableFuture<Integer> thenApply = future.thenApply(integer -> {
            System.out.println("thenApply");
            return integer + 1;
        });
        System.out.println(thenApply.get());
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void test3() throws InterruptedException, ExecutionException {
        CompletableFuture<String> whenComplete = init3().whenComplete((s, throwable) -> {
            System.out.println(s);
            System.out.println(throwable.getMessage());
        });

        CompletableFuture<String> handle = init3().handle((s, throwable) -> {
            System.out.println(s);
            System.out.println(throwable.getMessage());
            return "handle";
        });

        CompletableFuture<String> exceptionally = init3().exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return "exceptionally";
        });
        // 调用get()、join()，主线程会抛出异常
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void test4() throws InterruptedException {
        CompletableFuture.allOf(init(1), init(2)).thenRun(() -> System.out.println("all执行完毕"));
        CompletableFuture.anyOf(init(1), init2()).thenRun(() -> System.out.println("any执行完毕"));
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void test5() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> thenCombine = init(1).thenCombine(init(2), (num1, num2) -> {
            System.out.println(num1);
            System.out.println(num2);
            return num1 + num2;
        });
        System.out.println(thenCombine.get());

        CompletableFuture<Void> thenAcceptBoth = init(10).thenAcceptBoth(init(20), (num1, num2) -> {
            System.out.println(num1);
            System.out.println(num2);
        });

        CompletableFuture<Void> runAfterBoth = init(100).runAfterBoth(init(200), new Runnable() {
            @Override
            public void run() {
                System.out.println("runAfterBoth");
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void test6() throws InterruptedException, ExecutionException {
        CompletableFuture<String> applyToEither = init(1).applyToEither(init(2), num -> "先完成的是：" + num);
        System.out.println(applyToEither.get());

        CompletableFuture<Void> acceptEither = init(10).acceptEither(init(20), num -> System.out.println("先完成的是：" + num));

        CompletableFuture<Void> runAfterEither = init(100).runAfterEither(init(200), () -> System.out.println("有一个已经完成了"));

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 有返回值异步任务
     */
    private static CompletableFuture<Integer> init(int num) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步任务(有返回值)");
            return num;
        });
    }

    /**
     * 无返回值异步任务
     */
    private static CompletableFuture<Void> init2() {
        return CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步任务(无返回值)");
        });
    }


    /**
     * 有异常异步任务
     */
    private static CompletableFuture<String> init3() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("有异常异步任务");
            int i = 1 / 0;
            return "XXX";
        });
    }

}
