package com.htt.vehiclerental.view;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class RentalDetailDialog extends JDialog{
    private JTextField 
     rentalId,
     customerId, 
     customerName, 
     vehiclePlate, 
     rentalDate, 
     expectedReturnDate,
     actualReturnTime,
     pricePerDay,
     estimatedTotal, // giá dự kiến (tính theo ngày thuê * giá thuê xe)
     totalAmount, // tổng tiền phải trả (có thể bao gồm phí phát sinh)
     extraFees; // tổng phí phát sinh (nếu có)
    public RentalDetailDialog(Object rentalId) {
        setTitle("Rental Details");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        //
        JPanel body = UiKit.createSurfacePanel();

        body.setLayout(new GridLayout(2, 1, 16, 16));
        JPanel panel = new JPanel(new GridLayout(3, 4, 16, 16));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        rentalId = UiKit.createTextField(15);
        rentalId.setText("1");
        vehiclePlate = UiKit.createTextField(15);
        vehiclePlate.setText("30A-12345");
        customerId = UiKit.createTextField(15);
        customerId.setText("123456789");
        customerName = UiKit.createTextField(15);
        customerName.setText("Nguyen Van A");
        rentalDate = UiKit.createTextField(15);
        rentalDate.setText("2024-06-01");
        expectedReturnDate = UiKit.createTextField(15);
        expectedReturnDate.setText("2024-06-10");
        actualReturnTime = UiKit.createTextField(15);
        actualReturnTime.setText("2024-06-10 10:00");
        pricePerDay = UiKit.createTextField(15);
        pricePerDay.setText("500.000 VND");
        estimatedTotal = UiKit.createTextField(15);
        estimatedTotal.setText("5.000.000 VND");
        totalAmount = UiKit.createTextField(15);
        totalAmount.setText("5.000.000 VND");
        extraFees = UiKit.createTextField(15);
        extraFees.setText("0 VND");

        panel.add(UiKit.createFieldBlock("Mã thuê xe", rentalId));
        panel.add(UiKit.createFieldBlock("Biển số xe", vehiclePlate));
        panel.add(UiKit.createFieldBlock("Mã khách hàng", customerId));
        panel.add(UiKit.createFieldBlock("Tên khách hàng", customerName));
        panel.add(UiKit.createFieldBlock("Ngày thuê", rentalDate));
        panel.add(UiKit.createFieldBlock("Ngày trả dự kiến", expectedReturnDate));
        panel.add(UiKit.createFieldBlock("Ngày trả thực tế", actualReturnTime));
        panel.add(UiKit.createFieldBlock("Giá thuê/ ngày", pricePerDay));
        panel.add(UiKit.createFieldBlock("Tổng tiền dự kiến", estimatedTotal));
        panel.add(UiKit.createFieldBlock("Tổng tiền phải trả", totalAmount));
        panel.add(UiKit.createFieldBlock("Tổng phí phát sinh", extraFees));

        // add rental_extrafee table
        JTable extraFeeTable = UiKit.createTable(
            new String[] {"Mã phí", "Tên phí", "Giá phạt", "Số lượng"},
            new Object[][] {
                {"P001", "Phí trễ hạn", "500.000 VND/ngày", "1"},
                {"P002", "Phí hủy đặt trước", "1.000.000 VND", "1"},
                {"P003", "Phí thiệt hại", "Tùy theo mức độ thiệt hại", "1"},
                {"P004", "Phí nhiên liệu", "Tùy theo lượng nhiên liệu thiếu hụt", "1"}
            }
        );

        body.add(panel);
        body.add(UiKit.createTableScrollPane(extraFeeTable));
        add(body, BorderLayout.CENTER);

        // add action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton closeButton = UiKit.createSecondaryButton("Đóng");
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);


        add(buttonPanel, BorderLayout.SOUTH);
    }
}
