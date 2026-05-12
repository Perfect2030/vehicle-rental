package com.htt.vehiclerental.bll;

import java.util.ArrayList;
import java.util.List;

import com.htt.vehiclerental.dto.*;
import com.htt.vehiclerental.dal.*;

public class RentalBLL {
    public static List<RentalView> getAllRentalsView() {
        List<Rental> rentals = RentalDAL.getAllRentals();
        List<RentalView> rentalViews = new ArrayList<>();
        
        if (rentals == null) {
            return rentalViews;
        }
        
        for (Rental rental : rentals) {
            Customer customer = CustomerDAL.getCustomer(rental.getCustomerId());
            Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
            RentalView view = new RentalView(
                    rental.getId(),
                    customer != null ? customer.getFullName() : "Unknown",
                    vehicle != null ? vehicle.getLicensePlate() : "Unknown",
                    vehicle != null ? vehicle.getBrand() : "Unknown",
                    vehicle != null ? vehicle.getModel() : "Unknown",
                    rental.getStartTime(),
                    rental.getExpectedReturnTime(),
                    rental.getStatus()
            );
            rentalViews.add(view);
        }
        return rentalViews;
    }

    public static RentalDetail getRentalDetail(int rentalId) {
        Rental rental = RentalDAL.getRental(rentalId);
        if (rental == null) {
            return null;
        }
        Customer customer = CustomerDAL.getCustomer(rental.getCustomerId());
        Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
        List<RentalExtraFee>  extraFees = RentalExtraFeeDAL.getRentalExtraFeesByRentalId(rentalId);

        RentalDetail detail = new RentalDetail(
                rental.getId(),
                vehicle != null ? vehicle.getLicensePlate() : "Unknown",
                vehicle != null ? vehicle.getBrand() : "Unknown",   
                vehicle != null ? vehicle.getModel() : "Unknown",
                customer != null ? customer.getIdentityNumber() : "Unknown",
                customer != null ? customer.getFullName() : "Unknown",
                customer != null ? customer.getPhoneNumber() : "Unknown",
                customer != null ? customer.getAddress() : "Unknown",
                rental.getStartTime(),
                rental.getExpectedReturnTime(),
                rental.getActualReturnTime(),
                rental.getPricePerDay(),
                rental.getEstimatedTotal(),
                rental.getTotalAmount(),
                rental.getExtraFee(),
                extraFees
        );
        return detail;
    }
}
