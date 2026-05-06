package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManageCustomersPanel extends JPanel {
    public ManageCustomersPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 18));

        add(UiKit.createInfoBanner(
                "Quản lý khách hàng",
                "Lưu và tra cứu thông tin khách thuê theo số định danh, số điện thoại và địa chỉ.",
                UiKit.INFO), BorderLayout.NORTH);

        JPanel metrics = new JPanel(new GridLayout(1, 3, 16, 16));
        metrics.setOpaque(false);
        metrics.add(UiKit.createMetricCard("Khách hàng", "128", "Tổng số hồ sơ đang có", UiKit.PRIMARY));
        metrics.add(UiKit.createMetricCard("Khách hàng mới", "14", "Phát sinh trong tháng", UiKit.SUCCESS));
        metrics.add(UiKit.createMetricCard("Đang thuê", "22", "Khách có hợp đồng mở", UiKit.WARNING));

        JPanel body = new JPanel(new GridLayout(1, 2, 16, 16));
        body.setOpaque(false);

        JPanel formCard = UiKit.createSurfacePanel();
        formCard.setLayout(new BorderLayout(0, 14));
        formCard.add(UiKit.createSectionHeader(
                "Thông tin khách hàng",
                "Quản lý hồ sơ cá nhân để hỗ trợ đặt xe và đối soát hợp đồng."), BorderLayout.NORTH);

        JPanel formGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        formGrid.setOpaque(false);

        JTextField identityField = UiKit.createTextField(12);
        JTextField fullNameField = UiKit.createTextField(12);
        JTextField phoneField = UiKit.createTextField(12);
        JTextField addressField = UiKit.createTextField(12);
        JTextField createdAtField = UiKit.createTextField(12);
        createdAtField.setText("2026-05-06 08:30");
        createdAtField.setEditable(false);

        formGrid.add(UiKit.createFieldBlock("Số định danh", identityField));
        formGrid.add(UiKit.createFieldBlock("Họ và tên", fullNameField));
        formGrid.add(UiKit.createFieldBlock("Số điện thoại", phoneField));
        formGrid.add(UiKit.createFieldBlock("Địa chỉ", addressField));
        formGrid.add(UiKit.createFieldBlock("Ngày tạo", createdAtField));
        formGrid.add(UiKit.createFieldBlock("Ghi chú", UiKit.createTextArea(3, 12)));

        formCard.add(formGrid, BorderLayout.CENTER);
        formCard.add(UiKit.createButtonRow(
                UiKit.createPrimaryButton("Thêm khách"),
                UiKit.createSecondaryButton("Cập nhật"),
                UiKit.createGhostButton("Làm mới")), BorderLayout.SOUTH);

        JPanel tableCard = UiKit.createSurfacePanel();
        tableCard.setLayout(new BorderLayout(0, 14));
        tableCard.add(UiKit.createSectionHeader(
                "Danh sách khách hàng",
                "Bảng dữ liệu mẫu để bạn thay bằng dữ liệu từ cơ sở dữ liệu."), BorderLayout.NORTH);

        JPanel filterRow = new JPanel(new GridLayout(1, 2, 12, 12));
        filterRow.setOpaque(false);
        filterRow.add(UiKit.createFieldBlock("Tìm kiếm", UiKit.createTextField(12)));
        filterRow.add(UiKit.createFieldBlock("Sắp xếp", UiKit.createComboBox("Mới nhất", "A -> Z", "Nhiều hợp đồng nhất")));

        JTable table = UiKit.createTable(
                new String[] {"Số định danh", "Họ và tên", "SĐT", "Địa chỉ"},
                new Object[][] {
                        {"012345678901", "Nguyễn Minh Anh", "0909123456", "Quận 1, TP.HCM"},
                        {"079201234567", "Trần Quốc Bảo", "0918333444", "Thủ Đức, TP.HCM"},
                        {"036789123456", "Lê Hoàng Yến", "0933555777", "Biên Hòa, Đồng Nai"},
                        {"027654321098", "Phạm Gia Huy", "0988111222", "Bình Dương"}
                });

        JPanel filterAndTable = new JPanel(new BorderLayout(0, 14));
        filterAndTable.setOpaque(false);
        filterAndTable.add(filterRow, BorderLayout.NORTH);
        filterAndTable.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

        tableCard.add(filterAndTable, BorderLayout.CENTER);

        body.add(formCard);
        body.add(tableCard);

        add(metrics, BorderLayout.CENTER);
        add(body, BorderLayout.SOUTH);
    }
}