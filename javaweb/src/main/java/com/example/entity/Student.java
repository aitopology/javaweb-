package com.example.entity;

public class Student {
    private String no;
    private String name;
    private int age;

    public Student() {}

    public Student(String no, String name, int age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }
public void setNo(String no) {
        this.no = no;
}
public void setName(String name) {
        this.name = name;
}

    public int getAge() {
        return age;
    }

    public String getNo() {
    return no;
    }
    public String getName() {
        return name;
    }
}
