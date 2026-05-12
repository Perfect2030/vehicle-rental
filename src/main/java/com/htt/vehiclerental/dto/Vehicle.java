package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;

public class Vehicle {
    private int id;
    private String licensePlate;
    private String model;
    private String brand;
    private VehicleType vehicleType;
    private int displacement; // phân khối (cc)
    private int pricePerDay;
    private VehicleStatus status;
    private LocalDateTime createdAt;
    private boolean isDeleted;

    public Vehicle() {
    }

    public Vehicle(int id, String licensePlate, String model, String brand, VehicleType vehicleType, int displacement,
            int pricePerDay, VehicleStatus status, LocalDateTime createdAt, boolean isDeleted) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.model = model;
        this.brand = brand;
        this.vehicleType = vehicleType;
        this.displacement = displacement;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public static Vehicle fromMap(Map<String, Object> map) {
        return new Vehicle(
                (int) map.get("id"),
                (String) map.get("licensePlate"),
                (String) map.get("model"),
                (String) map.get("brand"),
                VehicleType.fromString((String) map.get("vehicleType")),
                (int) map.get("displacement"),
                (int) map.get("pricePerDay"),
                VehicleStatus.fromString((String) map.get("status")),
                (LocalDateTime) map.get("createdAt"),
                (boolean) map.get("isDeleted"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(String status) {
        this.status = VehicleStatus.fromString(status);
    }

    public VehicleStatus getStatusEnum() {
        return status;
    }

    public void setStatusEnum(VehicleStatus status) {
        this.status = status;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleTypeDisplay() {
        return vehicleType != null ? vehicleType.getDisplayName() : "";
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", vehicleType=" + vehicleType +
                ", displacement=" + displacement +
                ", pricePerDay=" + pricePerDay +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
