package com.htt.vehiclerental.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDateTime;

import com.htt.vehiclerental.bll.CustomerBLL;
import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.Customer;
import com.htt.vehiclerental.dto.Vehicle;

import com.toedter.calendar.JDateChooser;

public class NewRentalDialog extends JDialog {
    private JTextField licensePlateField, brandField, modelField, typeField, displacementField, pricePerDayField;
    private JTextField identityNumberField, fullNameField, phoneNumberField, addressField;
    private JDateChooser startTime, expectedReturnTime;
    private JTextField depositField, totalAmountField;

    public NewRentalDialog(String title, String licensePlate) {
        this.setTitle("Tạo hợp đồng thuê xe");
        this.setSize(930, 660);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        initComponents(title, licensePlate);
    }

    public void initComponents(String title, String licensePlate) {

        // center
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

        // center header
        JPanel centerHeader = UiKit.createSectionHeader(title, "");

        // center form
        JPanel centerForm = new JPanel(new GridLayout(6, 3, 16, 16));
        centerForm.setOpaque(false);

        //
        licensePlateField = UiKit.createTextField(12); licensePlateField.setEditable(false);
        brandField = UiKit.createTextField(12); brandField.setEditable(false);
        modelField = UiKit.createTextField(12); modelField.setEditable(false);
        typeField = UiKit.createTextField(12); typeField.setEditable(false);
        displacementField = UiKit.createTextField(12); displacementField.setEditable(false);
        pricePerDayField = UiKit.createTextField(12); pricePerDayField.setEditable(false);

        identityNumberField = UiKit.createTextField(12);
        fullNameField = UiKit.createTextField(12);
        phoneNumberField = UiKit.createTextField(12);
        addressField = UiKit.createTextField(12);

        startTime = new JDateChooser(); startTime.setDateFormatString("dd/MM/yyyy HH:mm");
        expectedReturnTime = new JDateChooser(); expectedReturnTime.setDateFormatString("dd/MM/yyyy HH:mm");

        depositField = UiKit.createTextField(12);
        totalAmountField = UiKit.createTextField(12);
        totalAmountField.setEditable(false);

        Vehicle currVehicle = VehicleBLL.getVehicle(licensePlate);
        if (currVehicle != null) {
            licensePlateField.setText(currVehicle.getLicensePlate());
            brandField.setText(currVehicle.getBrand());
            modelField.setText(currVehicle.getModel());
            typeField.setText(currVehicle.getVehicleType().getDisplayName());
            displacementField.setText(String.valueOf(currVehicle.getDisplacement()));
            pricePerDayField.setText(String.valueOf(currVehicle.getPricePerDay()));
        }

        //xe
        centerForm.add(UiKit.createFieldBlock("Biển số", licensePlateField));
        centerForm.add(UiKit.createFieldBlock("Hãng xe", brandField));
        centerForm.add(UiKit.createFieldBlock("Mẫu xe", modelField));
        centerForm.add(UiKit.createFieldBlock("Loại xe", typeField));
        centerForm.add(UiKit.createFieldBlock("Phân khối", displacementField));
        centerForm.add(UiKit.createFieldBlock("Giá thuê/ngày (VNĐ)", pricePerDayField));

        // khách hàng
        Customer customer;
        centerForm.add(UiKit.createFieldBlock("Số định danh khách hàng", identityNumberField));
        identityNumberField.addFocusListener(new FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String identityNumber = identityNumberField.getText().trim();
                if (!identityNumber.isEmpty()) {
                    FillCustomerInfo(identityNumber);
                } 
            }
        });
        identityNumberField.addActionListener(e -> {
            String identityNumber = identityNumberField.getText().trim();
            if (!identityNumber.isEmpty()) {
                FillCustomerInfo(identityNumber);
            }
        });

        centerForm.add(UiKit.createFieldBlock("Họ và tên khách hàng", fullNameField));
        centerForm.add(UiKit.createFieldBlock("Số điện thoại khách hàng", phoneNumberField));
        centerForm.add(UiKit.createFieldBlock("Địa chỉ khách hàng", addressField)); centerForm.add(new JLabel()); centerForm.add(new JLabel());

        // thông tin thuê xe
        centerForm.add(UiKit.createFieldBlock("Thời gian thuê", startTime));
        centerForm.add(UiKit.createFieldBlock("Thời gian trả dự kiến", expectedReturnTime));
        expectedReturnTime.getDateEditor().addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("date") && startTime.getDate() != null && expectedReturnTime.getDate() != null && 
                   !pricePerDayField.getText().isEmpty() && expectedReturnTime.getDate().after(startTime.getDate())) {
                CalculateTotalAmount();
            }
        });
        centerForm.add(UiKit.createFieldBlock("Tiền đặt cọc (VNĐ)", depositField));
        centerForm.add(UiKit.createFieldBlock("Tổng tiền dự kiến (VNĐ)", totalAmountField));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        buttonPanel.setOpaque(false);

        JButton saveButton = UiKit.createPrimaryButton("Lưu thông tin");
        saveButton.addActionListener(e -> saveInfo(currVehicle));

        JButton cancelButton = UiKit.createSecondaryButton("Hủy");
        cancelButton.addActionListener(e -> cancel());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        center.add(centerHeader, BorderLayout.NORTH);
        center.add(centerForm, BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        this.add(center);
    }

    private void FillCustomerInfo(String identityNumber) {
        Customer customer = CustomerBLL.getCustomer(identityNumber);
        if (customer != null) {
            fullNameField.setText(customer.getFullName());
            phoneNumberField.setText(customer.getPhoneNumber());
            addressField.setText(customer.getAddress());
        }
        else {
            fullNameField.setText("");
            phoneNumberField.setText("");
            addressField.setText("");
        }
    }

    private void CalculateTotalAmount() {
        if (startTime.getDate() != null && expectedReturnTime.getDate() != null 
                && !pricePerDayField.getText().isEmpty() && expectedReturnTime.getDate().after(startTime.getDate())) {
            long diffInMillies = expectedReturnTime.getDate().getTime() - startTime.getDate().getTime();
            long diffInHours = diffInMillies / (1000 * 60 * 60);
            long diffInDays = (diffInMillies / (1000 * 60 * 60 * 24)); // Cộng thêm 1 ngày để tính cả ngày bắt đầu
            double pricePerDay = Double.parseDouble(pricePerDayField.getText());
            long totalAmount = (long) Math.ceil((diffInDays + (diffInHours % 24) / 24.0) * pricePerDay); // Tính tổng tiền dựa trên số ngày và phần ngày lẻ
            totalAmountField.setText(String.valueOf(totalAmount));
        } else {
            totalAmountField.setText("");
        }
    } 

    public void saveInfo(Vehicle vehicle) {
        if (identityNumberField.getText().isEmpty() || fullNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || addressField.getText().isEmpty() ||
            startTime.getDate() == null || expectedReturnTime.getDate() == null || startTime.getDate().after(expectedReturnTime.getDate()) || depositField.getText().isEmpty() || totalAmountField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(CustomerBLL.getCustomer(identityNumberField.getText().trim()) == null) {
            Customer customer = new Customer(identityNumberField.getText().trim(), fullNameField.getText().trim(), phoneNumberField.getText().trim(), addressField.getText().trim(), LocalDateTime.now());
            int result = CustomerBLL.addCustomer(customer);
            if(result != CustomerBLL.SUCCESS) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
            }
        }

        this.dispose();
    }

    public void cancel() {
        this.dispose();
    }
}
