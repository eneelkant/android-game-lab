package com.burhanstore.bubblereward.model;

public class Leaderboard_model {
    public Leaderboard_model(String name, String points, String image, int tag) {
        this.name = name;
        this.points = points;
        this.image = image;
        this.tag = String.valueOf(tag);
    }

    public String getName() {
        return name;
    }

    public String getPoints() {
        return points;
    }

    public String getImage() {
        return image;
    }

    public String getTag() {
        return tag;
    }

    String name, points, image, tag;
}
