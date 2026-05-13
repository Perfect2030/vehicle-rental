package com.htt.vehiclerental.dal;

import java.util.List;

import com.htt.vehiclerental.dto.Rental;
import com.htt.vehiclerental.dto.RentalView;

public class RentalDAL {
    public static boolean add(Rental rental) {
        String sql = "INSERT INTO rental (customerId, vehicleId, startTime, expectedReturnTime, actualReturnTime, pricePerDay, deposit, estimatedTotal, extraFee, totalAmount, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, rental.getCustomerId(), rental.getVehicleId(), rental.getStartTime(), rental.getExpectedReturnTime(), rental.getActualReturnTime(), rental.getPricePerDay(), rental.getDeposit(), rental.getEstimatedTotal(), rental.getExtraFee(), rental.getTotalAmount(), rental.getStatus(), rental.getCreatedAt()) > 0;
    }

    public static boolean update(Rental rental) {
        String sql = "UPDATE rental SET customerId = ?, vehicleId = ?, startTime = ?, expectedReturnTime = ?, actualReturnTime = ?, pricePerDay = ?, deposit = ?, estimatedTotal = ?, extraFee = ?, totalAmount = ?, status = ?, createdAt = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, rental.getCustomerId(), rental.getVehicleId(), rental.getStartTime(), rental.getExpectedReturnTime(), rental.getActualReturnTime(), rental.getPricePerDay(), rental.getDeposit(), rental.getEstimatedTotal(), rental.getExtraFee(), rental.getTotalAmount(), rental.getStatus(), rental.getCreatedAt(), rental.getId()) > 0;
    }

    // public static boolean delete(int rentalId) {
    //     String sql = "UPDATE rental SET isDeleted = 1 WHERE id = ?";
    //     return DBHelper.getInstance().executeUpdate(sql, rentalId) > 0;
    // }

    public static Rental getRental(int rentalId) {
        String sql = "SELECT * FROM rental WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, rentalId);

        if (result.isEmpty()) return null;

        return Rental.fromMap(result.get(0));
    }

    public static List<Rental> getAllRentals() {
        String sql = "SELECT * FROM rental";
        var results = DBHelper.getInstance().executeQuery(sql);

        List<Rental> rentals = new java.util.ArrayList<>();
        for (var result : results) {
            rentals.add(Rental.fromMap(result));
        }
        return rentals;
    }

    public static List<Rental> getRentalsByCustomer(int customerId) {
        String sql = "SELECT r.* FROM rental r WHERE r.customerId = ?";
        var results = DBHelper.getInstance().executeQuery(sql, customerId);

        List<Rental> rentals = new java.util.ArrayList<>();
        for (var result : results) {
            rentals.add(Rental.fromMap(result));
        }
        return rentals;
    }

    // -1 = ignore filter
    public static List<Rental> searchRentals(int customerId, int vehicleId, String status) {
        String sql = "SELECT * FROM rental WHERE (customerId = ? OR ? = -1) AND (vehicleId = ? OR ? = -1) AND (status LIKE ?)";
        var results = DBHelper.getInstance().executeQuery(sql, customerId, customerId, vehicleId, vehicleId, "%" + status + "%");

        List<Rental> rentals = new java.util.ArrayList<>();
        for (var result : results) {
            rentals.add(Rental.fromMap(result));
        }
        return rentals;
    }

    public static boolean checkRentalConflict(int vehicleId, java.time.LocalDateTime startTime, java.time.LocalDateTime expectedReturnTime) {
        String sql = "SELECT COUNT(*) AS count FROM rental WHERE vehicleId = ? AND ((startTime < ? AND expectedReturnTime > ?) OR (startTime < ? AND expectedReturnTime > ?) OR (startTime >= ? AND expectedReturnTime <= ?))";
        var result = DBHelper.getInstance().executeQuery(sql, vehicleId, expectedReturnTime, expectedReturnTime, startTime, startTime, startTime, expectedReturnTime);

        if (result.isEmpty()) return false;

        int count = Integer.parseInt(result.get(0).get("count").toString());
        return count > 0;
    }    

    public static boolean hasFutureRental(int vehicleId, java.time.LocalDateTime startTime) {
        String sql = "SELECT COUNT(*) AS count FROM rental WHERE vehicleId = ? AND startTime > ? AND status IN ('ACTIVE', 'CREATED')";
        var result = DBHelper.getInstance().executeQuery(sql, vehicleId, startTime);

        if (result.isEmpty()) return false;

        int count = Integer.parseInt(result.get(0).get("count").toString());
        return count > 0;
    }

    public static List<RentalView> searchRentalsViews(String searchTerm, String statusFilter) {
        String sql = "SELECT r.id AS rentalId, c.fullName AS customerName, v.licensePlate, v.brand, v.model, r.startTime, r.expectedReturnTime, r.status " +
                     "FROM rental r " +
                     "JOIN customer c ON r.customerId = c.id " +
                     "JOIN vehicle v ON r.vehicleId = v.id " +
                     "WHERE (c.fullName LIKE ? OR v.licensePlate LIKE ? OR v.brand LIKE ? OR v.model LIKE ?) " +
                     "AND (? = '' OR r.status = ?)";
        var results = DBHelper.getInstance().executeQuery(sql, "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", "%" + searchTerm + "%", statusFilter, statusFilter);

        List<RentalView> rentalViews = new java.util.ArrayList<>();
        for (var result : results) {
            RentalView view = new RentalView(
                Integer.parseInt(result.get("rentalId").toString()),
                result.get("customerName").toString(),
                result.get("licensePlate").toString(),
                result.get("brand").toString(),
                result.get("model").toString(),
                java.time.LocalDateTime.parse(result.get("startTime").toString()),
                java.time.LocalDateTime.parse(result.get("expectedReturnTime").toString()),
                result.get("status").toString()
            );
            rentalViews.add(view);
        }
        return rentalViews;
    }
}
