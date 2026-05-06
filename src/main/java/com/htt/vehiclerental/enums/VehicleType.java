package com.htt.vehiclerental.enums;

public enum VehicleType {
    MANUAL("Xe số"),
    AUTOMATIC("Xe ga"),
    SCOOTER("Xe côn");

    private final String displayName;

    VehicleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static VehicleType fromString(String value) {
        try {
            return VehicleType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
