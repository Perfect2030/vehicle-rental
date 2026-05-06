package com.htt.vehiclerental.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
    private static DBHelper instance;
    private String connectionString = "jdbc:mysql://localhost:3306/vehicle_rental";
    private String username = "root";
    private String password = "";
    
    public DBHelper() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print("Database driver not found! \nMessage: " + e.getMessage());
        }
    }

    public static DBHelper getInstance() {
        if (instance == null) instance = new DBHelper();
        return instance;
    }

    public int executeUpdate(String sql, Object... params) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stm = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++)
                if (params[i] == null) stm.setNull(i + 1, Types.NULL);
                else stm.setObject(i + 1, params[i]);

            int affectedRows = stm.executeUpdate();
            stm.close();
            connection.close();
            return affectedRows;
        } catch (SQLException e) {
            System.out.print("Database error! \nMessage: " + e.getMessage());
        }
        return -1;
    }

    public int executeUpdateAndGetId(String sql, Object... params) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < params.length; i++)
                if (params[i] == null) stm.setNull(i + 1, Types.NULL);
                else stm.setObject(i + 1, params[i]);

            int affectedRows = stm.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating record failed, no rows affected.");
            }

            ResultSet generatedKeys = stm.getGeneratedKeys();
            int id = -1;
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating record failed, no ID obtained.");
            }

            stm.close();
            connection.close();
            return id;
        } catch (SQLException e) {
            System.out.print("Database error! \nMessage: " + e.getMessage());
        }

        return -1;
    }

    public List<Map<String, Object>> executeQuery(String sql, Object... params) {
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            PreparedStatement stm = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++)
                stm.setObject(i + 1, params[i]);

            ResultSet rs = stm.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            List<Map<String, Object>> data = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= colCount; i++)
                    row.put(meta.getColumnName(i), rs.getObject(i));

                data.add(row);
            }

            rs.close();
            stm.close();
            connection.close();
            return data;
        } catch (SQLException e) {
            System.out.print("Database error! \nMessage: " + e.getMessage());
        }
        return null;
    }
}
