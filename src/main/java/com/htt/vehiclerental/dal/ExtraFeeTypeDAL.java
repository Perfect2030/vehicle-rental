package com.htt.vehiclerental.dal;

import com.htt.vehiclerental.dto.ExtraFeeType;

public class ExtraFeeTypeDAL {
    public static boolean add(ExtraFeeType extraFeeType) {
        String sql = "INSERT INTO extrafee_type (name, amount, description) VALUES (?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, extraFeeType.getName(), extraFeeType.getAmount(), extraFeeType.getDescription()) > 0;
    }

    public static boolean update(ExtraFeeType extraFeeType) {
        String sql = "UPDATE extrafee_type SET name = ?, amount = ?, description = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, extraFeeType.getName(), extraFeeType.getAmount(), extraFeeType.getDescription(), extraFeeType.getId()) > 0;
    }

    public static boolean delete(int extraFeeTypeId) {
        String sql = "DELETE FROM extrafee_type WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, extraFeeTypeId) > 0;
    }

    public static ExtraFeeType getExtraFeeType(int extraFeeTypeId) {
        String sql = "SELECT * FROM extrafee_type WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, extraFeeTypeId);

        if (result.isEmpty()) return null;

        return ExtraFeeType.fromMap(result.get(0));
    }

    public static ExtraFeeType searchExtraFeeType(String name, int minAmount, int maxAmount, String description) {
        String sql = "SELECT * FROM extrafee_type WHERE name LIKE ? AND (amount >= ? OR ? = -1) AND (amount <= ? OR ? = -1) AND description LIKE ?";
        var result = DBHelper.getInstance().executeQuery(sql, "%" + name + "%", minAmount, minAmount, maxAmount, maxAmount, "%" + description + "%");

        if (result.isEmpty()) return null;

        return ExtraFeeType.fromMap(result.get(0));
    }
}
