package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.CustomerBLL;
import com.htt.vehiclerental.dto.Customer;

public class ManageCustomersPanel extends JPanel {

    private static final String[] CUSTOMER_COLUMNS = {
        "Số định danh", "Họ và tên", "Số điện thoại", "Địa chỉ"
    };

    private JTextField searchField;
    private JTable table;
    private JComboBox<String> sortComboBox;

    public ManageCustomersPanel() {
        initComponents();
        updateTable();
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

            table = UiKit.createTable(CUSTOMER_COLUMNS, new Object[][] {{""}});
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm khách mới");
                    addButton.addActionListener(e -> addCustomer());

                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");
                    updateButton.addActionListener(e -> updateCustomer());

                JButton deleteButton = UiKit.createSecondaryButton("Xóa khách hàng");
                    deleteButton.addActionListener(e -> deleteCustomer());

            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }   
    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        //sort criteria
        // if (sortComboBox.getSelectedIndex() == 1)
        //

        List<Customer> customers = CustomerBLL.searchCustomers(searchField.getText());

        for (Customer customer : customers) {
            model.addRow(new Object[] {
                customer.getIdentityNumber(),
                customer.getFullName(),
                customer.getPhoneNumber(),
                customer.getAddress()
            });
        }

        table.revalidate();
        table.repaint();
    }

    public void addCustomer() {
        new CustomerInfoDialog("Thêm khách hàng mới").setVisible(true);
        this.updateTable();
    }

    public void updateCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            UiKit.showErrorDialog(this, "Vui lòng chọn một khách hàng để cập nhật thông tin.");
             return;
        }

        String identityNumber = (String) table.getValueAt(selectedRow, 0);
        
        Customer customer = CustomerBLL.getCustomer(identityNumber);
        if (customer == null) {
            UiKit.showErrorDialog(this, "Không thể lấy thông tin khách hàng.");
            return;
        }

        new CustomerInfoDialog("Cập nhật thông tin khách hàng", customer).setVisible(true);
        this.updateTable();
    }

    public void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            UiKit.showErrorDialog(this, "Vui lòng chọn một khách hàng để xóa.");
             return;
        }

        String identityNumber = (String) table.getValueAt(selectedRow, 0);

        //confirm delete
        int confirm = UiKit.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?");
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        //delete customer
        switch (CustomerBLL.deleteCustomer(identityNumber)) {
            case CustomerBLL.INVALID_INPUT:
                UiKit.showErrorDialog(this, "Số định danh không hợp lệ.");
                return;
            case CustomerBLL.NOT_FOUND:
                UiKit.showErrorDialog(this, "Không tìm thấy khách hàng.");
                return;
            case CustomerBLL.RENTAL_EXISTS:
                UiKit.showErrorDialog(this, "Không thể xóa khách hàng có hợp đồng thuê đang tồn tại.");
                return;
            case CustomerBLL.DATABASE_ERROR:
                UiKit.showErrorDialog(this, "Đã xảy ra lỗi khi xóa khách hàng. Vui lòng thử lại sau.");
                return;
        }

        this.updateTable();
    }
}