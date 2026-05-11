package com.htt.vehiclerental.dal;

import java.util.ArrayList;
import java.util.List;

import com.htt.vehiclerental.dto.Customer;

public class CustomerDAL {
    public static boolean add(Customer customer) {
        String sql = "INSERT INTO customer (identityNumber, fullName, phoneNumber, address, createdAt) VALUES (?, ?, ?, ?, ?)";
        return DBHelper.getInstance().executeUpdate(sql, customer.getIdentityNumber(), customer.getFullName(), customer.getPhoneNumber(), customer.getAddress(), customer.getCreatedAt()) > 0;
    }

    public static boolean update(Customer customer) {
        String sql = "UPDATE customer SET fullName = ?, phoneNumber = ?, address = ?, createdAt = ? WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, customer.getFullName(), customer.getPhoneNumber(), customer.getAddress(), customer.getCreatedAt(), customer.getIdentityNumber()) > 0;
    }

    // public static boolean delete(int customerId) {
    //     String sql = "UPDATE customer SET isDeleted = 1 WHERE id = ?";
    //     return DBHelper.getInstance().executeUpdate(sql, customerId) > 0;
    // }

    public static Customer getCustomer(int customerId) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, customerId);

        if (result.isEmpty()) return null;

        return Customer.fromMap(result.get(0));
    }

    public static List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        var results = DBHelper.getInstance().executeQuery(sql);

        List<Customer> customers = new ArrayList<>();
        for (var result : results) {
            customers.add(Customer.fromMap(result));
        }
        return customers;
    }

    public static List<Customer> searchCustomers(String fullName, String phoneNumber) {
        String sql = "SELECT * FROM customer WHERE (fullName LIKE ? AND phoneNumber LIKE ?)";
        var results = DBHelper.getInstance().executeQuery(sql, "%" + fullName + "%", "%" + phoneNumber + "%");

        List<Customer> customers = new ArrayList<>();
        for (var result : results) {
            customers.add(Customer.fromMap(result));
        }
        return customers;
    }    
}
