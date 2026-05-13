package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.htt.vehiclerental.bll.ReportBLL;
import com.htt.vehiclerental.dto.Report;

public class GenerateReportsPanel extends JPanel {

    private JLabel totalRevenue, totalRentals, totalFees;

    public GenerateReportsPanel() {
        initComponents();
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
        northPanel.add(info, BorderLayout.CENTER);

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
                buttonPanel.setOpaque(false);

                JButton todayButton = UiKit.createPrimaryButton("Thống kê hôm nay");
                    todayButton.addActionListener(e -> createTodayReport());

                JButton weeklyButton = UiKit.createPrimaryButton("Thống kê tuần này");
                    weeklyButton.addActionListener(e -> createWeeklyReport());

                JButton monthlyButton = UiKit.createPrimaryButton("Thống kê tháng này");
                    monthlyButton.addActionListener(e -> createMonthlyReport());

                JButton yearlyButton = UiKit.createPrimaryButton("Thống kê năm này");
                    yearlyButton.addActionListener(e -> createYearlyReport());

                JButton customButton = UiKit.createPrimaryButton("Thống kê tùy chỉnh");
                    customButton.addActionListener(e -> createCustomReport());

            buttonPanel.add(todayButton);
            buttonPanel.add(weeklyButton);
            buttonPanel.add(monthlyButton);
            buttonPanel.add(yearlyButton);
            buttonPanel.add(customButton);

        center.add(buttonPanel, BorderLayout.NORTH);

        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void createTodayReport() {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        createReport(start, end);
    }

    private void createWeeklyReport() {
        LocalDateTime start = LocalDateTime.now().minusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        createReport(start, end);
    }

    private void createMonthlyReport() {
        LocalDateTime start = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().plusMonths(1).withDayOfMonth(1).minusNanos(1);
        createReport(start, end);
    }

    private void createYearlyReport() {
        LocalDateTime start = LocalDateTime.now().withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().plusYears(1).withMonth(1).withDayOfMonth(1).minusNanos(1);
        createReport(start, end);
    }

    private void createCustomReport() {
        // Placeholder for custom report generation logic
        JOptionPane.showMessageDialog(this, "Chức năng thống kê tùy chỉnh đang được phát triển.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void createReport(LocalDateTime start, LocalDateTime end) {
        // Placeholder for report generation logic
        Report report = ReportBLL.generateReport(start, end);
        totalRevenue.setText(String.valueOf(report.getTotalRevenue()));
        totalFees.setText(String.valueOf(report.getTotalFees()));
        totalRentals.setText(String.valueOf(report.getTotalRentals()));
    }
    
}
