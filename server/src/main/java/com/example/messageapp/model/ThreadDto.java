package com.example.messageapp.model;

/**
 * POJO for generating threads
 */
public class ThreadDto {
    private String title;
    private String content;

    public ThreadDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
