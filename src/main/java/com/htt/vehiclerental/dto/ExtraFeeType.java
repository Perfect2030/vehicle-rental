package com.htt.vehiclerental.dto;

import java.util.Map;

public class ExtraFeeType {
    private int id;
    private String name;
    private int amount;
    private String description;

    public ExtraFeeType() {
    }

    public ExtraFeeType(int id, String name, int amount, String description) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    public static ExtraFeeType fromMap(Map<String, Object> map) {
        return new ExtraFeeType(
                (int) map.get("id"),
                (String) map.get("name"),
                (int) map.get("amount"),
                (String) map.get("description")
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExtraFeeType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
