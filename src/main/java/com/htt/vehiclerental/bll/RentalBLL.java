package com.htt.vehiclerental.bll;

import java.time.Duration;
import java.time.LocalDateTime;
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

    public static RentalCompletion getRentalCompletion(int rentalId) {
        Rental rental = RentalDAL.getRental(rentalId);
        if (rental == null) {
            return null;
        }
        Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
        List<RentalExtraFee>  extraFees = RentalExtraFeeDAL.getRentalExtraFeesByRentalId(rentalId);

        int totalAmount = (int)(rental.getPricePerDay() 
                    * (Duration.between(rental.getStartTime(), LocalDateTime.now()).toMinutes()/1440 ) 
                    + rental.getExtraFee());
        RentalCompletion completion = new RentalCompletion(
                vehicle != null ? vehicle.getLicensePlate() + " - " + vehicle.getBrand() + " " + vehicle.getModel() : "Unknown",
                rental.getPricePerDay(),
                rental.getStartTime(),
                LocalDateTime.now(), // Assuming actual return time is now for completion
                rental.getExtraFee(),
                totalAmount,
                extraFees
        );
        return completion;
    }

    public static List<ExtraFeeType> getAllExtraFeeTypes() {
        return ExtraFeeTypeDAL.getAllExtraFeeTypes();
    }

    public static boolean addRentalExtraFee(RentalExtraFee rentalExtraFee) {
        boolean result = RentalExtraFeeDAL.add(rentalExtraFee);

        // After adding extra fee, we should also update the total extra fee in Rental table
        if (result) {
            Rental rental = RentalDAL.getRental(rentalExtraFee.getRentalId());
            if (rental != null) {
                rental.setExtraFee(rental.getExtraFee() + rentalExtraFee.getAmount());
                RentalDAL.update(rental);
            }
        }
        return result;
    }

    public static boolean completeRental(int rentalId, LocalDateTime actualReturnTime, int totalAmount) {
        Rental rental = RentalDAL.getRental(rentalId);
        if (rental == null) {
            return false;
        }
        rental.setActualReturnTime(actualReturnTime);
        rental.setTotalAmount(totalAmount);
        rental.setStatus("COMPLETED");

        // Update vehicle status to AVAILABLE
        Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus("AVAILABLE");
            VehicleDAL.update(vehicle);
        }
        return RentalDAL.update(rental);
    }

    public static boolean startRental(int rentalId) {
        Rental rental = RentalDAL.getRental(rentalId);
        if (rental == null) {
            return false;
        }
        rental.setStatus("ACTIVE");

        // Update vehicle status to RENTED
        Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus("RENTED");
            VehicleDAL.update(vehicle);
        }
        return RentalDAL.update(rental);
    }

    public static boolean cancelRental(int rentalId) {
        Rental rental = RentalDAL.getRental(rentalId);
        if (rental == null) {
            return false;
        }
        rental.setStatus("CANCELED");

        // Update vehicle status to AVAILABLE
        Vehicle vehicle = VehicleDAL.getVehicle(rental.getVehicleId());
        if (vehicle != null) {
            vehicle.setStatus("AVAILABLE");
            VehicleDAL.update(vehicle);
        }
        return RentalDAL.update(rental);
    }


}
