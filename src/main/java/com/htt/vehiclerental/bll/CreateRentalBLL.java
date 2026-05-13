package com.htt.vehiclerental.bll;

import com.htt.vehiclerental.dal.CustomerDAL;
import com.htt.vehiclerental.dal.RentalDAL;
import com.htt.vehiclerental.dal.VehicleDAL;
import com.htt.vehiclerental.dal.CreateRentalDAL;
import com.htt.vehiclerental.dto.CreateRental;
import com.htt.vehiclerental.dto.Rental;
import com.htt.vehiclerental.enums.RentalStatus;

public class CreateRentalBLL {
    public static final int SUCCESS = 1;
    public static final int RENTAL_EXISTS = -1;
    public static final int DATABASE_ERROR = -2;
    public static final int NOT_FOUND_VEHICLE_OR_CUSTOMER = -3;

    public static int addRental(CreateRental rental) {
        // Kiểm tra xem khách hàng và xe có tồn tại không
        if (CustomerDAL.getCustomer(rental.getCustomerId()) == null || VehicleDAL.getVehicle(rental.getVehicleId()) == null) {
            return NOT_FOUND_VEHICLE_OR_CUSTOMER; // Không tìm thấy khách hàng hoặc xe
        }

        // Kiểm tra xem đã có hợp đồng thuê nào cho xe này trong khoảng thời gian này chưa
        if (CreateRentalDAL.checkRentalConflict(rental.getVehicleId(), rental.getStartTime(), rental.getExpectedReturnTime())) {
            return RENTAL_EXISTS; // Đã có hợp đồng thuê xung đột
        }

        Rental newRental = new Rental();
        newRental.setCustomerId(rental.getCustomerId());
        newRental.setVehicleId(rental.getVehicleId());
        newRental.setStartTime(rental.getStartTime());
        newRental.setExpectedReturnTime(rental.getExpectedReturnTime());
        newRental.setPricePerDay(rental.getPricePerDay());
        newRental.setDeposit(rental.getDeposit());
        newRental.setTotalAmount(rental.getTotalAmount());
        newRental.setStatusEnum(RentalStatus.CREATED);
        newRental.setCreatedAt(java.time.LocalDateTime.now());

        // Thêm hợp đồng thuê vào cơ sở dữ liệu
        if (!RentalDAL.add(newRental)) {
            return DATABASE_ERROR; // Lỗi khi thêm vào cơ sở dữ liệu
        }

        return SUCCESS; // Thành công
    }
}
