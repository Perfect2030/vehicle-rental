package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Customer {
    private int id;
    private String identityNumber;
    private String fullName;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;

    public Customer() {
    }

    public Customer(int id, String identityNumber, String fullName, String phoneNumber, String address, LocalDateTime createdAt) {
        this.id = id;
        this.identityNumber = identityNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
    }

    public Customer(String identityNumber, String fullName, String phoneNumber, String address, LocalDateTime createdAt) {
        this.id = 0; //auto assign
        this.identityNumber = identityNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdAt = createdAt;
    }

    public static Customer fromMap(Map<String, Object> map) {
        return new Customer(
                (int) map.get("id"),
                (String) map.get("identityNumber"),
                (String) map.get("fullName"),
                (String) map.get("phoneNumber"),
                (String) map.get("address"),
                (LocalDateTime) map.get("createdAt")
        );
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", identityNumber='" + identityNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
