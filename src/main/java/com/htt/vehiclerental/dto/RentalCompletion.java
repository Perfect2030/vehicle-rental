package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RentalCompletion {
    private String vehicleInfo;
    private int pricePerDay;
    private LocalDateTime rentalDate;
    private LocalDateTime actualReturnTime;
    private int extraFees;
    private int totalAmount;
    private List<RentalExtraFee> extraFeeDetails;


    public RentalCompletion(String vehicleInfo, int pricePerDay, LocalDateTime rentalDate,
            LocalDateTime actualReturnTime, int extraFees, int totalAmount, List<RentalExtraFee> extraFeeDetails) {
        this.vehicleInfo = vehicleInfo;
        this.pricePerDay = pricePerDay;
        this.rentalDate = rentalDate;
        this.actualReturnTime = actualReturnTime;
        this.extraFees = extraFees;
        this.totalAmount = totalAmount;
        this.extraFeeDetails = extraFeeDetails;
    }

    
    public String getVehicleInfo() {
        return vehicleInfo;
    }
    public int getPricePerDay() {
        return pricePerDay;
    }
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }
    public LocalDateTime getActualReturnTime() {
        return actualReturnTime;
    }
    public int getExtraFees() {
        return extraFees;
    }
    public int getTotalAmount() {
        return totalAmount;
    }
    public List<RentalExtraFee> getExtraFeeDetails() {
        return extraFeeDetails;
    }
    public void setVehicleInfo(String vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }
    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setActualReturnTime(LocalDateTime actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }
    public void setExtraFees(int extraFees) {
        this.extraFees = extraFees;
    }
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public void setExtraFeeDetails(List<RentalExtraFee> extraFeeDetails) {
        this.extraFeeDetails = extraFeeDetails;
    }
    


    
}
