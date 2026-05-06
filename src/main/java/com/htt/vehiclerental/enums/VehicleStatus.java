package com.htt.vehiclerental.enums;

public enum VehicleStatus {
    AVAILABLE("Sẵn sàng"),
    RENTED("Đang cho thuê"),
    MAINTENANCE("Bảo trì");

    private final String displayName;

    VehicleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static VehicleStatus fromString(String value) {
        try {
            return VehicleStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
