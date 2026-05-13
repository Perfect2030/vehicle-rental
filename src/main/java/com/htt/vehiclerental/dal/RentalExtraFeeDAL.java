package com.htt.vehiclerental.dal;

import java.util.List;

import com.htt.vehiclerental.dto.RentalExtraFee;

public class RentalExtraFeeDAL {
    public static boolean add(RentalExtraFee rentalExtraFee) {
        String sql = "INSERT INTO rental_extrafee (rentalId, extraFeeTypeId, amount, description) VALUES (?, ?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, rentalExtraFee.getRentalId(), rentalExtraFee.getExtraFeeTypeId(), rentalExtraFee.getAmount(), rentalExtraFee.getDescription()) > 0;
    }

    public static boolean update(RentalExtraFee rentalExtraFee) {
        String sql = "UPDATE rental_extrafee SET rentalId = ?, extraFeeTypeId = ?, amount = ?, description = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, rentalExtraFee.getRentalId(), rentalExtraFee.getExtraFeeTypeId(), rentalExtraFee.getAmount(), rentalExtraFee.getDescription(), rentalExtraFee.getId()) > 0;
    }

    // public static boolean delete(int rentalExtraFeeId) {
    //     String sql = "DELETE FROM rental_extra_fee WHERE id = ?";
    //     return DBHelper.getInstance().executeUpdate(sql, rentalExtraFeeId) > 0;
    // }

    public static RentalExtraFee getRentalExtraFee(int rentalExtraFeeId) {
        String sql = "SELECT * FROM rental_extrafee WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, rentalExtraFeeId);

        if (result.isEmpty()) return null;

        return RentalExtraFee.fromMap(result.get(0));
    }

    public static List<RentalExtraFee> getRentalExtraFeesByRentalId(int rentalId) {
        String sql = "SELECT * FROM rental_extrafee WHERE rentalId = ?";
        var results = DBHelper.getInstance().executeQuery(sql, rentalId);

        return results.stream().map(RentalExtraFee::fromMap).toList();
    }
}
