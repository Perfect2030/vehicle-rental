package com.htt.vehiclerental.view;

import java.awt.*;
import java.time.LocalDateTime;

import javax.swing.*;

import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;

public class VehicleInfoDialog extends JDialog {
    JTextField licensePlateField, brandField, modelField, displacementField, pricePerDayField;
    JComboBox<String> typeField, statusComboBox;

    public VehicleInfoDialog(String title, int id) {
        this.setTitle("Thông tin xe");
        this.setSize(800, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        initComponents(title, id);
    }

    public void initComponents(String title, int id) {

        // center
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

        // center header
        JPanel centerHeader = UiKit.createSectionHeader(title, "");

        // center form
        JPanel centerForm = new JPanel(new GridLayout(4, 2, 16, 16));
        centerForm.setOpaque(false);

        licensePlateField = UiKit.createTextField(12);
        brandField = UiKit.createTextField(12);
        modelField = UiKit.createTextField(12);
        typeField = UiKit.createComboBox(new String[] { "Xe côn", "Xe số", "Xe ga" });
        displacementField = UiKit.createTextField(12);
        pricePerDayField = UiKit.createTextField(12);
        statusComboBox = UiKit
            .createComboBox(new String[] { "Sẵn sàng", "Bảo trì" });

        Vehicle currVehicle = VehicleBLL.getVehicle(id);
        if (currVehicle != null) {
            licensePlateField.setText(currVehicle.getLicensePlate());
            brandField.setText(currVehicle.getBrand());
            modelField.setText(currVehicle.getModel());
            typeField.setSelectedItem(currVehicle.getVehicleType().getDisplayName());
            displacementField.setText(String.valueOf(currVehicle.getDisplacement()));
            pricePerDayField.setText(String.valueOf(currVehicle.getPricePerDay()));
            statusComboBox.setSelectedItem(currVehicle.getStatusEnum().getDisplayName());
        }

        centerForm.add(UiKit.createFieldBlock("Biển số", licensePlateField));
        centerForm.add(UiKit.createFieldBlock("Hãng xe", brandField));
        centerForm.add(UiKit.createFieldBlock("Mẫu xe", modelField));
        centerForm.add(UiKit.createFieldBlock("Loại xe", typeField));
        centerForm.add(UiKit.createFieldBlock("Phân khối", displacementField));
        centerForm.add(UiKit.createFieldBlock("Giá thuê/ngày (VNĐ)", pricePerDayField));
        centerForm.add(UiKit.createFieldBlock("Trạng thái", statusComboBox));

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

    public void saveInfo(Vehicle vehicle) {
        if (licensePlateField.getText().isEmpty() || brandField.getText().isEmpty() || modelField.getText().isEmpty() ||
                displacementField.getText().isEmpty() || pricePerDayField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isUpdate = vehicle != null;

        if (!isUpdate) {
            vehicle = new Vehicle();
        }

        vehicle.setLicensePlate(licensePlateField.getText());
        vehicle.setBrand(brandField.getText());
        vehicle.setModel(modelField.getText());
        vehicle.setVehicleType(typeField.getSelectedItem().toString().equals("Xe côn") ? VehicleType.MANUAL
                : typeField.getSelectedItem().toString().equals("Xe số") ? VehicleType.AUTOMATIC : VehicleType.SCOOTER);
        vehicle.setDisplacement(Integer.parseInt(displacementField.getText()));
        vehicle.setPricePerDay(Integer.parseInt(pricePerDayField.getText()));
        vehicle.setStatusEnum(statusComboBox.getSelectedItem().toString().equals("Sẵn sàng") ? VehicleStatus.AVAILABLE : VehicleStatus.MAINTENANCE);

        if (isUpdate) {
            switch (VehicleBLL.updateVehicle(vehicle)) {
                case VehicleBLL.SUCCESS:
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin xe thành công.", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    break;
                case VehicleBLL.NOT_FOUND:
                    JOptionPane.showMessageDialog(this, "Xe không tồn tại.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case VehicleBLL.RENTAL_EXISTS:
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật trạng thái. Lý do: xe đang được thuê hoặc đang có đơn thuê trong tương lai. Vui lòng hoàn tất giao dịch thuê trước khi cập nhật.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case VehicleBLL.VEHICLE_EXISTS:
                    JOptionPane.showMessageDialog(this, "Xe với biển số này đã tồn tại.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case VehicleBLL.DATABASE_ERROR:
                    JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            
                default:
                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng thử lại sau!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } else {
            vehicle.setCreatedAt(LocalDateTime.now());
            switch (VehicleBLL.addVehicle(vehicle)) {
                case VehicleBLL.SUCCESS:
                    JOptionPane.showMessageDialog(this, "Thêm xe mới thành công.", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    break;
                case VehicleBLL.VEHICLE_EXISTS:
                    JOptionPane.showMessageDialog(this, "Xe với biển số này đã tồn tại.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case VehicleBLL.DATABASE_ERROR:
                    JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu.", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            
                default:
                    JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng thử lại sau!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    public void cancel() {
        this.dispose();
    }
}
