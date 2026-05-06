package com.htt.vehiclerental.dal;

import java.util.ArrayList;
import java.util.List;

import com.htt.vehiclerental.dto.Vehicle;

public class VehicleDAL {
    
    public static boolean add(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (licensePlate, model, brand, vehicleType, displacement, pricePerDay, status, createdAt)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getBrand(), vehicle.getVehicleType(), vehicle.getDisplacement(), vehicle.getPricePerDay(), vehicle.getStatus(), vehicle.getCreatedAt()) > 0;
    }

    public static boolean update(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET licensePlate = ?, model = ?, brand = ?, vehicleType = ?, displacement = ?, pricePerDay = ?, status = ?, createdAt = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getBrand(), vehicle.getVehicleType(), vehicle.getDisplacement(), vehicle.getPricePerDay(), vehicle.getStatus(), vehicle.getCreatedAt(), vehicle.getId()) > 0;
    }

    public static boolean delete(int vehicleId) {
        String sql = "UPDATE vehicle SET isDeleted = 1 WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, vehicleId) > 0;
    }

    public static Vehicle getVehicle(int vehicleId) {
        String sql = "SELECT * FROM vehicle WHERE id = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, vehicleId);

        if (result.isEmpty()) return null;

        return Vehicle.fromMap(result.get(0));
    }

    public static List<Vehicle> getAllVehicles() {
        String sql = "SELECT * FROM vehicle WHERE isDeleted = 0";
        var results = DBHelper.getInstance().executeQuery(sql);

        List<Vehicle> vehicles = new ArrayList<>();
        for (var result : results) {
            vehicles.add(Vehicle.fromMap(result));
        }
        return vehicles;
    }
    
    public static List<Vehicle> searchVehicles(String model, String brand, String vehicleType, int displacement, int pricePerDay, String status) {
        String sql = "SELECT * FROM vehicle WHERE (model LIKE ? AND brand LIKE ? AND vehicleType LIKE ? AND (displacement >= ? OR ? = -1) AND (pricePerDay <= ? OR ? = -1) AND status = ?) AND isDeleted = 0";
        var results = DBHelper.getInstance().executeQuery(sql, "%" + model + "%", "%" + brand + "%", "%" + vehicleType + "%", displacement, displacement, pricePerDay, pricePerDay, status);

        List<Vehicle> vehicles = new ArrayList<>();
        for (var result : results) {
            vehicles.add(Vehicle.fromMap(result));
        }
        return vehicles;
    }

}
