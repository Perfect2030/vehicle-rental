package com.htt.vehiclerental.dal;

public class CreateRentalDAL {

    public static boolean checkRentalConflict(int vehicleId, java.time.LocalDateTime startTime, java.time.LocalDateTime expectedReturnTime) {
        String sql = "SELECT COUNT(*) AS count FROM rental WHERE vehicleId = ? AND ((startTime < ? AND expectedReturnTime > ?) OR (startTime < ? AND expectedReturnTime > ?) OR (startTime >= ? AND expectedReturnTime <= ?))";
        var result = DBHelper.getInstance().executeQuery(sql, vehicleId, expectedReturnTime, expectedReturnTime, startTime, startTime, startTime, expectedReturnTime);

        if (result.isEmpty()) return false;

        int count = Integer.parseInt(result.get(0).get("count").toString());
        return count > 0;
    }    
}
