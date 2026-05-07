package com.htt.vehiclerental.view;

import javax.swing.*;
import java.awt.*;

public class ManageVehiclesPanel extends JPanel {
    public ManageVehiclesPanel() {
        initComponents();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

        // north
        JPanel northPanel = new JPanel(new BorderLayout(0, 16));
        northPanel.setOpaque(false);

        JPanel banner = UiKit.createInfoBanner("Quản lý xe",
                "Thiết lập và quản lý thông tin xe.", UiKit.INFO);
        banner.setPreferredSize(new Dimension(0, 100));

        JPanel info = new JPanel(new GridLayout(1, 4, 16, 0));
        info.setOpaque(false);

        JPanel card1 = UiKit.createMetricCard("Tổng số xe", "120", "", UiKit.PRIMARY);
        JPanel card2 = UiKit.createMetricCard("Số xe đang cho thuê", "5", "", UiKit.WARNING);
        JPanel card4 = UiKit.createMetricCard("Số xe đang bảo trì", "10", "", UiKit.INFO);
        JPanel card3 = UiKit.createMetricCard("Số xe sẵn có", "105", "", UiKit.SUCCESS);

        info.add(card1);
        info.add(card2);
        info.add(card3);
        info.add(card4);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(info, BorderLayout.CENTER);

        // center
        JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerPanel.setOpaque(false);

        // left
        JPanel leftCenter = UiKit.createSurfacePanel();
        leftCenter.setLayout(new BorderLayout(16, 16));
        leftCenter.setBorder(UiKit.createCardBorder());

        // left header
        JPanel leftHeader = UiKit.createSectionHeader("Thông tin xe",
                "Thêm mới hoặc cập nhật thông tin xe.");

        // left form
        JPanel leftForm = new JPanel(new GridLayout(4, 2, 12, 12));
        leftForm.setOpaque(false);

        JTextField plateField = UiKit.createTextField(12);
        JTextField brandField = UiKit.createTextField(12);
        JTextField modelField = UiKit.createTextField(12);
        JComboBox<String> typeBox = UiKit.createComboBox("Xe số", "Xe tay ga", "Xe côn");
        JTextField displacementField = UiKit.createTextField(12);
        JTextField priceField = UiKit.createTextField(12);
        JComboBox<String> statusBox = UiKit.createComboBox("AVAILABLE", "RENTED", "MAINTENANCE");

        leftForm.add(UiKit.createFieldBlock("Biển số", plateField));
        leftForm.add(UiKit.createFieldBlock("Hãng xe", brandField));
        leftForm.add(UiKit.createFieldBlock("Mẫu xe", modelField));
        leftForm.add(UiKit.createFieldBlock("Loại xe", typeBox));
        leftForm.add(UiKit.createFieldBlock("Phân khối", displacementField));
        leftForm.add(UiKit.createFieldBlock("Giá thuê/ngày", priceField));
        leftForm.add(UiKit.createFieldBlock("Trạng thái", statusBox));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        buttonPanel.setOpaque(false);

        JButton addButton = UiKit.createPrimaryButton("Thêm xe mới");
        JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");
        JButton deleteButton = UiKit.createDangerButton("Xóa xe");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        leftCenter.add(leftHeader, BorderLayout.NORTH);
        leftCenter.add(leftForm, BorderLayout.CENTER);
        leftCenter.add(buttonPanel, BorderLayout.SOUTH);

        // right
        JPanel rightCenter = UiKit.createSurfacePanel();
        rightCenter.setLayout(new BorderLayout(16, 16));
        rightCenter.setBorder(UiKit.createCardBorder());

        // right north
        JPanel rightNorth = new JPanel(new BorderLayout(16, 0));
        rightNorth.setOpaque(false);

        JPanel rightHeader = UiKit.createSectionHeader("Danh sách xe hiện có",
                "Xem và tìm kiếm xe trong hệ thống.");
        JPanel searchBar = new JPanel(new GridLayout(4, 2, 16, 0));

        searchBar.add(UiKit.createSubtitleLabel("Tìm kiếm theo biển số, hãng xe, mẫu xe..."));
        searchBar.add(UiKit.createSubtitleLabel("Trạng thái"));
        searchBar.add(UiKit.createTextField(20));
        searchBar.add(UiKit.createComboBox("ALL", "AVAILABLE", "RENTED", "MAINTENANCE"));
        searchBar.add(UiKit.createSubtitleLabel("Sắp xếp"));
        searchBar.add(new JLabel()); // placeholder
        searchBar.add(UiKit.createComboBox(
                new String[] { "Mặc định", "Giá thuê tăng dần", "Giá thuê giảm dần", "Phân khối tăng dần",
                        "Phân khối giảm dần" }));

        rightNorth.add(rightHeader, BorderLayout.NORTH);
        rightNorth.add(searchBar, BorderLayout.CENTER);

        searchBar.setOpaque(false);

        JTable table = UiKit.createTable(
                new String[] { "Biển số", "Hãng xe", "Mẫu xe", "Loại xe", "Phân khối", "Giá thuê/ngày", "Trạng thái" },
                new Object[][] {
                        { "30A-12345", "Honda", "Air Blade", "Xe tay ga", "125cc", "150,000 VND", "AVAILABLE" },
                        { "29B-54321", "Yamaha", "Exciter", "Xe côn", "150cc", "200,000 VND", "RENTED" },
                        { "31C-67890", "Suzuki", "Viva", "Xe số", "110cc", "120,000 VND", "MAINTENANCE" },
                        { "32D-78901", "Kawasaki", "Ninja", "Xe thể thao", "300cc", "250,000 VND", "AVAILABLE" },
                });

        rightCenter.add(rightNorth, BorderLayout.NORTH);
        rightCenter.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

        centerPanel.add(leftCenter, JSplitPane.LEFT);
        centerPanel.add(rightCenter, JSplitPane.RIGHT);

        //

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

}
