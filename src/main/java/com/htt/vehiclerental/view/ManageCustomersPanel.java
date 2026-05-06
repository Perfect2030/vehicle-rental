package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;

public class ManageCustomersPanel extends JPanel {
    public ManageCustomersPanel() {
        initComponents();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

        //north
        JPanel northPanel = new JPanel(new BorderLayout(0, 16));
        northPanel.setOpaque(false);

            JPanel banner = UiKit.createInfoBanner("Quản lý khách hàng", "Lưu và tra cứu thông tin khách thuê theo số định danh, số điện thoại và địa chỉ.", UiKit.INFO);
            banner.setPreferredSize(new Dimension(0, 100));

            JPanel info = new JPanel(new GridLayout(1, 3, 16, 16));
            info.setOpaque(false);

            JPanel card1 = UiKit.createMetricCard("Tổng số khách hàng", "120", "", UiKit.PRIMARY);
            JPanel card2 = UiKit.createMetricCard("Số khách hàng đang thuê", "5", "", UiKit.WARNING);

            info.add(card1);
            info.add(card2);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(info, BorderLayout.CENTER);


        //center 
        JSplitPane centerPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerPanel.setOpaque(false);
        
            //left 
            JPanel leftCenter = UiKit.createSurfacePanel();
            leftCenter.setLayout(new BorderLayout(16, 16));
            leftCenter.setBorder(UiKit.createCardBorder());

                //left header
                JPanel leftHeader = UiKit.createSectionHeader("Thông tin khách hàng", "Thêm mới hoặc cập nhật thông tin khách hàng.");

                //left form
                JPanel leftForm = new JPanel(new GridLayout(4, 2, 16, 16));
                leftForm.setOpaque(false);

                leftForm.add(UiKit.createSubtitleLabel("Số định danh (CCCD):"));
                leftForm.add(UiKit.createTextField(12));

                leftForm.add(UiKit.createSubtitleLabel("Họ và tên:"));
                leftForm.add(UiKit.createTextField(20));

                leftForm.add(UiKit.createSubtitleLabel("Số điện thoại:"));
                leftForm.add(UiKit.createTextField(12));

                leftForm.add(UiKit.createSubtitleLabel("Địa chỉ:"));
                leftForm.add(UiKit.createTextField(20));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm khách mới");
                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");

                buttonPanel.add(addButton);
                buttonPanel.add(updateButton);

            leftCenter.add(leftHeader, BorderLayout.NORTH);
            leftCenter.add(leftForm, BorderLayout.CENTER);
            leftCenter.add(buttonPanel, BorderLayout.SOUTH);

            //right 
            JPanel rightCenter = UiKit.createSurfacePanel();
            rightCenter.setLayout(new BorderLayout(16, 16));
            rightCenter.setBorder(UiKit.createCardBorder());

                //right north
                JPanel rightNorth = new JPanel(new BorderLayout(16, 0));
                rightNorth.setOpaque(false);

                    JPanel rightHeader = UiKit.createSectionHeader("Danh sách khách hàng", "Xem và tìm kiếm khách hàng đã đăng ký trong hệ thống.");
                    JPanel searchBar = new JPanel(new GridLayout(2, 2, 16, 0));

                        searchBar.add(UiKit.createSubtitleLabel("Tìm kiếm"));
                        searchBar.add(UiKit.createSubtitleLabel("Sắp xếp"));
                        searchBar.add(UiKit.createTextField(20));
                        searchBar.add(UiKit.createComboBox(
                            new String[] {"Mặc định", "Tên A-Z", "Tên Z-A", "Số định danh tăng dần", "Số định danh giảm dần"}
                        ));

                
                rightNorth.add(rightHeader, BorderLayout.NORTH);
                rightNorth.add(searchBar, BorderLayout.CENTER);
                
                searchBar.setOpaque(false);

                JTable table = UiKit.createTable(
                    new String[]{"Số định danh", "Họ và tên", "Số điện thoại", "Địa chỉ"}, 
                    new Object[][] {
                        {"123456789012", "Nguyễn Văn A", "0123456789", "123 Đường ABC, Quận 1"},
                        {"987654321098", "Trần Thị B", "0987654321", "456 Đường XYZ, Quận 2"},
                        {"555555555555", "Lê Văn C", "0555555555", "789 Đường DEF, Quận 3"},
                    }
                );
            
            rightCenter.add(rightNorth, BorderLayout.NORTH);
            rightCenter.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

        centerPanel.add(leftCenter, JSplitPane.LEFT);
        centerPanel.add(rightCenter, JSplitPane.RIGHT);

        //

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
}