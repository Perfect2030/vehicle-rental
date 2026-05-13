package com.htt.vehiclerental.dto;

public class CustomerDetail extends Customer {
    private int totalRentals;
    
    public CustomerDetail(Customer customer, int totalRentals) {
        super(customer.getIdentityNumber(), customer.getFullName(), customer.getPhoneNumber(), customer.getAddress(), customer.getCreatedAt());
        this.totalRentals = totalRentals;
    }

    public int getTotalRentals() {
        return totalRentals;
    }

    public void setTotalRentals(int totalRentals) {
        this.totalRentals = totalRentals;
    }
}
