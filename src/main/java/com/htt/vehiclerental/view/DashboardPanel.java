package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
    initComponents();
    }

    private void initComponents() {
    setBackground(UiKit.APP_BACKGROUND);
    setLayout(new BorderLayout(0, 18));

    JPanel content = new JPanel();
    content.setOpaque(false);
    content.setLayout(new javax.swing.BoxLayout(content, javax.swing.BoxLayout.Y_AXIS));

    JPanel hero = UiKit.createInfoBanner(
        "Bảng điều khiển vận hành",
        "Theo dõi nhanh tình trạng xe, đơn thuê, khách hàng và doanh thu theo ngày.",
        UiKit.PRIMARY);

    JPanel cards = new JPanel(new GridLayout(1, 3, 16, 16));
    cards.setOpaque(false);
    cards.add(UiKit.createMetricCard("Xe đang sẵn sàng", "18", "Còn trống để cho thuê ngay", UiKit.SUCCESS));
    cards.add(UiKit.createMetricCard("Đơn thuê hôm nay", "09", "3 đơn đang chờ xác nhận", UiKit.INFO));
    cards.add(UiKit.createMetricCard("Doanh thu tháng", "48.6M", "Tăng 12% so với tháng trước", UiKit.WARNING));

    JPanel lower = new JPanel(new GridLayout(1, 2, 16, 16));
    lower.setOpaque(false);

    JPanel quickActions = new JPanel();
    quickActions.setOpaque(false);
    quickActions.setLayout(new javax.swing.BoxLayout(quickActions, javax.swing.BoxLayout.Y_AXIS));
    quickActions.add(UiKit.createSectionHeader(
        "Lối tắt nhanh",
        "Những thao tác thường dùng để điều hành hệ thống."));
    quickActions.add(javax.swing.Box.createVerticalStrut(14));
    quickActions.add(UiKit.createButtonRow(
        UiKit.createPrimaryButton("Thêm xe mới"),
        UiKit.createSecondaryButton("Tạo đơn thuê")));
    quickActions.add(javax.swing.Box.createVerticalStrut(14));
    quickActions.add(UiKit.createButtonRow(
        UiKit.createGhostButton("Ghi phí phát sinh"),
        UiKit.createGhostButton("Xuất báo cáo")));
    quickActions.add(javax.swing.Box.createVerticalStrut(18));
    quickActions.add(UiKit.createSectionTitleLabel("Tình trạng hệ thống"));
    quickActions.add(javax.swing.Box.createVerticalStrut(10));
    quickActions.add(UiKit.createStatList(
        "Cơ sở dữ liệu sẵn sàng cho kết nối",
        "Giao diện đã bố cục đủ 6 phân hệ",
        "Có thể gắn CRUD và báo cáo sau này"));

    JTable activityTable = UiKit.createTable(
        new String[] {"Thời gian", "Sự kiện", "Trạng thái"},
        new Object[][] {
            {"08:15", "Nhận xe Wave mới", "Đã ghi nhận"},
            {"09:10", "Khách hàng tạo đơn thuê", "Chờ xác nhận"},
            {"11:45", "Thu phí phát sinh", "Hoàn tất"},
            {"14:20", "Xuất báo cáo ngày", "Thành công"}
        });

    JPanel recentActivity = new JPanel();
    recentActivity.setOpaque(false);
    recentActivity.setLayout(new BorderLayout(0, 14));
    recentActivity.add(UiKit.createSectionHeader(
        "Hoạt động gần đây",
        "Mẫu dữ liệu minh họa để bạn hình dung khu vực hiển thị thực tế."), BorderLayout.NORTH);
    recentActivity.add(UiKit.createTableScrollPane(activityTable), BorderLayout.CENTER);

    lower.add(quickActions);
    lower.add(recentActivity);

    content.add(hero);
    content.add(javax.swing.Box.createVerticalStrut(18));
    content.add(cards);
    content.add(javax.swing.Box.createVerticalStrut(18));
    content.add(lower);

    add(content, BorderLayout.CENTER);
    }
}
