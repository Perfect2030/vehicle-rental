package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GenerateReportsPanel extends JPanel {

    private JLabel totalCustomersLabel, totalRentingLabel;

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

            JPanel info = new JPanel(new GridLayout(1, 2, 16, 16));
            info.setOpaque(false);

            totalCustomersLabel = UiKit.createMetricValueLabel("0"); 
            totalRentingLabel = UiKit.createMetricValueLabel("0");

            JPanel card1 = UiKit.createMetricCard("Tổng số khách hàng", totalCustomersLabel.getText(), "", UiKit.PRIMARY);
            JPanel card2 = UiKit.createMetricCard("Số khách hàng đang thuê", totalRentingLabel.getText(), "", UiKit.WARNING);

            info.add(card1);
            info.add(card2);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(info, BorderLayout.CENTER);

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton createReportButton = UiKit.createPrimaryButton("Tạo báo cáo");
                    createReportButton.addActionListener(e -> createReport());

            buttonPanel.add(createReportButton);

        // center.add(searchBar, BorderLayout.NORTH);
        // center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void createReport() {
        // Placeholder for report generation logic
        JOptionPane.showMessageDialog(this, "Báo cáo đã được tạo thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
