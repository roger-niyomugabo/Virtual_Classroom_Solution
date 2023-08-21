package com.example.virtualclassroomsolution;

public class Subject {
    private String title;
    private String content;
    private  Integer id;

    public Subject(Integer id, String title, String content) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public  Integer getId() {
        return id;
    }

}

