package com.htt.vehiclerental.bll;

import java.time.LocalDateTime;
import java.util.List;

import com.htt.vehiclerental.dal.CustomerDAL;
import com.htt.vehiclerental.dal.RentalDAL;
import com.htt.vehiclerental.dto.Customer;

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

        if (CustomerDAL.getCustomer(customer.getIdentityNumber()) != null) {
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

        if (CustomerDAL.getCustomer(customer.getIdentityNumber()) == null) {
            return NOT_FOUND; // Customer with this identity number does not exist
        }

        if (!CustomerDAL.update(customer)) {
            return DATABASE_ERROR; // Failed to update customer in the database
        }

        return SUCCESS;
    }

    public static int deleteCustomer(String identityNumber) {
        if (identityNumber == null || identityNumber.isEmpty()) {
            return INVALID_INPUT;
        }

        if (CustomerDAL.getCustomer(identityNumber) == null) {
            return NOT_FOUND; // Customer with this identity number does not exist
        }

        //check if customer has rentals before deleting
        if (!RentalDAL.getRentalsByCustomer(identityNumber).isEmpty()) {
            return RENTAL_EXISTS; // Cannot delete customer with existing rentals
        }

        if (!CustomerDAL.delete(identityNumber)) {
            return DATABASE_ERROR; // Failed to delete customer from the database
        }

        return SUCCESS;
    }

    public static Customer getCustomer(String identityNumber) {
        if (identityNumber == null || identityNumber.isEmpty()) {
            return null;
        }
        return CustomerDAL.getCustomer(identityNumber);
    }

    public static List<Customer> getAllCustomers() {
        return CustomerDAL.getAllCustomers();
    }

    public static List<Customer> searchCustomers(String keyword) {
        if (keyword == null) {
            keyword = "";
        }

        return CustomerDAL.searchCustomers(keyword.trim());
    }
}
