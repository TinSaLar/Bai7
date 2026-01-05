package com.example.bai7;

public class Student {
    private int id;
    private String name;
    private String email;
    private int avatar;

    public Student(int id, String name, String email, int avatar) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public Student(String name, String email, int avatar) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public int getAvatar() { return avatar; }
}
