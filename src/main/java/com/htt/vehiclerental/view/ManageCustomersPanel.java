package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.CustomerBLL;
import com.htt.vehiclerental.dto.Customer;
import com.htt.vehiclerental.dto.CustomerDetail;

public class ManageCustomersPanel extends JPanel {

    private static final String[] CUSTOMER_COLUMNS = {
        "Mã khách hàng", "Số định danh", "Họ và tên", "Số điện thoại", "Địa chỉ"
    };

    private JTextField searchField;
    private JTable table;
    private JComboBox<String> sortComboBox;
    private JLabel totalCustomersLabel, totalRentingLabel;

    private JButton viewDetailsButton, addButton, updateButton, deleteButton;

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

            JPanel info = new JPanel(new GridLayout(1, 2, 16, 16));
            info.setOpaque(false);

            totalCustomersLabel = UiKit.createMetricValueLabel("0"); 
            totalRentingLabel = UiKit.createMetricValueLabel("0");

            JPanel card1 = UiKit.createMetricCard("Tổng số khách hàng", totalCustomersLabel, "", UiKit.PRIMARY);
            JPanel card2 = UiKit.createMetricCard("Số khách hàng đang thuê", totalRentingLabel, "", UiKit.WARNING);

            info.add(card1);
            info.add(card2);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(info, BorderLayout.CENTER);

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());


            JPanel searchBar = new JPanel(new GridLayout(1, 3, 16, 0));

                searchField = UiKit.createTextField(20);
                sortComboBox = UiKit.createComboBox(new String[] {"Mặc định", "Họ và tên A-Z", "Họ và tên Z-A"});
                JButton searchButton = UiKit.createPrimaryButton("Tìm kiếm");
                    searchButton.addActionListener(e -> updateTable());

                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp", sortComboBox));
                searchBar.add(UiKit.createFieldBlock(" ", searchButton));

            searchBar.setOpaque(false);

            table = UiKit.createTable(CUSTOMER_COLUMNS, new Object[][] {{""}});
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            table.getSelectionModel().addListSelectionListener(e -> updateButtonStates());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                viewDetailsButton = UiKit.createPrimaryButton("Xem chi tiết");
                    viewDetailsButton.addActionListener(e -> viewCustomerDetails());

                addButton = UiKit.createPrimaryButton("Thêm khách mới");
                    addButton.addActionListener(e -> addCustomer());

                updateButton = UiKit.createPrimaryButton("Cập nhật thông tin");
                    updateButton.addActionListener(e -> updateCustomer());

                deleteButton = UiKit.createDangerButton("Xóa khách hàng");
                    deleteButton.addActionListener(e -> deleteCustomer());

            buttonPanel.add(viewDetailsButton);
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

        List<Customer> customers = CustomerBLL.searchCustomers(searchField.getText(), sortComboBox.getSelectedIndex());

        for (Customer customer : customers) {
            model.addRow(new Object[] {
                customer.getId(),
                customer.getIdentityNumber(),
                customer.getFullName(),
                customer.getPhoneNumber(),
                customer.getAddress()
            });
        }

        //update metrics
        totalCustomersLabel.setText(String.format("%,d", CustomerBLL.getCustomerCount()));
        totalRentingLabel.setText(String.format("%,d", CustomerBLL.getRentingCustomerCount()));

        table.revalidate();
        table.repaint();

        updateButtonStates();
    }

    public void updateButtonStates() {
        boolean hasSelection = table.getSelectedRowCount() > 0;
        if (hasSelection) {
            viewDetailsButton.setEnabled(true);
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            viewDetailsButton.setEnabled(false);
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    public void addCustomer() {
        new CustomerInfoDialog("Thêm khách hàng mới").setVisible(true);
        this.updateTable();
    }

    public void updateCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để cập nhật thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int customerId = (int) table.getValueAt(selectedRow, 0);
        System.out.println(customerId);
        
        Customer customer = CustomerBLL.getCustomer(customerId);
        if (customer == null) {
            JOptionPane.showMessageDialog(this, "Không thể lấy thông tin khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new CustomerInfoDialog("Cập nhật thông tin khách hàng", customer).setVisible(true);
        this.updateTable();
    }

    public void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int customerId = (int) table.getValueAt(selectedRow, 0);

        //confirm delete
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        //delete customer
        switch (CustomerBLL.deleteCustomer(customerId)) {
            case CustomerBLL.SUCCESS:
                break;
            case CustomerBLL.INVALID_INPUT:
                JOptionPane.showMessageDialog(this, "Mã khách hàng không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            case CustomerBLL.NOT_FOUND:
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            default:
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa khách hàng. Vui lòng thử lại sau.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
        }

        this.updateTable();
    }

    public void viewCustomerDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng để xem chi tiết.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int customerId = (int) table.getValueAt(selectedRow, 0);

        CustomerDetail customerDetail = CustomerBLL.getCustomerDetail(customerId);

        if (customerDetail == null) {
            JOptionPane.showMessageDialog(this, "Không thể lấy thông tin khách hàng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MainFrame mainFrame = (MainFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        new CustomerDetailsDialog(mainFrame, customerDetail).setVisible(true);
    }
}   