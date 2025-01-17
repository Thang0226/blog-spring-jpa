package com.model;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class BlogForm {
    private Long id;
    private String title;
    private String content;
    private String author;
    private MultipartFile imageFile;
    private String time;
    private Long category_id;

    public BlogForm() {
    }

    public BlogForm(Long id, String title, String content, String author, MultipartFile imageFile,
                    String time, Long category_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageFile = imageFile;
        this.time = time;
        this.category_id = category_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
}
