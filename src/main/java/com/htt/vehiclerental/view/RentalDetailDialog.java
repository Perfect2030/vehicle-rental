package com.htt.vehiclerental.view;

import javax.swing.*;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.dto.RentalDetail;

import java.awt.*;
import java.util.concurrent.Flow;
import javax.swing.table.DefaultTableModel;

public class RentalDetailDialog extends JDialog{
    private int Id;
    JTable extraFeeTable;
    private JTextField 
     rentalId,

     vehiclePlate, 
     vehicleBrand,
     vehicleModel,

     customerId, 
     customerName,
     customerPhone,
     customerAddress, 
     
     rentalDate, 
     expectedReturnDate,
     actualReturnTime,

     pricePerDay,
     estimatedTotal, // giá dự kiến (tính theo ngày thuê * giá thuê xe)
     totalAmount, // tổng tiền phải trả (có thể bao gồm phí phát sinh)
     extraFees; // tổng phí phát sinh (nếu có)

    public RentalDetailDialog(Object rentalId) {
        this.Id = (int) rentalId;
        setTitle("Rental Details");
        setSize(1200, 850);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
        loadData();
    }

    public void initComponents() {
        setLayout(new BorderLayout());
        //
        JPanel body = UiKit.createSurfacePanel();

        body.setLayout(new GridLayout(2, 1, 16, 16));
        JPanel panel = new JPanel(new GridLayout(4, 4, 16, 16));
        // panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        rentalId = UiKit.createTextField(15);

        vehiclePlate = UiKit.createTextField(15);
        vehicleBrand = UiKit.createTextField(15);
        vehicleModel = UiKit.createTextField(15);

        customerId = UiKit.createTextField(15);
        customerName = UiKit.createTextField(15);
        customerPhone = UiKit.createTextField(15);
        customerAddress = UiKit.createTextField(15);

        rentalDate = UiKit.createTextField(15);
        expectedReturnDate = UiKit.createTextField(15);
        actualReturnTime = UiKit.createTextField(15);
        pricePerDay = UiKit.createTextField(15);
        estimatedTotal = UiKit.createTextField(15);
        totalAmount = UiKit.createTextField(15);
        extraFees = UiKit.createTextField(15);

        //load data
        

        panel.add(UiKit.createFieldBlock("Mã thuê xe", rentalId));

        panel.add(UiKit.createFieldBlock("Biển số xe", vehiclePlate));
        panel.add(UiKit.createFieldBlock("Hãng xe", vehicleBrand));
        panel.add(UiKit.createFieldBlock("Mẫu xe", vehicleModel));

        panel.add(UiKit.createFieldBlock("Mã khách hàng", customerId));
        panel.add(UiKit.createFieldBlock("Tên khách hàng", customerName));
        panel.add(UiKit.createFieldBlock("Số điện thoại", customerPhone));
        panel.add(UiKit.createFieldBlock("Địa chỉ", customerAddress));

        panel.add(UiKit.createFieldBlock("Ngày thuê", rentalDate));
        panel.add(UiKit.createFieldBlock("Ngày trả dự kiến", expectedReturnDate));
        panel.add(UiKit.createFieldBlock("Ngày trả thực tế", actualReturnTime));
        panel.add(UiKit.createFieldBlock("Giá thuê/ ngày", pricePerDay));
        panel.add(UiKit.createFieldBlock("Tổng tiền dự kiến", estimatedTotal));
        panel.add(UiKit.createFieldBlock("Tổng tiền phải trả", totalAmount));
        panel.add(UiKit.createFieldBlock("Tổng phí phát sinh", extraFees));

        // add rental_extrafee table
        extraFeeTable = UiKit.createTable(
            new String[] {"Mã phí", "Tên phí", "Giá phạt", "Số lượng"},
            new Object[][]{

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

    private void loadData() {
        RentalDetail  data =  RentalBLL.getRentalDetail(Id);
        // Set the values of the text fields with the data
        rentalId.setText(String.valueOf(data.getRentalId()));
        vehiclePlate.setText(data.getVehiclePlate());
        vehicleBrand.setText(data.getVehicleBrand());
        vehicleModel.setText(data.getVehicleModel());
        customerId.setText(String.valueOf(data.getCustomerId()));
        customerName.setText(data.getCustomerName());
        customerPhone.setText(data.getCustomerPhone());
        customerAddress.setText(data.getCustomerAddress());
        rentalDate.setText(data.getRentalDate().toString());
        expectedReturnDate.setText(data.getExpectedReturnDate().toString());
        actualReturnTime.setText(data.getActualReturnTime() != null ? data.getActualReturnTime().toString() : "NULL");
        pricePerDay.setText(String.valueOf(data.getPricePerDay()));
        estimatedTotal.setText(String.valueOf(data.getEstimatedTotal()));
        totalAmount.setText(String.valueOf(data.getTotalAmount() == -1 ? "Chưa có" : data.getTotalAmount()));
        extraFees.setText(String.valueOf(data.getExtraFees()));

        // load extra fee details into table
        var extraFeeDetails = data.getExtraFeeDetails();
        var tableModel = (DefaultTableModel) extraFeeTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        for (var fee : extraFeeDetails) {
            tableModel.addRow(new Object[]{
                fee.getId(),
                fee.getDescription(),
                fee.getAmount(),
                1
            });
        }
    }
}
