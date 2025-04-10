package com.example.entity;

public class Teacher {
    private String name;
    private String phone;
    private String subject;


    public Teacher() {}

    public Teacher(String name, String phone,String subject) {
        this.name = name;
        this.phone = phone;
        this.subject = subject;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
}