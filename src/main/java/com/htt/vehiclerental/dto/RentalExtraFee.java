package com.htt.vehiclerental.dto;

import java.util.Map;

public class RentalExtraFee {
    private int id;
    private Integer rentalId; // Nullable vì cho phép custom khi không có lỗi khác
    private int extraFeeTypeId;
    private int amount;
    private String description;

    public RentalExtraFee() {
    }

    public RentalExtraFee(int id, Integer rentalId, int extraFeeTypeId, int amount, String description) {
        this.id = id;
        this.rentalId = rentalId;
        this.extraFeeTypeId = extraFeeTypeId;
        this.amount = amount;
        this.description = description;
    }

    

    public static RentalExtraFee fromMap(Map<String, Object> map) {
        return new RentalExtraFee(
                (int) map.get("id"),
                (Integer) map.get("rentalId"),
                (int) map.get("extraFeeTypeId"),
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

    public int getExtraFeeTypeId() {
        return extraFeeTypeId;
    }

    public void setExtraFeeTypeId(int extraFeeTypeId) {
        this.extraFeeTypeId = extraFeeTypeId;
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
                ", extraFeeTypeId=" + extraFeeTypeId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
