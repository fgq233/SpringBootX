package com.example.demo.java8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InterfaceTest {

    public static void main(String[] args) {
        testConsumer(s -> {
            System.out.println(s);
        });
        Integer function = testFunction(s -> Integer.valueOf(s));
        boolean predicate = testPredicate(num -> num > 0, 0);
        String supplier = testSupplier(() -> "666");

        System.out.println(function);
        System.out.println(predicate);
        System.out.println(supplier);
    }


    public static void testConsumer(Consumer<String> consumer) {
        consumer.accept("fgq");
    }

    public static boolean testPredicate(Predicate<Integer> predicate, Integer num) {
        return predicate.test(num);
    }

    public static Integer testFunction(Function<String, Integer> function) {
        return function.apply("666");
    }

    public static String testSupplier(Supplier<String> supplier) {
        return supplier.get();
    }

}
