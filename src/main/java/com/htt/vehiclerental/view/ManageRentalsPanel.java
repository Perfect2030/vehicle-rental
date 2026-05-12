package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;  
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.*;

import javax.swing.*;
public class ManageRentalsPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JTable table;
    private JButton detailButton;
    private JButton completedButton;

    public ManageRentalsPanel() {
        initComponents();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

         // north
        JPanel northPanel = new JPanel(new BorderLayout(0, 16));
        northPanel.setOpaque(false);

        JPanel banner = UiKit.createInfoBanner("Quản lý thuê xe",
                "Thiết lập và quản lý thông tin thuê xe.", UiKit.INFO);
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
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

        //center north
        JPanel searchBar = new JPanel(new GridLayout(1, 2, 16, 0));
        searchBar.setOpaque(false);

        searchField = UiKit.createTextField(20);
        sortComboBox = UiKit.createComboBox(new String[] {"Tất cả", "Đang cho thuê", "Đã hoàn thành", "Quá hạn"});

        searchBar.add(searchField);
        searchBar.add(sortComboBox);
        searchBar.add(UiKit.createFieldBlock("Tìm Kiếm", searchField));
        searchBar.add(UiKit.createFieldBlock("Trạng thái", sortComboBox));

        table = UiKit.createTable(
        new String[] { "Mã số thuê", "Tên khách hàng", "Biển số xe", "Hãng xe", "Mẫu xe", "Ngày thuê", "Ngày trả dự kiến", "Trạng thái" },
        new Object[][] {
                {1, "Nguyễn Văn A", "30A-12345", "Honda", "Air Blade", "01/01/2024", "05/01/2024", "RENTING" },
                {2, "Trần Thị B", "30B-54321", "Yamaha", "Exciter", "02/01/2024", "06/01/2024", "COMPLETED" },
                {3, "Lê Văn C", "30C-67890", "Suzuki", "V-Strom", "03/01/2024", "07/01/2024", "OVERDUE" },
                {4, "Phạm Thị D", "30D-13579", "Honda", "CBR500R", "04/01/2024", "08/01/2024", "RENTING" },
                {5, "Hoàng Văn E", "30E-24680", "Yamaha", "MT-07", "05/01/2024", "09/01/2024", "COMPLETED" },
                {6, "Đỗ Thị F", "30F-11223", "Suzuki", "GSX250R", "06/01/2024", "10/01/2024", "OVERDUE" },
                {7, "Vũ Văn G", "30G-44556", "Honda", "CB500X", "07/01/2024", "11/01/2024", "RENTING" },
                {8, "Ngô Thị H", "30H-77889", "Yamaha", "R3", "08/01/2024", "12/01/2024", "COMPLETED" },       
                {9, "Trịnh Văn I", "30I-99000", "Suzuki", "V-Strom 650", "09/01/2024", "13/01/2024", "OVERDUE" },
                {10, "Phan Thị J", "30J-22334", "Honda", "CB650R", "10/01/2024", "14/01/2024", "RENTING" }
        });

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);


        //SOUTH
        JPanel centerSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        centerSouth.setOpaque(false);
        detailButton = UiKit.createPrimaryButton("Xem chi tiết");
        completedButton = UiKit.createPrimaryButton("Trả xe");

        // add action listeners
        // truyền vào id của thuê xe để hiển thị chi tiết trong dialog ( cột dầu tiên của table )
        detailButton.addActionListener(e -> {new RentalDetailDialog(table.getValueAt(table.getSelectedRow(), 0)).setVisible(true);});
        // completedButton.addActionListener(e -> {new RentalCompletionDialog().setVisible(true);});

        centerSouth.add(detailButton);
        centerSouth.add(completedButton);
        center.add(centerSouth, BorderLayout.SOUTH);

        // add to main panel
        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
    
}
