package com.example.wisebridge;

public class Reviews {
    private String owner;
    private String rev;
    private String rate;

    public Reviews(String owner, String rev, String rate) {
        this.owner= owner;
        this.rev = rev;
        this.rate = rate;
    }

    public String getOwner() {
        return owner;
    }

    public String getRev() {
        return rev;
    }

    public String getRate() {
        return rate;
    }
}
