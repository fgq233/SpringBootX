package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Comparable<Person> {

    private int id;
    private String name;
    private int age;
    private List<Book> books;

    @Override
    public int compareTo(Person p) {
        return this.getAge() - p.getAge();
    }
}
