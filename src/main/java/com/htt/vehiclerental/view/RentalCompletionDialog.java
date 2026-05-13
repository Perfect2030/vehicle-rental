package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.dto.RentalCompletion;

public class RentalCompletionDialog  extends JDialog{
    private int rentalId;
    private RentalCompletion data;
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
        this.rentalId = (Integer) rentalId;
        initComponents();
        loadData();
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
        rentalDate = UiKit.createTextField(20);
        actualReturnTime = UiKit.createTextField(20);
        pricePerDay = UiKit.createTextField(20);
        extraFees = UiKit.createTextField(20);
        totalAmount = UiKit.createTextField(20);
        totalAmount.setFont(UiKit.BODY_FONT_BOLD);

        rentalInfoPanel.add(UiKit.createFieldBlock("Thông tin xe", vehicleInfo));
        rentalInfoPanel.add(UiKit.createFieldBlock("Giá thuê mỗi ngày", pricePerDay));
        rentalInfoPanel.add(UiKit.createFieldBlock("Ngày thuê", rentalDate));
        rentalInfoPanel.add(UiKit.createFieldBlock("Ngày trả ", actualReturnTime));
        rentalInfoPanel.add(UiKit.createFieldBlock("Phí phụ trợ", extraFees));
        rentalInfoPanel.add(UiKit.createFieldBlock_Bold("Tổng số tiền", totalAmount));

        body.add(rentalInfoPanel);

        // add extra fee button
        JPanel extraFeePanel = new JPanel(new BorderLayout());
        addExtraFeeButton = UiKit.createSecondaryButton("Thêm phí phát sinh");
        addExtraFeeButton.addActionListener(e -> {
            AddExtraFeeDialog dialog = new AddExtraFeeDialog(rentalId);
            dialog.setVisible(true);
            loadData(); // reload data after adding extra fee
        });

        // extra fees table
        extraFeesTable = UiKit.createTable(
            new String[] {"Mã phí", "Tên phí", "Giá phạt", "Số lượng"},
            new Object[][] {}
        );

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addExtraFeeButton);
        extraFeePanel.add(btnPanel, BorderLayout.NORTH);
        extraFeePanel.add(UiKit.createTableScrollPane(extraFeesTable), BorderLayout.CENTER);



        body.add(extraFeePanel);

        //
        JPanel bottomJPanel = new JPanel();
        xacnhan = UiKit.createPrimaryButton("Xác nhận hoàn thành");
        huy = UiKit.createSecondaryButton("Thoát");
        bottomJPanel.add(xacnhan);
        bottomJPanel.add(huy);


        xacnhan.addActionListener(e -> updateRentalCompletion());
        huy.addActionListener(e -> dispose());

        add(body, BorderLayout.CENTER);
        add(bottomJPanel, BorderLayout.SOUTH);

    }
    private void loadData() {
        data = RentalBLL.getRentalCompletion(rentalId);

        //
        vehicleInfo.setText(data.getVehicleInfo());
        pricePerDay.setText(String.format("%,d VND", data.getPricePerDay()));
        rentalDate.setText(data.getRentalDate().toString());
        actualReturnTime.setText(data.getActualReturnTime().toString());
        extraFees.setText(String.format("%,d VND", data.getExtraFees()));
        totalAmount.setText(String.format("%,d VND", data.getTotalAmount()));

        // load extra fee details into table
        var extraFeeDetails = data.getExtraFeeDetails();
        var tableModel = (DefaultTableModel) extraFeesTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        for (var fee : extraFeeDetails) {
            tableModel.addRow(new Object[]{
                fee.getExtraFeeTypeId(),
                fee.getDescription(),
                fee.getAmount(),
                1
            });
        }
         
    }

    private void updateRentalCompletion() {
        try {
            boolean success = RentalBLL.completeRental(rentalId, data.getActualReturnTime(), data.getTotalAmount());
            if (success) {
                dispose();
                JOptionPane.showMessageDialog(this, "Cập nhật hoàn thành thuê xe thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hoàn thành thuê xe thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tổng số tiền phải là số nguyên hợp lệ", "Lỗi nhập", JOptionPane.WARNING_MESSAGE);
        }
    }
}
