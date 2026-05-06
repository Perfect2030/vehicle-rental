package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManageFeesPanel extends JPanel {
    public ManageFeesPanel() {
    initComponents();
    }

    private void initComponents() {
    setBackground(UiKit.APP_BACKGROUND);
    setLayout(new BorderLayout(0, 18));

    add(UiKit.createInfoBanner(
        "Quản lý phí phát sinh",
        "Thiết lập loại phí và ghi nhận chi phí bổ sung trong từng đơn thuê.",
        UiKit.DANGER), BorderLayout.NORTH);

    JPanel metrics = new JPanel(new GridLayout(1, 3, 16, 16));
    metrics.setOpaque(false);
    metrics.add(UiKit.createMetricCard("Loại phí", "08", "Danh mục phí phát sinh", UiKit.PRIMARY));
    metrics.add(UiKit.createMetricCard("Phí đã ghi nhận", "41", "Theo từng đơn thuê", UiKit.WARNING));
    metrics.add(UiKit.createMetricCard("Doanh thu phí", "9.8M", "Tổng giá trị phát sinh", UiKit.SUCCESS));

    JTabbedPane tabs = new JTabbedPane();
    tabs.setFont(UiKit.BODY_FONT);
    tabs.addTab("Loại phí", createFeeTypeTab());
    tabs.addTab("Phí theo đơn thuê", createRentalFeeTab());

        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setOpaque(false);
        content.add(metrics, BorderLayout.NORTH);
        content.add(tabs, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    private JPanel createFeeTypeTab() {
    JPanel panel = new JPanel(new BorderLayout(0, 16));
    panel.setBackground(UiKit.APP_BACKGROUND);

    JPanel formCard = UiKit.createSurfacePanel();
    formCard.setLayout(new BorderLayout(0, 14));
    formCard.add(UiKit.createSectionHeader(
        "Thiết lập loại phí",
        "Mỗi loại phí đại diện cho một tình huống phát sinh trong quá trình thuê."), BorderLayout.NORTH);

    JPanel formGrid = new JPanel(new GridLayout(0, 2, 12, 12));
    formGrid.setOpaque(false);

    JTextField nameField = UiKit.createTextField(12);
    JTextField amountField = UiKit.createTextField(12);

    formGrid.add(UiKit.createFieldBlock("Tên phí", nameField));
    formGrid.add(UiKit.createFieldBlock("Số tiền", amountField));
    formGrid.add(UiKit.createFieldBlock("Mô tả", UiKit.createTextArea(3, 12)));
    formGrid.add(UiKit.createFieldBlock("Trạng thái", UiKit.createComboBox("ACTIVE", "INACTIVE")));

    formCard.add(formGrid, BorderLayout.CENTER);
    formCard.add(UiKit.createButtonRow(
        UiKit.createPrimaryButton("Thêm loại phí"),
        UiKit.createSecondaryButton("Cập nhật"),
        UiKit.createGhostButton("Làm mới")), BorderLayout.SOUTH);

    JPanel tableCard = UiKit.createSurfacePanel();
    tableCard.setLayout(new BorderLayout(0, 14));
    tableCard.add(UiKit.createSectionHeader(
        "Danh sách loại phí",
        "Dùng để chọn nhanh khi gán phí cho đơn thuê."), BorderLayout.NORTH);

    JTable table = UiKit.createTable(
        new String[] {"ID", "Tên phí", "Số tiền", "Mô tả"},
        new Object[][] {
            {"1", "Phí trễ hạn", "150.000", "Áp dụng khi trả trễ"},
            {"2", "Phí xăng", "80.000", "Đổ bù nhiên liệu"},
            {"3", "Phí vệ sinh", "60.000", "Dọn xe trước khi bàn giao"}
        });

    tableCard.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

    panel.add(formCard, BorderLayout.NORTH);
    panel.add(tableCard, BorderLayout.CENTER);
    return panel;
    }

    private JPanel createRentalFeeTab() {
    JPanel panel = new JPanel(new BorderLayout(0, 16));
    panel.setBackground(UiKit.APP_BACKGROUND);

    JPanel formCard = UiKit.createSurfacePanel();
    formCard.setLayout(new BorderLayout(0, 14));
    formCard.add(UiKit.createSectionHeader(
        "Ghi nhận phí theo đơn thuê",
        "Nhập mã đơn thuê, loại phí và số tiền để theo dõi chi phí bổ sung."), BorderLayout.NORTH);

    JPanel formGrid = new JPanel(new GridLayout(0, 2, 12, 12));
    formGrid.setOpaque(false);

    JTextField rentalIdField = UiKit.createTextField(12);
    JTextField typeIdField = UiKit.createTextField(12);
    JTextField amountField = UiKit.createTextField(12);

    formGrid.add(UiKit.createFieldBlock("Mã thuê", rentalIdField));
    formGrid.add(UiKit.createFieldBlock("Mã loại phí", typeIdField));
    formGrid.add(UiKit.createFieldBlock("Số tiền", amountField));
    formGrid.add(UiKit.createFieldBlock("Mô tả", UiKit.createTextArea(3, 12)));

    formCard.add(formGrid, BorderLayout.CENTER);
    formCard.add(UiKit.createButtonRow(
        UiKit.createPrimaryButton("Ghi phí"),
        UiKit.createSecondaryButton("Cập nhật"),
        UiKit.createGhostButton("Xóa")), BorderLayout.SOUTH);

    JPanel tableCard = UiKit.createSurfacePanel();
    tableCard.setLayout(new BorderLayout(0, 14));
    tableCard.add(UiKit.createSectionHeader(
        "Chi phí phát sinh",
        "Bảng mẫu giúp bạn hình dung cách trình bày chi phí theo hợp đồng."), BorderLayout.NORTH);

    JTable table = UiKit.createTable(
        new String[] {"ID", "Mã thuê", "Loại phí", "Số tiền", "Mô tả"},
        new Object[][] {
            {"1", "R-001", "Phí trễ hạn", "150.000", "Trả trễ 1 ngày"},
            {"2", "R-002", "Phí vệ sinh", "60.000", "Xe cần làm sạch"},
            {"3", "R-003", "Phí xăng", "80.000", "Đổ thêm nhiên liệu"}
        });

    tableCard.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

    panel.add(formCard, BorderLayout.NORTH);
    panel.add(tableCard, BorderLayout.CENTER);
    return panel;
    }
}
