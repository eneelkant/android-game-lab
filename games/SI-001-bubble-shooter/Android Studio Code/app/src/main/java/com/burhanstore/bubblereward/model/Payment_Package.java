package com.burhanstore.bubblereward.model;

public class Payment_Package {

    String id;
    String payment_id;
    String amount;
    String coins;
    String symbol;
    String image;

    public Payment_Package(String id, String payment_id, String amount, String coins, String symbol, String image) {
        this.id = id;
        this.payment_id = payment_id;
        this.amount = amount;
        this.coins = coins;
        this.symbol = symbol;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getAmount() {
        return amount;
    }

    public String getCoins() {
        return coins;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getImage() {
        return image;
    }
}
