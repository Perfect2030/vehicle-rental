package com.htt.vehiclerental.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class UpcomingRentalCustomer {
    private String identityNumber;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime startTime;
    private LocalDateTime expectedReturnTime;

    public UpcomingRentalCustomer(String identityNumber, String fullName, String phoneNumber, LocalDateTime startTime, LocalDateTime expectedReturnTime) {
        this.identityNumber = identityNumber;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.startTime = startTime;
        this.expectedReturnTime = expectedReturnTime;
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

    public static UpcomingRentalCustomer fromMap(Map<String, Object> map) {
        return new UpcomingRentalCustomer(
                (String) map.get("identityNumber"),
                (String) map.get("fullName"),
                (String) map.get("phoneNumber"),
                (LocalDateTime) map.get("startTime"),
                (LocalDateTime) map.get("expectedReturnTime")
        );
    }

    @Override
    public String toString() {
        return "UpcomingRentalCustomer{" +
                "identityNumber='" + identityNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", startTime=" + startTime +
                ", expectedReturnTime=" + expectedReturnTime +
                '}';
    }
}
