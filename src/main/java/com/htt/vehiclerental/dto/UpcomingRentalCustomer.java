package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;

public class UpcomingRentalCustomer {
    private String cccd;
    private String name;
    private String phone;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;

    public UpcomingRentalCustomer(String cccd, String name, String phone, LocalDateTime startTime, LocalDateTime expectedReturnTime) {
        this.cccd = cccd;
        this.name = name;
        this.phone = phone;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
