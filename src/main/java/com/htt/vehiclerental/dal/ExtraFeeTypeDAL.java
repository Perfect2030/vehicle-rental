package com.htt.vehiclerental.dal;

import java.util.List;

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

    public static ExtraFeeType getExtraFeeTypeByName(String name) {
        String sql = "SELECT * FROM extrafee_type WHERE name = ?";
        var result = DBHelper.getInstance().executeQuery(sql, name);

        if (result.isEmpty()) return null;

        return ExtraFeeType.fromMap(result.get(0));
    }

    public static List<ExtraFeeType> searchExtraFeeTypes(String name, int sortOption) {
        String sql = "SELECT * FROM extrafee_type WHERE name LIKE ?";

        switch (sortOption) {
            case 1: sql += " ORDER BY name ASC"; break;
            case 2: sql += " ORDER BY name DESC"; break;
            case 3: sql += " ORDER BY amount ASC"; break;
            case 4: sql += " ORDER BY amount DESC"; break;
        }

        var result = DBHelper.getInstance().executeQuery(sql, "%" + name + "%");

        if (result.isEmpty()) return null;

        return result.stream().map(ExtraFeeType::fromMap).toList();
    }

    public static List<ExtraFeeType> getAllExtraFeeTypes() {
        String sql = "SELECT * FROM extrafee_type";
        var result = DBHelper.getInstance().executeQuery(sql);

        if (result.isEmpty()) return null;

        return result.stream().map(ExtraFeeType::fromMap).toList();
    }
}
