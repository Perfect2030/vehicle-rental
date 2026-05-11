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
import javax.swing.table.DefaultTableModel;

public class ManageCustomersPanel extends JPanel {

    private static final String[] CUSTOMER_COLUMNS = {
        "Số định danh", "Họ và tên", "Số điện thoại", "Địa chỉ"
    };

    private JTextField searchField;
    private JTable table;
    private JComboBox<String> sortComboBox;

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
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());


            JPanel searchBar = new JPanel(new GridLayout(1, 2, 16, 0));

                searchField = UiKit.createTextField(20);
                sortComboBox = UiKit.createComboBox(new String[] {"Mặc định", "Tên A-Z", "Tên Z-A"});

                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp", sortComboBox));

            searchBar.setOpaque(false);

            table = UiKit.createTable(CUSTOMER_COLUMNS, getSampleCustomers());
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm khách mới");
                    addButton.addActionListener(e -> addCustomer());

                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");
                    updateButton.addActionListener(e -> updateCustomer());

            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    public void addCustomer() {
        new CustomerInfoDialog("Thêm khách hàng mới").setVisible(true);
        this.updateTable();
    }

    public void updateCustomer() {
        new CustomerInfoDialog("Cập nhật thông tin khách hàng").setVisible(true);
        this.updateTable();
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Object[] row : getUpdatedCustomers()) {
            model.addRow(row);
        }

        table.revalidate();
        table.repaint();
    }

    private Object[][] getSampleCustomers() {
        return new Object[][] {
            {"123456789012", "Nguyễn Văn A", "0123456789", "123 Đường ABC, Quận 1"},
            {"987654321098", "Trần Thị B", "0987654321", "456 Đường XYZ, Quận 2"},
            {"555555555555", "Lê Văn C", "0555555555", "789 Đường DEF, Quận 3"},
        };
    }

    private Object[][] getUpdatedCustomers() {
        return new Object[][] {
            {"123456789012", "Nguyễn Văn AABC", "0123456789", "123 Đường ABC, Quận 1"},
            {"987654321098", "Trần Thị B", "0987654321", "456 Đường XYZ, Quận 2"},
            {"555555555555", "Lê Văn C", "0555555555", "789 Đường DEF, Quận 3"},
        };
    }
}