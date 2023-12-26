package com.example.wisebridge;

public class contents {
    private String Title_content;
    private String descript;
    private String id;
    private String price;
    private String keys;

    public contents(String title, String id, String descript, String price, String keys) {
        this.Title_content = title;
        this.id = id;
        this.descript = descript;
        this.price = price;
        this.keys = keys;
    }

    public String getName() {
        return Title_content;
    }

    public String getId() {
        return id;
    }

    public String getDescript() {
        return descript;
    }

    public String getPrice() {
        return price;
    }

    public String getKeys() {
        return keys;
    }

}
