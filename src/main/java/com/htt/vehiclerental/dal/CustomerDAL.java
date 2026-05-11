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
        String sql = "UPDATE customer SET fullName = ?, phoneNumber = ?, address = ?, createdAt = ? WHERE identityNumber = ?";
        return DBHelper.getInstance().executeUpdate(sql, customer.getFullName(), customer.getPhoneNumber(), customer.getAddress(), customer.getCreatedAt(), customer.getIdentityNumber()) > 0;
    }

    public static boolean delete(String customerId) {
        String sql = "DELETE FROM customer WHERE identityNumber = ?";
        return DBHelper.getInstance().executeUpdate(sql, customerId) > 0;
    }

    public static Customer getCustomer(String customerId) {
        String sql = "SELECT * FROM customer WHERE identityNumber = ?";
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

    public static List<Customer> searchCustomers(String keyword) {
        String sql = "SELECT * FROM customer WHERE fullName LIKE ? OR phoneNumber LIKE ? OR address LIKE ?";
        var results = DBHelper.getInstance().executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");

        List<Customer> customers = new ArrayList<>();
        for (var result : results) {
            customers.add(Customer.fromMap(result));
        }
        return customers;
    }    
}
