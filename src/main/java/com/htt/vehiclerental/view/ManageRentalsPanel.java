package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;  
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.dal.RentalDAL;
import com.htt.vehiclerental.dto.RentalView;

public class ManageRentalsPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JTable table;
    private DefaultTableModel tableModel;
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

        searchBar.add(UiKit.createFieldBlock("Tìm Kiếm", searchField));
        searchBar.add(UiKit.createFieldBlock("Trạng thái", sortComboBox));

        table = UiKit.createTable(
        new String[] { "Mã số thuê", "Tên khách hàng", "Biển số xe", "Hãng xe", "Mẫu xe", "Ngày thuê", "Ngày trả dự kiến", "Trạng thái" },
        new Object[0][0]);
        tableModel = (DefaultTableModel) table.getModel();

        loadRentals();

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
        completedButton.addActionListener(e -> {new RentalCompletionDialog(table.getValueAt(table.getSelectedRow(), 0)).setVisible(true);});

        centerSouth.add(detailButton);
        centerSouth.add(completedButton);
        center.add(centerSouth, BorderLayout.SOUTH);

        // add to main panel
        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void loadRentals() {
        tableModel.setRowCount(0);

        for (RentalView row : RentalBLL.getAllRentalsView()) {
            tableModel.addRow(new Object[] {
                row.getRentalId(),
                row.getCustomerName(),
                row.getLicensePlate(),
                row.getBrand(),
                row.getModel(),
                row.getStartTime(),
                row.getExpectedReturnTime(),
                row.getStatus()
            });
        }
    }
    
}
