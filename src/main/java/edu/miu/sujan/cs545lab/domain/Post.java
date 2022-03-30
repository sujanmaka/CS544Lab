package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

@Data
public class Post {
    private long id;
    private String title;
    private String content;
    private String author;
    private static int count;

    public Post() {
    }

    public Post(String title, String content, String author) {
        this.id = count++;
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
