package com.model;

public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String imageFile;
    private String time;
    private String category;

    public BlogDTO() {
    }

    public BlogDTO(Long id, String title, String content, String author, String imageFile, String time, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.imageFile = imageFile;
        this.time = time;
        this.category = category;
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
