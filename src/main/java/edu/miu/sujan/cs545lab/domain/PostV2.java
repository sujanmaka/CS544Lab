package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

@Data
public class PostV2 {
    private long id;
    private String title;
    private String content;
    private String author;
    private static int count;

    public PostV2() {
    }

    public PostV2(String title, String content, String author) {
        this.id = count++;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
