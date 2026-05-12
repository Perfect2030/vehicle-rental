package com.htt.vehiclerental.bll;

import java.util.List;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.dal.VehicleDAL;

public class VehicleBLL {
    public static List<Vehicle> getAllVehicles() {
        return VehicleDAL.getAllVehicles();
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