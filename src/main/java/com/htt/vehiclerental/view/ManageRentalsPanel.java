package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ManageRentalsPanel extends JPanel {
    public ManageRentalsPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 18));

        add(UiKit.createInfoBanner(
                "Quản lý thuê xe",
                "Tạo hợp đồng thuê, theo dõi thời gian nhận trả và tính toán tổng tiền.",
                UiKit.WARNING), BorderLayout.NORTH);

        JPanel metrics = new JPanel(new GridLayout(1, 4, 16, 16));
        metrics.setOpaque(false);
        metrics.add(UiKit.createMetricCard("Đang thuê", "27", "Đơn chưa kết thúc", UiKit.INFO));
        metrics.add(UiKit.createMetricCard("Đã trả", "64", "Đơn hoàn tất", UiKit.SUCCESS));
        metrics.add(UiKit.createMetricCard("Quá hạn", "04", "Cần xử lý ngay", UiKit.DANGER));
        metrics.add(UiKit.createMetricCard("Doanh thu", "71.2M", "Tổng giá trị hợp đồng", UiKit.PRIMARY));

        JPanel body = new JPanel(new BorderLayout(0, 16));
        body.setOpaque(false);

        JPanel formCard = UiKit.createSurfacePanel();
        formCard.setLayout(new BorderLayout(0, 14));
        formCard.add(UiKit.createSectionHeader(
                "Thông tin hợp đồng thuê",
                "Màn hình được bố cục theo dạng biểu mẫu để dễ nhập liệu và kiểm tra."), BorderLayout.NORTH);

        JPanel formGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        formGrid.setOpaque(false);

        JTextField rentalIdField = UiKit.createTextField(12);
        rentalIdField.setText("Auto");
        rentalIdField.setEditable(false);
        JTextField customerIdField = UiKit.createTextField(12);
        JTextField vehicleIdField = UiKit.createTextField(12);
        JTextField startTimeField = UiKit.createTextField(12);
        JTextField expectedReturnField = UiKit.createTextField(12);
        JTextField actualReturnField = UiKit.createTextField(12);
        JTextField pricePerDayField = UiKit.createTextField(12);
        JTextField depositField = UiKit.createTextField(12);
        JTextField estimatedTotalField = UiKit.createTextField(12);
        JTextField extraFeeField = UiKit.createTextField(12);
        JTextField totalAmountField = UiKit.createTextField(12);

        formGrid.add(UiKit.createFieldBlock("Mã thuê", rentalIdField));
        formGrid.add(UiKit.createFieldBlock("Mã khách", customerIdField));
        formGrid.add(UiKit.createFieldBlock("Mã xe", vehicleIdField));
        formGrid.add(UiKit.createFieldBlock("Giá thuê/ngày", pricePerDayField));
        formGrid.add(UiKit.createFieldBlock("Tiền cọc", depositField));
        formGrid.add(UiKit.createFieldBlock("Tổng tạm tính", estimatedTotalField));
        formGrid.add(UiKit.createFieldBlock("Phí phát sinh", extraFeeField));
        formGrid.add(UiKit.createFieldBlock("Tổng thanh toán", totalAmountField));
        formGrid.add(UiKit.createFieldBlock("Thời gian bắt đầu", startTimeField));
        formGrid.add(UiKit.createFieldBlock("Dự kiến trả", expectedReturnField));
        formGrid.add(UiKit.createFieldBlock("Thực trả", actualReturnField));
        formGrid.add(UiKit.createFieldBlock("Trạng thái", UiKit.createComboBox("PENDING", "ACTIVE", "COMPLETED", "CANCELLED")));

        formCard.add(formGrid, BorderLayout.CENTER);
        formCard.add(UiKit.createButtonRow(
                UiKit.createPrimaryButton("Tạo hợp đồng"),
                UiKit.createSecondaryButton("Cập nhật"),
                UiKit.createGhostButton("Đánh dấu đã trả")), BorderLayout.SOUTH);

        JPanel tableCard = UiKit.createSurfacePanel();
        tableCard.setLayout(new BorderLayout(0, 14));
        tableCard.add(UiKit.createSectionHeader(
                "Danh sách hợp đồng",
                "Bảng tham chiếu giúp bạn nhìn rõ khu vực sẽ gắn dữ liệu thật."), BorderLayout.NORTH);

        JPanel filterRow = new JPanel(new GridLayout(1, 3, 12, 12));
        filterRow.setOpaque(false);
        filterRow.add(UiKit.createFieldBlock("Tìm kiếm", UiKit.createTextField(12)));
        filterRow.add(UiKit.createFieldBlock("Trạng thái", UiKit.createComboBox("Tất cả", "PENDING", "ACTIVE", "COMPLETED", "CANCELLED")));
        filterRow.add(UiKit.createFieldBlock("Khoảng thời gian", UiKit.createComboBox("Hôm nay", "7 ngày", "30 ngày")));

        JTable table = UiKit.createTable(
                new String[] {"Mã thuê", "Khách", "Xe", "Bắt đầu", "Dự kiến trả", "Trạng thái"},
                new Object[][] {
                        {"R-001", "C001", "V001", "2026-05-05 08:00", "2026-05-07 08:00", "ACTIVE"},
                        {"R-002", "C009", "V010", "2026-05-03 09:00", "2026-05-04 09:00", "COMPLETED"},
                        {"R-003", "C004", "V005", "2026-05-06 10:00", "2026-05-08 10:00", "PENDING"}
                });

        JPanel filterAndTable = new JPanel(new BorderLayout(0, 14));
        filterAndTable.setOpaque(false);
        filterAndTable.add(filterRow, BorderLayout.NORTH);
        filterAndTable.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

        tableCard.add(filterAndTable, BorderLayout.CENTER);

                body.add(formCard, BorderLayout.NORTH);
                body.add(tableCard, BorderLayout.CENTER);

                JPanel content = new JPanel(new BorderLayout(0, 16));
                content.setOpaque(false);
                content.add(metrics, BorderLayout.NORTH);
                content.add(body, BorderLayout.CENTER);

                add(content, BorderLayout.CENTER);
    }
}
