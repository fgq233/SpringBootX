package com.example.demo.java8;

import java.util.Optional;

public class OptionalTest {


    public static void main(String[] args) {
        Optional<Integer> o1 = Optional.ofNullable(1);
        Optional<String> o2 = Optional.ofNullable(null);

        System.out.println(o2.orElse("11"));

    }

}
