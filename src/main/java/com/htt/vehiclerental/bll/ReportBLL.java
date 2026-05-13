package com.htt.vehiclerental.bll;

import java.time.LocalDateTime;
import java.util.List;

import com.htt.vehiclerental.dal.RentalDAL;
import com.htt.vehiclerental.dto.Rental;
import com.htt.vehiclerental.dto.Report;

public class ReportBLL {
    public static Report generateReport(LocalDateTime start, LocalDateTime end) {
        List<Rental> rentals = RentalDAL.getRentalsByTime(start, end);

        int totalRevenue = rentals.stream().mapToInt(Rental::getTotalAmount).sum();
        int totalFees = rentals.stream().mapToInt(Rental::getExtraFee).sum();
        int totalRentals = rentals.size();

        Report report = new Report();
        report.setTotalRevenue(totalRevenue);
        report.setTotalFees(totalFees);
        report.setTotalRentals(totalRentals);
        
        return report;
    }
}
