package com.demo.firebasehw.pojo;

public class User {
    private String name;
    private String lastname;
    private int age;
    private String sex;

    public User() {
    }

    public User(String name, String lastname, int age, String sex) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}