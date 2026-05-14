package com.htt.vehiclerental.bll;

import java.time.LocalDateTime;
import java.util.List;

import com.htt.vehiclerental.dal.CustomerDAL;
import com.htt.vehiclerental.dal.RentalDAL;
import com.htt.vehiclerental.dto.Customer;
import com.htt.vehiclerental.dto.CustomerDetail;

public class CustomerBLL {

    public static final int SUCCESS = 1;
    public static final int NOT_FOUND = -1;
    public static final int RENTAL_EXISTS = -2;
    public static final int DATABASE_ERROR = -3;
    public static final int INVALID_INPUT = -4;
    public static final int CUSTOMER_EXISTS = -5;

    public static int addCustomer(Customer customer) {
        if (customer.getIdentityNumber() == null || customer.getIdentityNumber().isEmpty() ||
            customer.getFullName() == null || customer.getFullName().isEmpty() ||
            customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty() ||
            customer.getAddress() == null || customer.getAddress().isEmpty()) {
            return INVALID_INPUT;
        }

        if (CustomerDAL.isCustomerExists(customer.getIdentityNumber())) {
            return CUSTOMER_EXISTS; // Customer with this identity number already exists
        }

        customer.setCreatedAt(LocalDateTime.now());

        if (!CustomerDAL.add(customer)) {
            return DATABASE_ERROR; // Failed to add customer to the database
        }

        return SUCCESS;
    }

    public static int updateCustomer(Customer customer) {
        if (customer.getIdentityNumber() == null || customer.getIdentityNumber().isEmpty() ||
            customer.getFullName() == null || customer.getFullName().isEmpty() ||
            customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty() ||
            customer.getAddress() == null || customer.getAddress().isEmpty()) {
            return INVALID_INPUT;
        }

        if (CustomerDAL.getCustomer(customer.getId()) == null) {
            return NOT_FOUND; // Customer with this identity number does not exist
        }

        if (!CustomerDAL.update(customer)) {
            return DATABASE_ERROR; // Failed to update customer in the database
        }

        return SUCCESS;
    }

    public static int deleteCustomer(int customerId) {
        if (customerId <= 0) {
            return INVALID_INPUT;
        }

        if (CustomerDAL.getCustomer(customerId) == null) {
            return NOT_FOUND; // Customer with this identity number does not exist
        }

        //check if customer has rentals before deleting
        if (!RentalDAL.getRentalsByCustomer(customerId).isEmpty()) {
            
            if (!CustomerDAL.softDelete(customerId)) {
                return DATABASE_ERROR; // Failed to soft delete customer from the database
            }

            return SUCCESS;
        }

        if (!CustomerDAL.hardDelete(customerId)) {
            return DATABASE_ERROR; // Failed to delete customer from the database
        }

        return SUCCESS;
    }

    public static Customer getCustomer(int customerId) {
        if (customerId <= 0) {
            return null;
        }
        return CustomerDAL.getCustomer(customerId);
    }

    public static Customer getCustomer(String identityNumber) {
        if (identityNumber == null || identityNumber.isEmpty()) {
            return null;
        }
        return CustomerDAL.getCustomer(identityNumber);
    }

    public static CustomerDetail getCustomerDetail(int customerId) {
        Customer customer = getCustomer(customerId);
        if (customer == null) {
            return null;
        }

        int totalRentals = RentalDAL.getRentalsByCustomer(customerId).size();

        return new CustomerDetail(customer, totalRentals);
    }

    public static List<Customer> getAllCustomers() {
        return CustomerDAL.getAllCustomers();
    }

    public static List<Customer> searchCustomers(String keyword, int sortOption) {
        if (keyword == null) {
            keyword = "";
        }

        return CustomerDAL.searchCustomers(keyword.trim(), sortOption);
    }

    public static int getCustomerCount() {
        return CustomerDAL.getCustomerCount();
    }

    public static int getRentingCustomerCount() {
        return RentalDAL.getRentingCustomerCount();
    }
}
