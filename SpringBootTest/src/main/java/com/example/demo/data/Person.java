package com.example.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Person implements Comparable<Person> {

    private Integer id;
    private String name;
    private Integer age;
    private List<Book> books;

    @Override
    public int compareTo(Person p) {
        return this.getAge() - p.getAge();
    }
}
