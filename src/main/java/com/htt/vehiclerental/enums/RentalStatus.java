package com.htt.vehiclerental.enums;

public enum RentalStatus {
    CREATED("Vừa tạo"),
    ACTIVE("Đang thuê"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy");

    private final String displayName;

    RentalStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RentalStatus fromString(String value) {
        try {
            return RentalStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
