package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ManageFeesPanel extends JPanel {

    private JTable table;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;

    public ManageFeesPanel() {
        initComponents();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

        //north
        JPanel banner = UiKit.createInfoBanner("Quản lý phí", "Lưu và tra cứu thông tin phí.", UiKit.INFO);
        banner.setPreferredSize(new Dimension(0, 100));

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());


            JPanel searchBar = new JPanel(new GridLayout(1, 2, 16, 0));

                searchField = UiKit.createTextField(20);
                sortComboBox = UiKit.createComboBox(new String[] {"Mặc định", "Tên A-Z", "Tên Z-A"});

                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp", sortComboBox));

            searchBar.setOpaque(false);

            table = UiKit.createTable(
                new String[]{"Mã phí", "Tên phí", "Giá phạt", "Mô tả lỗi"}, 
                new Object[][] {
                    {"P001", "Phí trễ hạn", "500.000 VND/ngày", "Áp dụng khi trả xe muộn hơn ngày dự kiến."},
                    {"P002", "Phí hủy đặt trước", "1.000.000 VND", "Áp dụng khi khách hàng hủy đặt trước trong vòng 24 giờ trước ngày thuê."},
                    {"P003", "Phí thiệt hại", "Tùy theo mức độ thiệt hại", "Áp dụng khi xe bị hư hỏng do lỗi của khách hàng."},
                    {"P004", "Phí nhiên liệu", "Tùy theo lượng nhiên liệu thiếu hụt", "Áp dụng khi xe được trả lại với lượng nhiên liệu thấp hơn mức ban đầu."}
                }
            );   
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm phí mới");
                    addButton.addActionListener(e -> addFee());

                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");
                    updateButton.addActionListener(e -> updateFee());
            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        add(banner, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    public void addFee() {
        new FeeInfoDialog("Thêm phí mới").setVisible(true); 
    }

    public void updateFee() {
        new FeeInfoDialog("Cập nhật thông tin phí").setVisible(true);
    }
}