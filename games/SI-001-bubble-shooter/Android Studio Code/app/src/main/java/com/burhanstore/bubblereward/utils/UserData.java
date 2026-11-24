package com.burhanstore.bubblereward.utils;

public class UserData {
    private String id;
    private String name;
    private String phone;
    private String image;
    private String username;
    private String password;
    private String refer;
    private String email;
    private String device_id;
    private String user_country;
    private String date_registered;
    private String points;
    private String user_id;
    private String status;
    private String total_refer;
    private String notice_badge;
    private String daily_date_store;
    private String spin_count;
    private String last_date_spin;


    public UserData(String id, String name, String phone, String image, String username, String password, String refer, String email, String device_id, String user_country, String date_registered, String points, String user_id, String status, String total_refer, String notice_badge, String daily_date_store, String spin_count, String last_date_spin) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.username = username;
        this.password = password;
        this.refer = refer;
        this.email = email;
        this.device_id = device_id;
        this.user_country = user_country;
        this.date_registered = date_registered;
        this.points = points;
        this.user_id = user_id;
        this.status = status;
        this.total_refer = total_refer;
        this.notice_badge = notice_badge;
        this.daily_date_store = daily_date_store;
        this.spin_count = spin_count;
        this.last_date_spin = last_date_spin;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRefer() {
        return refer;
    }

    public String getEmail() {
        return email;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getUser_country() {
        return user_country;
    }

    public String getDate_registered() {
        return date_registered;
    }

    public String getPoints() {
        return points;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public String getTotal_refer() {
        return total_refer;
    }

    public String getNotice_badge() {
        return notice_badge;
    }

    public String getDaily_date_store() {
        return daily_date_store;
    }

    public String getSpin_count() {
        return spin_count;
    }

    public String getLast_date_spin() {
        return last_date_spin;
    }
}
