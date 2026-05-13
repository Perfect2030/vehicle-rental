package com.htt.vehiclerental.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Calendar;
import java.time.Duration;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.bll.CustomerBLL;
import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.Customer;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.dto.CreateRental;

import com.toedter.calendar.JDateChooser;

public class CreateRentalDialog extends JDialog {
    private JTextField licensePlateField, brandField, modelField, typeField, displacementField, pricePerDayField;
    private JTextField identityNumberField, fullNameField, phoneNumberField, addressField;
    private JDateChooser startDay, expectedReturnDay;
    private JSpinner startHour, expectedReturnHour;
    private JTextField depositField, totalAmountField;

    public CreateRentalDialog(String title, String licensePlate) {
        this.setTitle("Tạo hợp đồng thuê xe");
        this.setSize(1080, 580);
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
        JPanel centerForm = new JPanel(new GridLayout(5, 4, 16, 16));
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

        startDay = new JDateChooser(); startDay.setDateFormatString("dd/MM/yyyy");
        startHour = new JSpinner(new SpinnerDateModel());
        startHour.setEditor(new JSpinner.DateEditor(startHour, "HH:mm"));
        expectedReturnDay = new JDateChooser(); expectedReturnDay.setDateFormatString("dd/MM/yyyy");
        expectedReturnHour = new JSpinner(new SpinnerDateModel());
        expectedReturnHour.setEditor(new JSpinner.DateEditor(expectedReturnHour, "HH:mm"));

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
        centerForm.add(UiKit.createFieldBlock("Giá thuê/ngày (VNĐ)", pricePerDayField)); centerForm.add(new JLabel()); centerForm.add(new JLabel());

        // khách hàng
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
        centerForm.add(UiKit.createFieldBlock("Địa chỉ khách hàng", addressField)); 

        // thông tin thuê xe
        centerForm.add(UiKit.createFieldBlock("Ngày thuê", startDay));
        centerForm.add(UiKit.createFieldBlock("Giờ thuê", startHour));
        centerForm.add(UiKit.createFieldBlock("Ngày trả dự kiến", expectedReturnDay));
        centerForm.add(UiKit.createFieldBlock("Giờ trả dự kiến", expectedReturnHour));
        startDay.getDateEditor().addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("date") && startDay.getDate() != null && expectedReturnDay.getDate() != null && 
                   !pricePerDayField.getText().isEmpty() && startHour.getValue() != null && expectedReturnHour.getValue() != null) {
                CalculateTotalAmount();
            }
        });
        startHour.addChangeListener(e -> {
            if (startDay.getDate() != null && expectedReturnDay.getDate() != null && 
                   !pricePerDayField.getText().isEmpty() && startHour.getValue() != null && expectedReturnHour.getValue() != null) {
                CalculateTotalAmount();
            }
        });
        expectedReturnDay.getDateEditor().addPropertyChangeListener(e -> {
            if (e.getPropertyName().equals("date") && startDay.getDate() != null && expectedReturnDay.getDate() != null && 
                   !pricePerDayField.getText().isEmpty() && startHour.getValue() != null && expectedReturnHour.getValue() != null) {
                CalculateTotalAmount();
            }
        });
        expectedReturnHour.addChangeListener(e -> {
            if (startDay.getDate() != null && expectedReturnDay.getDate() != null && 
                   !pricePerDayField.getText().isEmpty() && startHour.getValue() != null && expectedReturnHour.getValue() != null) {
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

    private LocalDateTime getLocalDateTime(JDateChooser dateChooser, JSpinner timeSpinner) {
        Date date = dateChooser.getDate();
        Date time = (Date) timeSpinner.getValue();

        if (date == null || time == null) return null;

        // Sử dụng Calendar để kết hợp ngày và giờ
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);

        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);

        // Lấy các thông số thời gian
        int year = dateCal.get(Calendar.YEAR);
        int month = dateCal.get(Calendar.MONTH) + 1; // Calendar month bắt đầu từ 0
        int day = dateCal.get(Calendar.DAY_OF_MONTH);
        int hour = timeCal.get(Calendar.HOUR_OF_DAY);
        int minute = timeCal.get(Calendar.MINUTE);

        return LocalDateTime.of(year, month, day, hour, minute);
    }

    private void CalculateTotalAmount() {
        LocalDateTime startDateTime = getLocalDateTime(startDay, startHour);
        LocalDateTime expectedReturnDateTime = getLocalDateTime(expectedReturnDay, expectedReturnHour);

        if (startDateTime != null && expectedReturnDateTime != null && !pricePerDayField.getText().isEmpty() && expectedReturnDateTime.isAfter(startDateTime)) {
            int totalAmount = (int) ((Duration.between(startDateTime, expectedReturnDateTime).toMinutes() / 1440.0) * Integer.parseInt(pricePerDayField.getText().trim()));
            totalAmountField.setText(String.valueOf(totalAmount));
            
        } else {
            totalAmountField.setText("");
        }
    } 

    public void saveInfo(Vehicle vehicle) {
        if (identityNumberField.getText().isEmpty() || fullNameField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || addressField.getText().isEmpty() ||
            startDay.getDate() == null || expectedReturnDay.getDate() == null || startDay.getDate().after(expectedReturnDay.getDate()) || depositField.getText().isEmpty() || totalAmountField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = CustomerBLL.getCustomer(identityNumberField.getText().trim());        
        if(customer == null) {
            customer = new Customer(identityNumberField.getText().trim(), fullNameField.getText().trim(), phoneNumberField.getText().trim(), addressField.getText().trim(), LocalDateTime.now());
            int result = CustomerBLL.addCustomer(customer);
            if(result != CustomerBLL.SUCCESS) {
                        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi, vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
            }
        }

        CreateRental rental = new CreateRental();
        rental.setCustomerId(customer.getIdentityNumber());
        rental.setVehicleId(vehicle.getId());
        rental.setStartTime(getLocalDateTime(startDay, startHour));
        rental.setExpectedReturnTime(getLocalDateTime(expectedReturnDay, expectedReturnHour));
        rental.setPricePerDay(Integer.parseInt(pricePerDayField.getText().trim()));
        rental.setDeposit(Integer.parseInt(depositField.getText().trim()));
        rental.setTotalAmount(Integer.parseInt(totalAmountField.getText().trim()));
        
       switch (RentalBLL.addRental(rental)) {
        case RentalBLL.SUCCESS:
            JOptionPane.showMessageDialog(this, "Thêm hợp đồng thuê thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            break;
        case RentalBLL.RENTAL_EXISTS:
            JOptionPane.showMessageDialog(this, "Xe đã có người thuê vào khoảng thời gian đó. Vui lòng chọn xe khác hoặc điều chỉnh thời gian thuê.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            break;
        case RentalBLL.DATABASE_ERROR:
            JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            break;
        case RentalBLL.NOT_FOUND_VEHICLE_OR_CUSTOMER:
            JOptionPane.showMessageDialog(this, "Không tìm thấy xe hoặc khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            break;
        default:
            break;
       }
       return;
    }

    public void cancel() {
        this.dispose();
    }
}
