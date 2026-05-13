package com.htt.vehiclerental.dto;

import java.util.Map;

public class RentalExtraFee {
    private int id;
    private Integer rentalId; // Nullable vì cho phép custom khi không có lỗi khác
    private String name;
    private int amount;
    private String description;

    public RentalExtraFee() {
    }

    public RentalExtraFee(int id, int rentalId, String name, int amount, String description) {
        this.id = id;
        this.rentalId = rentalId;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    

    public static RentalExtraFee fromMap(Map<String, Object> map) {
        return new RentalExtraFee(
                (int) map.get("id"),
                (Integer) map.get("rentalId"),
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

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
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
        return "RentalExtraFee{" +
                "id=" + id +
                ", rentalId=" + rentalId +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
