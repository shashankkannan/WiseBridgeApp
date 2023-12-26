package com.example.wisebridge;

public class Post {
    private String Name;
    private String id;

    public Post(String name, String id) {
        this.Name = name;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return id;
    }
}
