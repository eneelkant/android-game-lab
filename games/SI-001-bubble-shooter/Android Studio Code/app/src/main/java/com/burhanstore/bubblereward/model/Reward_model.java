package com.burhanstore.bubblereward.model;

public class Reward_model {
    String id;
    String name;
    String image;
    String symbol;
    String hint;
    String input_type;
    String details;


    public Reward_model(String id, String name, String image, String symbol, String hint, String input_type, String details) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.symbol = symbol;
        this.hint = hint;
        this.input_type = input_type;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getHint() {
        return hint;
    }

    public String getInput_type() {
        return input_type;
    }

    public String getDetails() {
        return details;
    }
}
