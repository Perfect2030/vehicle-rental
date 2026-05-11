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

                String totalVehicles = "120";
                String rentedVehicles = "5";
                String availableVehicles = "105";
                String maintenanceVehicles = "10";
                JPanel card1 = UiKit.createMetricCard("Tổng số xe", totalVehicles, "", UiKit.PRIMARY);
                JPanel card2 = UiKit.createMetricCard("Số xe đang cho thuê", rentedVehicles, "", UiKit.WARNING);
                JPanel card4 = UiKit.createMetricCard("Số xe đang bảo trì", maintenanceVehicles, "", UiKit.INFO);
                JPanel card3 = UiKit.createMetricCard("Số xe sẵn có", availableVehicles, "", UiKit.SUCCESS);

                info.add(card1);
                info.add(card2);
                info.add(card3);
                info.add(card4);

                northPanel.add(banner, BorderLayout.NORTH);
                northPanel.add(info, BorderLayout.CENTER);

                //
                JPanel centerPanel = UiKit.createSurfacePanel();
                centerPanel.setLayout(new BorderLayout(16, 16));
                centerPanel.setBorder(UiKit.createCardBorder());

                // search and filter
                JPanel searchBar = new JPanel(new GridLayout(1, 3, 16, 0));

                JTextField searchField = UiKit.createTextField(20);
                JComboBox<String> statusComboBox = UiKit.createComboBox("ALL", "AVAILABLE", "RENTED", "MAINTENANCE");
                JComboBox<String> sortComboBox = UiKit.createComboBox(
                                "Mặc định", "Giá thuê tăng dần", "Giá thuê giảm dần",
                                "Phân khối tăng dần",
                                "Phân khối giảm dần");

                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Trạng thái", statusComboBox));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp theo", sortComboBox));
                searchBar.setOpaque(false);

                JTable table = UiKit.createTable(
                                new String[] { "Biển số", "Hãng xe", "Mẫu xe", "Loại xe", "Phân khối", "Giá thuê/ngày",
                                                "Trạng thái" },
                                new Object[][] {
                                                { "30A-12345", "Honda", "Air Blade", "Xe tay ga", "125cc",
                                                                "150,000 VND", "AVAILABLE" },
                                                { "29B-54321", "Yamaha", "Exciter", "Xe côn", "150cc", "200,000 VND",
                                                                "RENTED" },
                                                { "31C-67890", "Suzuki", "Viva", "Xe số", "110cc", "120,000 VND",
                                                                "MAINTENANCE" },
                                                { "32D-78901", "Kawasaki", "Ninja", "Xe thể thao", "300cc",
                                                                "250,000 VND", "AVAILABLE" },
                                });

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm xe mới");
                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin xe");
                JButton deleteButton = UiKit.createDangerButton("Xóa xe");
                JButton createRentalButton = UiKit.createPrimaryButton("Tạo hợp đồng thuê");

                buttonPanel.add(addButton);
                buttonPanel.add(updateButton);
                buttonPanel.add(deleteButton);
                buttonPanel.add(createRentalButton);

                centerPanel.add(searchBar, BorderLayout.NORTH);
                centerPanel.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
                centerPanel.add(buttonPanel, BorderLayout.SOUTH);

                //
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
        }

}
