package com.htt.vehiclerental.dto;

public class Report {
    private int totalRevenue;
    private int totalFees;
    private int totalRentals;

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalFees() {
        return totalFees;
    }

    public int getTotalRentals() {
        return totalRentals;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public void setTotalFees(int totalFees) {
        this.totalFees = totalFees;
    }

    public void setTotalRentals(int totalRentals) {
        this.totalRentals = totalRentals;
    } 
}
