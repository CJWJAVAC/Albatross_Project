package com.company.albatross.models;

public class Post {
    private String documentId;
    private String title;
    private String contents;

    private String date;

    public Post() {
    }

    public Post(String documentId, String title, String contents) {
        this.documentId = documentId;
        this.title = title;
        this.contents = contents;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
