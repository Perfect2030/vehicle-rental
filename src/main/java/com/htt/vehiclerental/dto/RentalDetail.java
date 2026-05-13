package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.List;

public class RentalDetail {
   
    private int rentalId;
    private String vehiclePlate;
    private String vehicleBrand;
    private String vehicleModel;

    private String customerId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;

    private LocalDateTime rentalDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnTime;
    private int pricePerDay;
    
    private int estimatedTotal;
    private int totalAmount;
    private int extraFees;
    private List<RentalExtraFee> extraFeeDetails; // danh sách chi tiết các phí phát sinh (nếu có)

    
    
    public RentalDetail(int rentalId, String vehiclePlate, String vehicleBrand, String vehicleModel, String customerId,
            String customerName, String customerPhone, String customerAddress, LocalDateTime rentalDate,
            LocalDateTime expectedReturnDate, LocalDateTime actualReturnTime, int pricePerDay, int estimatedTotal,
            int totalAmount, int extraFees, List<RentalExtraFee> extraFeeDetails) {
        this.rentalId = rentalId;
        this.vehiclePlate = vehiclePlate;
        this.vehicleBrand = vehicleBrand;
        this.vehicleModel = vehicleModel;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.rentalDate = rentalDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnTime = actualReturnTime;
        this.pricePerDay = pricePerDay;
        this.estimatedTotal = estimatedTotal;
        this.totalAmount = totalAmount;
        this.extraFees = extraFees;
        this.extraFeeDetails = extraFeeDetails;
    }



    public int getRentalId() {
        return rentalId;
    }
    public String getVehiclePlate() {
        return vehiclePlate;
    }
    
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }
    public void setActualReturnTime(LocalDateTime actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }
    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public void setEstimatedTotal(int estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setExtraFees(int extraFees) {
        this.extraFees = extraFees;
    }
    public String getVehicleBrand() {
        return vehicleBrand;
    }
    public String getVehicleModel() {
        return vehicleModel;
    }
    public String getCustomerId() {
        return customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public String getCustomerAddress() {
        return customerAddress;
    }
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }
    public LocalDateTime getExpectedReturnDate() {
        return expectedReturnDate;
    }
    public LocalDateTime getActualReturnTime() {
        return actualReturnTime;
    }
    public int getPricePerDay() {
        return pricePerDay;
    }
    public int getEstimatedTotal() {
        return estimatedTotal;
    }
    public int getTotalAmount() {
        return totalAmount;
    }
    public int getExtraFees() {
        return extraFees;
    }

    public List<RentalExtraFee> getExtraFeeDetails() {
        return extraFeeDetails;
    }
    public void setExtraFeeDetails(List<RentalExtraFee> extraFeeDetails) {
        this.extraFeeDetails = extraFeeDetails;
    }
    

}
