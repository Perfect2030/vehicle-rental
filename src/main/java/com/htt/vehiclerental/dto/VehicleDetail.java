package com.htt.vehiclerental.dto;

import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;
import java.util.*;

public class VehicleDetail {
    private String licensePlate;
    private String brand;
    private String model;
    private VehicleType vehicleType;
    private int displacement;
    private double pricePerDay;
    private VehicleStatus status;
    private List<UpcomingRentalCustomer> upcomingRentalCustomers;

    public VehicleDetail() {
    }
    public VehicleDetail(String licensePlate, String brand, String model, VehicleType vehicleType, int displacement,
            double pricePerDay, VehicleStatus status, List<UpcomingRentalCustomer> upcomingRentalCustomers) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.displacement = displacement;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.upcomingRentalCustomers = upcomingRentalCustomers;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    public List<UpcomingRentalCustomer> getUpcomingRentalCustomers() {
        return upcomingRentalCustomers;
    }
   
    public void setUpcomingRentalCustomers(List<UpcomingRentalCustomer> upcomingRentalCustomers) {
        this.upcomingRentalCustomers = upcomingRentalCustomers;
    }
    
}
