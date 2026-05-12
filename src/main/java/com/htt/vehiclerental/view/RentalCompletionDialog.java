package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RentalCompletionDialog  extends JDialog{
    JTextField 
        vehicleInfo,
        rentalDate,
        actualReturnTime,
        pricePerDay,
        totalAmount,
        extraFees;
    JButton addExtraFeeButton, xacnhan, huy;
    JTable extraFeesTable;
    public RentalCompletionDialog(Object rentalId) {
        setTitle("Rental Completion");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        //body
        JPanel body = UiKit.createSurfacePanel();
        body.setLayout(new GridLayout(2, 1, 16, 16));
        body.setOpaque(false);

        //rental info
        JPanel rentalInfoPanel = new JPanel(new GridLayout(3, 2, 16, 16));

        vehicleInfo = UiKit.createTextField(20);
        vehicleInfo.setText("30A-12345 - Toyota Camry 2020");
        rentalDate = UiKit.createTextField(20);
        rentalDate.setText("2024-06-01");
        actualReturnTime = UiKit.createTextField(20);
        actualReturnTime.setText("2024-06-10 10:00");
        pricePerDay = UiKit.createTextField(20);
        pricePerDay.setText("500.000 VND");
        extraFees = UiKit.createTextField(20);
        extraFees.setText("0 VND"); 
        totalAmount = UiKit.createTextField(20);
        totalAmount.setText("5.000.000 VND");

        rentalInfoPanel.add(UiKit.createFieldBlock("Thông tin xe", vehicleInfo));
        rentalInfoPanel.add(UiKit.createFieldBlock("Ngày thuê", rentalDate));
        rentalInfoPanel.add(UiKit.createFieldBlock("Ngày trả ", actualReturnTime));
        rentalInfoPanel.add(UiKit.createFieldBlock("Giá thuê mỗi ngày", pricePerDay));
        rentalInfoPanel.add(UiKit.createFieldBlock("Phí phụ trợ", extraFees));
        rentalInfoPanel.add(UiKit.createFieldBlock("Tổng số tiền", totalAmount));

        body.add(rentalInfoPanel);

        // add extra fee button
        JPanel extraFeePanel = new JPanel(new BorderLayout());
        addExtraFeeButton = UiKit.createSecondaryButton("Thêm phí phát sinh");
        addExtraFeeButton.addActionListener(e -> {
            AddExtraFeeDialog dialog = new AddExtraFeeDialog(null);
            dialog.setVisible(true);
        });

        // extra fees table
        extraFeesTable = UiKit.createTable(
            new String[] {"Mã phí", "Tên phí", "Giá phạt", "Số lượng"},
            new Object[][] {
                {"P001", "Phí trễ hạn", "500.000 VND/ngày", "1"},
                {"P002", "Phí hủy đặt trước", "1.000.000 VND", "1"},
                {"P003", "Phí thiệt hại", "Tùy theo mức độ thiệt hại", "1"},
                {"P004", "Phí nhiên liệu", "Tùy theo lượng nhiên liệu thiếu hụt", "1"}
            }
        );

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addExtraFeeButton);
        extraFeePanel.add(btnPanel, BorderLayout.NORTH);
        extraFeePanel.add(UiKit.createTableScrollPane(extraFeesTable), BorderLayout.CENTER);



        body.add(extraFeePanel);

        //
        JPanel bottomJPanel = new JPanel();
        xacnhan = UiKit.createPrimaryButton("Xác nhận hoàn thành");
        huy = UiKit.createSecondaryButton("Hủy");
        bottomJPanel.add(xacnhan);
        bottomJPanel.add(huy);

        huy.addActionListener(e -> dispose());

        add(body, BorderLayout.CENTER);
        add(bottomJPanel, BorderLayout.SOUTH);

    }
}
