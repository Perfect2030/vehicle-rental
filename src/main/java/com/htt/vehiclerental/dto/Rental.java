package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.htt.vehiclerental.enums.RentalStatus;

public class Rental {
    private int id;
    private String customerId;
    private int vehicleId;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private LocalDateTime actualReturnTime;
    private int pricePerDay;
    private int deposit;
    private int estimatedTotal;
    private int extraFee;
    private int totalAmount;
    private RentalStatus status;
    private LocalDateTime createdAt;

    public Rental() {
    }

    public Rental(int id, String customerId, int vehicleId, LocalDateTime startTime, LocalDateTime expectedReturnTime, 
                  LocalDateTime actualReturnTime, int pricePerDay, int deposit, int estimatedTotal, int extraFee, int totalAmount, RentalStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
        this.actualReturnTime = actualReturnTime;
        this.pricePerDay = pricePerDay;
        this.deposit = deposit;
        this.estimatedTotal = estimatedTotal;
        this.extraFee = extraFee;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Rental fromMap(Map<String, Object> map) {
        return new Rental(
                (int) map.get("id"),
                (String) map.get("customerId"),
                (int) map.get("vehicleId"),
                (LocalDateTime) map.get("startTime"),
                (LocalDateTime) map.get("expectedReturnTime"),
                (LocalDateTime) map.get("actualReturnTime"),
                (int) map.get("pricePerDay"),
                (int) map.get("deposit"),
                (int) map.get("estimatedTotal"),
                (int) map.get("extraFee"),
                (int) map.get("totalAmount"),
                RentalStatus.fromString((String) map.get("status")),
                (LocalDateTime) map.get("createdAt")
        );
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

    public LocalDateTime getActualReturnTime() {
        return actualReturnTime;
    }

    public void setActualReturnTime(LocalDateTime actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
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

    public int getExtraFee() {
        return extraFee;
    }

    public void setExtraFee(int extraFee) {
        this.extraFee = extraFee;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        this.status = RentalStatus.fromString(status);
    }

    public RentalStatus getStatusEnum() {
        return status;
    }

    public void setStatusEnum(RentalStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", vehicleId=" + vehicleId +
                ", startTime=" + startTime +
                ", expectedReturnTime=" + expectedReturnTime +
                ", actualReturnTime=" + actualReturnTime +
                ", pricePerDay=" + pricePerDay +
                ", deposit=" + deposit +
                ", estimatedTotal=" + estimatedTotal +
                ", extraFee=" + extraFee +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
