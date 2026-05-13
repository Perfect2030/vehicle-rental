package com.htt.vehiclerental.view;

import java.awt.*;
import java.time.LocalDateTime;

import javax.swing.*;

import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.UpcomingRentalCustomer;
import com.htt.vehiclerental.dto.VehicleDetail;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;

public class VehicleDetailDialog extends JDialog {
    private static final String[] UPCOMING_RENTALS_CUSTOMER_COLUMNS = {
        "CCCD", "Họ tên", "Số điện thoại", "Ngày thuê dự kiến", "Ngày trả dự kiến"
    };

    JTextField licensePlateField, brandField, modelField, displacementField, pricePerDayField, typeField, statusField;
    private JTable upcomingRentalCustomerTable;

    public VehicleDetailDialog( int id) {
        this.setTitle("Thông tin chi tiết xe");
        this.setSize(1080,640);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        initComponents(this.getTitle(), id);
        loadData(id);
    }

    public void initComponents(String title, int id) {

        // Dialog
        JPanel Dialog = UiKit.createSurfacePanel();
        Dialog.setLayout(new BorderLayout(16, 16));
        Dialog.setBorder(UiKit.createCardBorder());

        // header
        JPanel centerHeader = UiKit.createSectionHeader(title, "");

        // center
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

        //center form
        JPanel centerForm = new JPanel(new GridLayout(2, 4, 16, 16));
        centerForm.setOpaque(false);


        licensePlateField = UiKit.createTextField(12); licensePlateField.setEditable(false);
        brandField = UiKit.createTextField(12); brandField.setEditable(false);
        modelField = UiKit.createTextField(12); modelField.setEditable(false);
        typeField = UiKit.createTextField(12); typeField.setEditable(false);
        displacementField = UiKit.createTextField(12); displacementField.setEditable(false);
        pricePerDayField = UiKit.createTextField(12); pricePerDayField.setEditable(false);
        statusField = UiKit.createTextField(12); statusField.setEditable(false);

        centerForm.add(UiKit.createFieldBlock("Biển số", licensePlateField));
        centerForm.add(UiKit.createFieldBlock("Hãng xe", brandField));
        centerForm.add(UiKit.createFieldBlock("Mẫu xe", modelField));
        centerForm.add(UiKit.createFieldBlock("Loại xe", typeField));
        centerForm.add(UiKit.createFieldBlock("Phân khối", displacementField));
        centerForm.add(UiKit.createFieldBlock("Giá thuê/ngày (VNĐ)", pricePerDayField));
        centerForm.add(UiKit.createFieldBlock("Trạng thái", statusField));

        //center table
        upcomingRentalCustomerTable = UiKit.createTable(UPCOMING_RENTALS_CUSTOMER_COLUMNS, new Object[][] {});

        center.add(centerForm, BorderLayout.NORTH);
        center.add(new JScrollPane(upcomingRentalCustomerTable), BorderLayout.CENTER);

        // button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        buttonPanel.setOpaque(false);

        JButton closeButton = UiKit.createPrimaryButton("Đóng");
        closeButton.addActionListener(e -> cancel());
        buttonPanel.add(closeButton);

        Dialog.add(centerHeader, BorderLayout.NORTH);
        Dialog.add(center, BorderLayout.CENTER);
        Dialog.add(buttonPanel, BorderLayout.SOUTH);

        this.add(Dialog);
    }

    public void loadData(int id) {
        // VehicleDetail vehicleDetail = VehicleBLL.getVehicleDetail(id);
        // if (vehicleDetail != null) {
        //     licensePlateField.setText(vehicleDetail.getLicensePlate());
        //     brandField.setText(vehicleDetail.getBrand());
        //     modelField.setText(vehicleDetail.getModel());
        //     typeField.setText(vehicleDetail.getVehicleType().toString());
        //     displacementField.setText(String.valueOf(vehicleDetail.getDisplacement()));
        //     pricePerDayField.setText(String.valueOf(vehicleDetail.getPricePerDay()));
        //     statusField.setText(vehicleDetail.getStatus().toString());

        //     // Load upcoming rental customers
        //     loadUpcomingRentalCustomers(vehicleDetail.getUpcomingRentalCustomers());
        // }
    }

    public void cancel() {
        this.dispose();
    }
}
