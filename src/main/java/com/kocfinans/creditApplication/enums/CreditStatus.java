package com.kocfinans.creditApplication.enums;

public enum CreditStatus {
    APPROVE(1,"Kabul"),REFUSE(2,"Red");
    private final int id;
    private final String name;

    CreditStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
