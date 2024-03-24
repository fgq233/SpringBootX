package com.example.demo.data;

import java.util.Arrays;
import java.util.List;

public class DataUtil {

    public static List<Person> initData() {
        List<Book> books1 = Arrays.asList(new Book("三国演义", "文学"), new Book("水浒传", "文学"), new Book("西游记", "文学"), new Book("红楼梦", "文学"));
        List<Book> books2 = Arrays.asList(new Book("哈姆雷特", "文学"), new Book("语文书", "教育"), new Book("相对论", "知识"));
        List<Book> books3 = Arrays.asList(new Book("物种起源", "科学"), new Book("数学书", "教育"), new Book("英语书", "教育"));
        return Arrays.asList(
                new Person(1, "张三", 24, books1),
                new Person(2, "李四", 18, books2),
                new Person(3, "王五", 32, books3),
                new Person(3, "王五", 32, books3)
        );
    }

}
