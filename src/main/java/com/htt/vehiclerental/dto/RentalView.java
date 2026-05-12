package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;

public class RentalView {
    private int rentalId;
    private String customerName;
    private String licensePlate;
    private String brand;
    private String model;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;
    private String status;


    
    
    public RentalView(int rentalId, String customerName, String licensePlate, String brand, String model,
            LocalDateTime startTime, LocalDateTime expectedReturnTime, String status) {
        this.rentalId = rentalId;
        this.customerName = customerName;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
        this.status = status;
    }


    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public void setExpectedReturnTime(LocalDateTime expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getRentalId() {
        return rentalId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public String getBrand() {
        return brand;
    }
    public String getModel() {
        return model;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getExpectedReturnTime() {
        return expectedReturnTime;
    }
    public String getStatus() {
        return status;
    }

}
