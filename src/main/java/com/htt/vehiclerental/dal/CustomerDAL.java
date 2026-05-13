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
        return DBHelper.getInstance().executeUpdate(sql, customer.getFullName(), customer.getPhoneNumber(), customer.getAddress(), customer.getCreatedAt(), customer.getId()) > 0;
    }

    public static boolean hardDelete(int customerId) {
        String sql = "DELETE FROM customer WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, customerId) > 0;
    }

    public static boolean softDelete(int customerId) {
        String sql = "UPDATE customer SET isDeleted = 1 WHERE id = ?";
        return DBHelper.getInstance().executeUpdate(sql, customerId) > 0;
    }

    public static Customer getCustomer(int customerId) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        var result = DBHelper.getInstance().executeQuery(sql, customerId);

        if (result.isEmpty()) return null;

        return Customer.fromMap(result.get(0));
    }

    public static boolean isCustomerExists(String identityNumber) {
        String sql = "SELECT identityNumber AS count FROM customer WHERE identityNumber = ? AND isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql, identityNumber);

        if (result.isEmpty()) return false;

        return true;
    }

    public static List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer WHERE isDeleted = 0";
        var results = DBHelper.getInstance().executeQuery(sql);

        List<Customer> customers = new ArrayList<>();
        for (var result : results) {
            customers.add(Customer.fromMap(result));
        }
        return customers;
    }

    public static List<Customer> searchCustomers(String keyword, int sortOption) {
        String sql = "SELECT * FROM customer WHERE (fullName LIKE ? OR phoneNumber LIKE ?) AND isDeleted = 0";

        if (sortOption == 1) sql += " ORDER BY fullName ASC";
        else if (sortOption == 2) sql += " ORDER BY fullName DESC";

        var results = DBHelper.getInstance().executeQuery(sql, "%" + keyword + "%", "%" + keyword + "%");

        List<Customer> customers = new ArrayList<>();
        for (var result : results) {
            customers.add(Customer.fromMap(result));
        }
        return customers;
    }    

    public static int getCustomerCount() {
        String sql = "SELECT COUNT(*) AS count FROM customer WHERE isDeleted = 0";
        var result = DBHelper.getInstance().executeQuery(sql);

        if (result.isEmpty()) return 0;

        return ((Long) result.get(0).get("count")).intValue();
    }
}
