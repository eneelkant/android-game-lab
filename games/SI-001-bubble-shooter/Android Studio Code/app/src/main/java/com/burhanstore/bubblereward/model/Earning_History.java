package com.burhanstore.bubblereward.model;

public class Earning_History {

    String id;
    String points;
    String type_earn;
    String created_date;
    String minas;


    public Earning_History(String id, String points, String type_earn, String created_date, String minas) {
        this.id = id;
        this.points = points;
        this.type_earn = type_earn;
        this.created_date = created_date;
        this.minas = minas;
    }


    public String getId() {
        return id;
    }

    public String getPoints() {
        return points;
    }

    public String getType_earn() {
        return type_earn;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getMinas() {
        return minas;
    }
}
