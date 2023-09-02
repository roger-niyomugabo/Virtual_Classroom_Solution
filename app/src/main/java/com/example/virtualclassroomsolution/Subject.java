package com.example.virtualclassroomsolution;

public class Subject {
    private String title;
    private String content;
    private  String id;

    private  String professor;

    public Subject(String id, String title, String content, String professor) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.professor = professor;

    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public  String getId() {
        return id;
    }

    public  String getProfessor() {
        return professor;
    }

}

