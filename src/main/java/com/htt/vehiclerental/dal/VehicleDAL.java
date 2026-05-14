package com.htt.vehiclerental.dal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.htt.vehiclerental.dto.UpcomingRentalCustomer;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.dto.VehicleDetail;
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

    public static boolean delete(int id) {
         String sql = "UPDATE vehicle SET isDeleted = 1 WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, id) > 0;
    }

    public static Vehicle getVehicle(int id) {
        String sql = "SELECT * FROM vehicle WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, id);

        if (result.isEmpty())
            return null;

        return Vehicle.fromMap(result.get(0));
    }

    public static List<Vehicle> getAllVehicles() {
        String sql = "SELECT * FROM vehicle";
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

    public static boolean isVehicleExists(String licensePlate) {
        String sql = "SELECT * FROM vehicle WHERE licensePlate = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, licensePlate);
        return !result.isEmpty();
    }

    public static VehicleDetail getVehicleDetail(int id) {
        VehicleDetail vehicleDetail = new VehicleDetail();

        // lấy thông tin xe
        String sql = "SELECT licensePlate, model, brand, vehicleType, displacement, pricePerDay, status " +
                        " FROM vehicle WHERE id = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, id);

        if (result.isEmpty()) return null;
        
        Map<String, Object> vehicleData = result.get(0);
        vehicleDetail.setLicensePlate((String) vehicleData.get("licensePlate"));
        vehicleDetail.setModel((String) vehicleData.get("model"));
        vehicleDetail.setBrand((String) vehicleData.get("brand"));
        vehicleDetail.setVehicleType(VehicleType.fromString((String) vehicleData.get("vehicleType")));
        vehicleDetail.setDisplacement((int) vehicleData.get("displacement"));
        vehicleDetail.setPricePerDay((int) vehicleData.get("pricePerDay"));
        vehicleDetail.setStatus(VehicleStatus.fromString((String) vehicleData.get("status")));

        // lấy thông tin khách hàng thuê xe sắp tới
        sql = "SELECT c.identityNumber, c.fullName, c.phoneNumber, r.startTime, r.expectedReturnTime " +
                "FROM rental r " +
                "JOIN customer c ON r.customerId = c.id " +
                "WHERE r.vehicleId = ? AND r.expectedReturnTime > NOW() AND r.status IN ('ACTIVE', 'CREATED') " +
                "ORDER BY r.startTime ASC";
        
        var rentalResults = DBHelper.getInstance().executeQuery(sql, id);
        
        List<UpcomingRentalCustomer> upcomingRentalCustomers = new ArrayList<>();
        for (var r : rentalResults) {
            UpcomingRentalCustomer customer = UpcomingRentalCustomer.fromMap(r);
            upcomingRentalCustomers.add(customer);
        }
        vehicleDetail.setUpcomingRentalCustomers(upcomingRentalCustomers);

        return vehicleDetail;
    }

    public static int getVehicleCount(VehicleStatus status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS count FROM vehicle WHERE isDeleted = 0");

        if (status != null) {
            sql.append(" AND status = '").append(status.name()).append("'");
        }

        var result = DBHelper.getInstance().executeQuery(sql.toString());
        if (result.isEmpty()) return 0;
        return ((Long) result.get(0).get("count")).intValue();
    }
}
