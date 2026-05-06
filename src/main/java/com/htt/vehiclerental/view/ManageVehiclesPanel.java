package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManageVehiclesPanel extends JPanel {
    public ManageVehiclesPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 18));

        add(UiKit.createInfoBanner(
                "Quản lý xe",
                "Nhập thông tin xe, theo dõi trạng thái và lọc danh sách theo loại hoặc tình trạng.",
                UiKit.SUCCESS), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 16));
        center.setOpaque(false);

        JPanel metrics = new JPanel(new GridLayout(1, 3, 16, 16));
        metrics.setOpaque(false);
        metrics.add(UiKit.createMetricCard("Tổng số xe", "36", "Đang quản lý trong hệ thống", UiKit.PRIMARY));
        metrics.add(UiKit.createMetricCard("Đang cho thuê", "21", "Xe đang được khách sử dụng", UiKit.WARNING));
        metrics.add(UiKit.createMetricCard("Bảo trì", "03", "Xe tạm ngưng để kiểm tra", UiKit.DANGER));

        JPanel mainContent = new JPanel(new GridLayout(1, 2, 16, 16));
        mainContent.setOpaque(false);

        JPanel formCard = UiKit.createSurfacePanel();
        formCard.setLayout(new BorderLayout(0, 14));
        formCard.add(UiKit.createSectionHeader(
                "Thông tin xe",
                "Điền các trường chính để tạo hoặc cập nhật hồ sơ xe."), BorderLayout.NORTH);

        JPanel formGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        formGrid.setOpaque(false);

        JTextField plateField = UiKit.createTextField(12);
        JTextField brandField = UiKit.createTextField(12);
        JTextField modelField = UiKit.createTextField(12);
        JComboBox<String> typeBox = UiKit.createComboBox("Xe máy", "Ô tô", "Xe điện", "Xe tải");
        JTextField priceField = UiKit.createTextField(12);
        JComboBox<String> statusBox = UiKit.createComboBox("AVAILABLE", "RENTED", "MAINTENANCE");
        JTextField createdAtField = UiKit.createTextField(12);
        createdAtField.setText("2026-05-06 08:30");
        createdAtField.setEditable(false);

        formGrid.add(UiKit.createFieldBlock("Biển số", plateField));
        formGrid.add(UiKit.createFieldBlock("Hãng xe", brandField));
        formGrid.add(UiKit.createFieldBlock("Mẫu xe", modelField));
        formGrid.add(UiKit.createFieldBlock("Loại xe", typeBox));
        formGrid.add(UiKit.createFieldBlock("Giá thuê/ngày", priceField));
        formGrid.add(UiKit.createFieldBlock("Trạng thái", statusBox));
        formGrid.add(UiKit.createFieldBlock("Ngày tạo", createdAtField));
        formGrid.add(UiKit.createFieldBlock("Ghi chú", UiKit.createTextArea(3, 12)));

        formCard.add(formGrid, BorderLayout.CENTER);
        formCard.add(UiKit.createButtonRow(
                UiKit.createPrimaryButton("Thêm xe"),
                UiKit.createSecondaryButton("Cập nhật"),
                UiKit.createGhostButton("Làm mới")), BorderLayout.SOUTH);

        JPanel tableCard = UiKit.createSurfacePanel();
        tableCard.setLayout(new BorderLayout(0, 14));
        tableCard.add(UiKit.createSectionHeader(
                "Danh sách xe",
                "Bảng dữ liệu mẫu mô phỏng màn hình quản lý thực tế."), BorderLayout.NORTH);

        JPanel filterRow = new JPanel(new GridLayout(1, 3, 12, 12));
        filterRow.setOpaque(false);
        filterRow.add(UiKit.createFieldBlock("Tìm kiếm", UiKit.createTextField(12)));
        filterRow.add(UiKit.createFieldBlock("Loại xe", UiKit.createComboBox("Tất cả", "Xe máy", "Ô tô", "Xe điện", "Xe tải")));
        filterRow.add(UiKit.createFieldBlock("Trạng thái", UiKit.createComboBox("Tất cả", "AVAILABLE", "RENTED", "MAINTENANCE")));

        JTable vehicleTable = UiKit.createTable(
                new String[] {"Biển số", "Hãng", "Mẫu", "Loại", "Giá/ngày", "Trạng thái"},
                new Object[][] {
                        {"59A1-12345", "Honda", "Wave Alpha", "Xe máy", "180.000", "AVAILABLE"},
                        {"51F-98765", "Yamaha", "Exciter", "Xe máy", "250.000", "RENTED"},
                        {"30K-77889", "Toyota", "Vios", "Ô tô", "650.000", "AVAILABLE"},
                        {"43A-11223", "VinFast", "VF e34", "Xe điện", "800.000", "MAINTENANCE"}
                });

        JPanel filterAndTable = new JPanel(new BorderLayout(0, 14));
        filterAndTable.setOpaque(false);
        filterAndTable.add(filterRow, BorderLayout.NORTH);
        filterAndTable.add(UiKit.createTableScrollPane(vehicleTable), BorderLayout.CENTER);

        tableCard.add(filterAndTable, BorderLayout.CENTER);

        mainContent.add(formCard);
        mainContent.add(tableCard);

        center.add(metrics, BorderLayout.NORTH);
        center.add(mainContent, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }
}
