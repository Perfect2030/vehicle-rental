package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

public class GenerateReportsPanel extends JPanel {
    public GenerateReportsPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 18));

        add(UiKit.createInfoBanner(
                "Báo cáo doanh thu",
                "Màn hình tổng hợp số liệu vận hành, doanh thu và hiệu suất hệ thống.",
                UiKit.PRIMARY), BorderLayout.NORTH);

        JPanel metrics = new JPanel(new GridLayout(2, 2, 16, 16));
        metrics.setOpaque(false);
        metrics.add(UiKit.createMetricCard("Tổng doanh thu", "71.2M", "Từ các đơn đã hoàn tất", UiKit.SUCCESS));
        metrics.add(UiKit.createMetricCard("Doanh thu phí", "9.8M", "Phí phát sinh đã ghi nhận", UiKit.WARNING));
        metrics.add(UiKit.createMetricCard("Đơn hoàn tất", "64", "Số hợp đồng đã đóng", UiKit.INFO));
        metrics.add(UiKit.createMetricCard("Tỷ lệ đúng hạn", "92%", "Khách trả xe đúng hạn", UiKit.PRIMARY));

        JPanel body = new JPanel(new GridLayout(1, 2, 16, 16));
        body.setOpaque(false);

        JPanel reportCard = UiKit.createSurfacePanel();
        reportCard.setLayout(new BorderLayout(0, 14));
        reportCard.add(UiKit.createSectionHeader(
                "Bộ lọc báo cáo",
                "Chọn phạm vi thời gian và kiểu thống kê trước khi xuất báo cáo."), BorderLayout.NORTH);

        JPanel filterGrid = new JPanel(new GridLayout(0, 2, 12, 12));
        filterGrid.setOpaque(false);
        filterGrid.add(UiKit.createFieldBlock("Từ ngày", UiKit.createTextField(12)));
        filterGrid.add(UiKit.createFieldBlock("Đến ngày", UiKit.createTextField(12)));
        filterGrid.add(UiKit.createFieldBlock("Loại báo cáo", UiKit.createComboBox("Tổng doanh thu", "Theo xe", "Theo khách hàng", "Theo phí")));
        filterGrid.add(UiKit.createFieldBlock("Xuất file", UiKit.createComboBox("PDF", "Excel", "CSV")));

        reportCard.add(filterGrid, BorderLayout.CENTER);
        reportCard.add(UiKit.createButtonRow(
                UiKit.createPrimaryButton("Xem báo cáo"),
                UiKit.createSecondaryButton("Xuất file"),
                UiKit.createGhostButton("Làm mới")), BorderLayout.SOUTH);

        JPanel insightsCard = UiKit.createSurfacePanel();
        insightsCard.setLayout(new BorderLayout(0, 14));
        insightsCard.add(UiKit.createSectionHeader(
                "Báo cáo gần đây",
                "Tóm tắt dưới đây mô phỏng phần bảng hoặc biểu đồ trong ứng dụng thật."), BorderLayout.NORTH);

        JTable table = UiKit.createTable(
                new String[] {"Mốc thời gian", "Chỉ số", "Giá trị"},
                new Object[][] {
                        {"Hôm nay", "Đơn thuê", "9"},
                        {"7 ngày", "Doanh thu", "18.3M"},
                        {"30 ngày", "Khách hàng mới", "41"},
                        {"30 ngày", "Phí phát sinh", "3.2M"}
                });

        insightsCard.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

        body.add(reportCard);
        body.add(insightsCard);

                JPanel content = new JPanel(new BorderLayout(0, 16));
                content.setOpaque(false);
                content.add(metrics, BorderLayout.NORTH);
                content.add(body, BorderLayout.CENTER);

                add(content, BorderLayout.CENTER);
    }
}
