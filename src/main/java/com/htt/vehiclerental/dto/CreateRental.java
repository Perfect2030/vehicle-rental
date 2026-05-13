package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;

public class CreateRental {
    private int id;
    private int customerId;
    private int vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private int pricePerDay;
    private int deposit;
    private int estimatedTotal;

    public CreateRental() {
    }

    public CreateRental(int id, int customerId, int vehicleId, LocalDateTime startTime, LocalDateTime expectedReturnTime,
                  int pricePerDay, int deposit, int estimatedTotal) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
        this.pricePerDay = pricePerDay;
        this.deposit = deposit;
        this.estimatedTotal = estimatedTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
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

    public int getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(int estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", vehicleId=" + vehicleId +
                ", startTime=" + startTime +
                ", expectedReturnTime=" + expectedReturnTime +
                ", deposit=" + deposit +
                ", estimatedTotal=" + estimatedTotal +
                '}';
    }
}
