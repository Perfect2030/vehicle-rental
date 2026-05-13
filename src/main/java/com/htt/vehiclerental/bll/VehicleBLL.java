package com.htt.vehiclerental.bll;

import java.util.List;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;
import com.htt.vehiclerental.dal.VehicleDAL;
import com.htt.vehiclerental.bll.RentalBLL;

public class VehicleBLL {

    public static final int SUCCESS = 1;
    public static final int NOT_FOUND = -1;
    public static final int RENTAL_EXISTS = -2;
    public static final int DATABASE_ERROR = -3;
    public static final int VEHICLE_EXISTS = -4;

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

    public static int addVehicle(Vehicle vehicle) {
        if(VehicleDAL.getVehicle(vehicle.getLicensePlate()) != null) {
            return VEHICLE_EXISTS; 
        }

        if(!VehicleDAL.add(vehicle)) {
            return DATABASE_ERROR; 
        }

        return SUCCESS;
    }

    public static int updateVehicle(Vehicle vehicle) {
        Vehicle existingVehicle = VehicleDAL.getVehicle(vehicle.getId());
        if(existingVehicle == null) {
            return NOT_FOUND; 
        }
        if(existingVehicle.getStatusEnum() == VehicleStatus.RENTED) {
            return RENTAL_EXISTS; 
        }
        if(!VehicleDAL.update(vehicle)) {
            return DATABASE_ERROR;
        }

        return SUCCESS;
    }

    public static int deleteVehicle(String licensePlate) {
        Vehicle vehicle = VehicleDAL.getVehicle(licensePlate);
        if(vehicle == null) {
            return NOT_FOUND;
        }
        if(vehicle.getStatusEnum() == VehicleStatus.RENTED) {
            return RENTAL_EXISTS;
        }

        if(!VehicleDAL.delete(licensePlate)) {
            return DATABASE_ERROR;
        }
        
        return SUCCESS;
    }

    public static List<Vehicle> searchVehicles(String model, String brand, String vehicleType, int displacement,
            int pricePerDay, String status) {
        return VehicleDAL.searchVehicles(model, brand, vehicleType, displacement, pricePerDay, status);
    }

    public static Vehicle getVehicle(String licensePlate) {
        return VehicleDAL.getVehicle(licensePlate);
    }
}