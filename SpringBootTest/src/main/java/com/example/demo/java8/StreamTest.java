package com.example.demo.java8;

import com.example.demo.data.Book;
import com.example.demo.data.DataUtil;
import com.example.demo.data.Person;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTest {


    public static void main(String[] args) {
        List<Person> list = DataUtil.initData();

        String ids = list.stream().map(Person::getId).map(String::valueOf).collect(Collectors.joining(","));


    }

}
