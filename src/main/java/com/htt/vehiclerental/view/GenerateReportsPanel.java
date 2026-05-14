package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.htt.vehiclerental.bll.ReportBLL;
import com.htt.vehiclerental.dto.Report;
import com.toedter.calendar.JDateChooser;

public class GenerateReportsPanel extends JPanel {

    private JLabel totalRevenue, totalRentals, totalFees;
    private JLabel headerTitle;

    private JDateChooser startDateChooser, endDateChooser;

    public GenerateReportsPanel() {
        initComponents();
        createLifetimeReport();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

        //north
        JPanel northPanel = new JPanel(new BorderLayout(0, 16));
        northPanel.setOpaque(false);

            JPanel banner = UiKit.createInfoBanner("Thống kê hệ thống", "Thống kê thông tin hệ thống.", UiKit.INFO);
            banner.setPreferredSize(new Dimension(0, 100));

            headerTitle = UiKit.createTitleLabel("Đang tải báo cáo...");

            JPanel info = new JPanel(new GridLayout(2, 2, 16, 16));
                info.setOpaque(false);

                totalRevenue = UiKit.createMetricValueLabel("0"); 
                totalRentals = UiKit.createMetricValueLabel("0");
                totalFees = UiKit.createMetricValueLabel("0");

                JPanel card1 = UiKit.createMetricCard("Tổng doanh thu", totalRevenue, "", UiKit.PRIMARY);
                JPanel card2 = UiKit.createMetricCard("Tổng phí phát sinh", totalFees, "", UiKit.PRIMARY);
                JPanel card3 = UiKit.createMetricCard("Tổng số đơn thuê", totalRentals, "", UiKit.WARNING);

            info.add(card1);
            info.add(card2);
            info.add(card3);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(headerTitle, BorderLayout.CENTER);
        northPanel.add(info, BorderLayout.SOUTH);

        //center


        //south 
        JPanel south = UiKit.createSurfacePanel();
        south.setLayout(new BorderLayout(16, 16));
        south.setBorder(UiKit.createCardBorder());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
                buttonPanel.setOpaque(false);

                JButton allButton = UiKit.createPrimaryButton("Thống kê tất cả");
                    allButton.addActionListener(e -> createLifetimeReport());

                JButton todayButton = UiKit.createPrimaryButton("Thống kê hôm nay");
                    todayButton.addActionListener(e -> createTodayReport());

                JButton weeklyButton = UiKit.createPrimaryButton("Thống kê tuần này");
                    weeklyButton.addActionListener(e -> createWeeklyReport());

                JButton monthlyButton = UiKit.createPrimaryButton("Thống kê tháng này");
                    monthlyButton.addActionListener(e -> createMonthlyReport());

                JButton yearlyButton = UiKit.createPrimaryButton("Thống kê năm này");
                    yearlyButton.addActionListener(e -> createYearlyReport());


            buttonPanel.add(allButton);
            buttonPanel.add(todayButton);
            buttonPanel.add(weeklyButton);
            buttonPanel.add(monthlyButton);
            buttonPanel.add(yearlyButton);

            JPanel customContainer = new JPanel(new BorderLayout());
            customContainer.setOpaque(false);
            JPanel customNorth = new JPanel(new BorderLayout());
            customNorth.setOpaque(false);
            JLabel emptyLabel1 = new JLabel("     ");
            JLabel emptyLabel2 = new JLabel("     ");

            JPanel customReportPanel = new JPanel(new GridLayout(1, 3, 16, 16));
                // customReportPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 180, 150));
                customReportPanel.setOpaque(false);
                
                startDateChooser = new JDateChooser();
                startDateChooser.setDateFormatString("dd/MM/yyyy");
                endDateChooser = new JDateChooser();
                endDateChooser.setDateFormatString("dd/MM/yyyy");

                JButton customButton = UiKit.createPrimaryButton("Thống kê tùy chỉnh");
                    customButton.addActionListener(e -> createCustomReport());

                customReportPanel.add(UiKit.createFieldBlock("Ngày bắt đầu", startDateChooser));
                customReportPanel.add(UiKit.createFieldBlock("Ngày kết thúc", endDateChooser));
                customReportPanel.add(UiKit.createFieldBlock(" ", customButton));

        customNorth.add(emptyLabel1, BorderLayout.WEST);
        customNorth.add(emptyLabel2, BorderLayout.EAST);
        customNorth.add(customReportPanel, BorderLayout.CENTER);

        customContainer.add(customNorth, BorderLayout.NORTH);

        south.add(buttonPanel, BorderLayout.NORTH);
        south.add(customContainer, BorderLayout.CENTER);

        add(northPanel, BorderLayout.NORTH);
        add(south, BorderLayout.CENTER);
    }

    private void createLifetimeReport() {
        headerTitle.setText("Thống kê toàn bộ");

        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.now();
        createReport(start, end);
    }

    private void createTodayReport() {
        headerTitle.setText("Thống kê theo ngày");

        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        createReport(start, end);
    }

    private void createWeeklyReport() {
        headerTitle.setText("Thống kê theo tuần");

        LocalDateTime start = LocalDateTime.now().with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().with(DayOfWeek.SUNDAY).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        createReport(start, end);
    }

    private void createMonthlyReport() {
        headerTitle.setText("Thống kê theo tháng");

        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusNanos(1);
        createReport(start, end);
    }

    private void createYearlyReport() {
        headerTitle.setText("Thống kê theo năm");

        LocalDateTime start = LocalDateTime.now().withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().plusYears(1).withMonth(1).withDayOfMonth(1).minusNanos(1);
        createReport(start, end);
    }

    private void createCustomReport() {
        headerTitle.setText("Thống kê tùy chỉnh");

        if (startDateChooser.getDate() == null || endDateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        createReport(startDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                     endDateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(23).withMinute(59).withSecond(59).withNano(999999999));
    }

    private void createReport(LocalDateTime start, LocalDateTime end) {
        // Placeholder for report generation logic
        Report report = ReportBLL.generateReport(start, end);
        totalRevenue.setText(String.format("%,d VND", report.getTotalRevenue()));
        totalFees.setText(String.format("%,d VND", report.getTotalFees()));
        totalRentals.setText(String.format("%,d", report.getTotalRentals()));
    }
    
}
