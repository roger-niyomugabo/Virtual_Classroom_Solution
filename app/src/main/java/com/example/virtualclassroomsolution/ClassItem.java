package com.example.virtualclassroomsolution;

public class ClassItem {
    private String className;
    private String videoLink;

    public ClassItem(String className, String videoLink) {
        this.className = className;
        this.videoLink = videoLink;
    }

    public String getClassName() {
        return className;
    }

    public String getVideoLink() {
        return videoLink;
    }
}
