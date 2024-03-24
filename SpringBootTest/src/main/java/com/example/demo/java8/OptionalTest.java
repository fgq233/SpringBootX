package com.example.demo.java8;

import java.util.Optional;

public class OptionalTest {


    public static void main(String[] args) {
        Optional<Integer> o1 = Optional.ofNullable(1);
        Optional<Integer> o2 = Optional.ofNullable(null);


        Optional<Optional<String>> ss1 = o1.map((a) -> Optional.of("key" + a));
        Optional<String> s2 = o1.flatMap((a) -> Optional.of("key" + a));
    }

}
