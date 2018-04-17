package edu.fsu.cs.mobile.safereader;

public class Book {

    private String title;
    private int coverImage;
    private String author;
    private String publishDate;

    public Book() {
    }

    public Book(String title, int coverImage, String author, String publishDate) {
        this.title = title;
        this.coverImage = coverImage;
        this.author = author;
        this.publishDate = publishDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCoverImage(int coverImage) {
        this.coverImage = coverImage;
    }

    public int getCoverImage() {
        return coverImage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
