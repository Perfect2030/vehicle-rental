package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;

import com.htt.vehiclerental.enums.RentalStatus;

public class CreateRental {
    private int id;
    private String customerId;
    private int vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private int pricePerDay;
    private int deposit;
    private int totalAmount;

    public CreateRental() {
    }

    public CreateRental(int id, String customerId, int vehicleId, LocalDateTime startTime, LocalDateTime expectedReturnTime,
                  int pricePerDay, int deposit, int totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
        this.pricePerDay = pricePerDay;
        this.deposit = deposit;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(LocalDateTime expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", vehicleId=" + vehicleId +
                ", startTime=" + startTime +
                ", expectedReturnTime=" + expectedReturnTime +
                ", deposit=" + deposit +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
