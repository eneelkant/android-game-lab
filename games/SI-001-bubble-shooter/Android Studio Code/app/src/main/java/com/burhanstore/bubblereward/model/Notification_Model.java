package com.burhanstore.bubblereward.model;

public class Notification_Model {

    String id;
    String title;
    String url;
    String date;
    String message;

    public Notification_Model(String id, String title, String url, String date, String message) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.date = date;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }
}
