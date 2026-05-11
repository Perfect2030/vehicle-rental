package com.htt.vehiclerental.dal;

import java.util.List;

import com.htt.vehiclerental.dto.Rental;

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

    public static List<Rental> getRentalsByCustomer(String identityNumber) {
        String sql = "SELECT r.* FROM rental r WHERE r.customerId = ?";
        var results = DBHelper.getInstance().executeQuery(sql, identityNumber);

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
}
