package com.burhanstore.bubblereward.model;

public class Transaction_Histroy {
    String id;
    String payment_method;
    String payment_coin;
    String package_amount;
    String created_date;
    String status;


    public Transaction_Histroy(String id, String payment_method, String payment_coin, String package_amount, String created_date, String status) {
        this.id = id;
        this.payment_method = payment_method;
        this.payment_coin = payment_coin;
        this.package_amount = package_amount;
        this.created_date = created_date;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public String getPayment_coin() {
        return payment_coin;
    }

    public String getPackage_amount() {
        return package_amount;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getStatus() {
        return status;
    }
}
