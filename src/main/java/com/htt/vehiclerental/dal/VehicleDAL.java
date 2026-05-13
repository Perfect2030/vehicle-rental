package com.htt.vehiclerental.dal;

import java.util.ArrayList;
import java.util.List;

import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;

public class VehicleDAL {

    public static boolean add(Vehicle vehicle) {
        String sql = "INSERT INTO vehicle (licensePlate, model, brand, vehicleType, displacement, pricePerDay, status, createdAt)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, vehicle.getLicensePlate(), vehicle.getModel(),
                vehicle.getBrand(), vehicle.getVehicleType().name(), vehicle.getDisplacement(),
                vehicle.getPricePerDay(),
                vehicle.getStatus(), vehicle.getCreatedAt()) > 0;
    }

    public static boolean update(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET licensePlate = ?, model = ?, brand = ?, vehicleType = ?, displacement = ?, pricePerDay = ?, status = ?, createdAt = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, vehicle.getLicensePlate(), vehicle.getModel(),
                vehicle.getBrand(), vehicle.getVehicleType().name(), vehicle.getDisplacement(),
                vehicle.getPricePerDay(),
                vehicle.getStatus(), vehicle.getCreatedAt(), vehicle.getId()) > 0;
    }

    public static boolean delete(String licensePlate) {
        String sql = "UPDATE vehicle SET isDeleted = 1 WHERE licensePlate = ?";
        return DBHelper.getInstance().executeUpdate(sql, licensePlate) > 0;
    }

    public static Vehicle getVehicle(String licensePlate) {
        String sql = "SELECT * FROM vehicle WHERE licensePlate = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, licensePlate);

        if (result.isEmpty())
            return null;

        return Vehicle.fromMap(result.get(0));
    }

    public static Vehicle getVehicle(int id) {
        String sql = "SELECT * FROM vehicle WHERE id = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, id);

        if (result.isEmpty())
            return null;

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

    public static List<Vehicle> getVehiclesAfterFilterAndSort(String search, VehicleType type, VehicleStatus status, String orderBy) {
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM vehicle WHERE (licensePlate LIKE ? OR brand LIKE ? OR model LIKE ?) AND isDeleted = 0");


        // xử lý status và type
        if (status != null) {
            sql.append(" AND status = '").append(status.name()).append("'");
        }
        if (type != null) {
            sql.append(" AND vehicleType = '").append(type.name()).append("'");
        }
        

        if(orderBy != null && !orderBy.isEmpty()) {
            sql.append(" ORDER BY ").append(orderBy);
        } 

        var results = DBHelper.getInstance().executeQuery(sql.toString(), "%" + search + "%", "%" + search + "%", "%" + search + "%");

        List<Vehicle> vehicles = new ArrayList<>();
        for (var result : results) {
            vehicles.add(Vehicle.fromMap(result));
        }
        return vehicles;
    }

}
