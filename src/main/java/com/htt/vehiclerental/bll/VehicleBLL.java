package com.htt.vehiclerental.bll;

import java.util.List;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;
import com.htt.vehiclerental.dal.VehicleDAL;

public class VehicleBLL {
    public static List<Vehicle> getVehiclesAfterFilterAndSort(String search, VehicleType type, VehicleStatus status, String sortBy) {
        String orderBy = switch (sortBy) {
            case "Giá thuê tăng dần" -> "pricePerDay ASC";
            case "Giá thuê giảm dần" -> "pricePerDay DESC";
            case "Phân khối tăng dần" -> "displacement ASC";
            case "Phân khối giảm dần" -> "displacement DESC";
            default -> "id DESC"; // Mặc định xe mới nhất lên đầu
        };

        return VehicleDAL.getVehiclesAfterFilterAndSort(search, type, status, orderBy);
    }

    public static boolean addVehicle(Vehicle vehicle) {
        return VehicleDAL.add(vehicle);
    }

    public static boolean updateVehicle(Vehicle vehicle) {
        return VehicleDAL.update(vehicle);
    }

    public static boolean deleteVehicle(String licensePlate) {
        return VehicleDAL.delete(licensePlate);
    }

    public static List<Vehicle> searchVehicles(String model, String brand, String vehicleType, int displacement,
            int pricePerDay, String status) {
        return VehicleDAL.searchVehicles(model, brand, vehicleType, displacement, pricePerDay, status);
    }

    public static Vehicle getVehicle(String licensePlate) {
        return VehicleDAL.getVehicle(licensePlate);
    }
}