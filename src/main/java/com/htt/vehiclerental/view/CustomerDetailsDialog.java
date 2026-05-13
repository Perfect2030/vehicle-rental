package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.htt.vehiclerental.dto.CustomerDetail;

public class CustomerDetailsDialog extends JDialog {

    private JTextField identityField, nameField, phoneField, addressField, rentCountField;
    private JTable table;
    private static final String[] RENTING_COLUMNS = {
        "Mã số thuê", "Tên khách hàng", "Biển số xe", "Hãng xe", "Mẫu xe", "Ngày thuê", "Ngày trả dự kiến", "Trạng thái" 
    };

    public CustomerDetailsDialog(CustomerDetail customerDetail) {
        setTitle("Chi tiết khách hàng");
        setModal(true);
        setSize(400, 450);
        setLocationRelativeTo(null);

        initComponents("Thông tin khách hàng");

        identityField.setText(customerDetail.getIdentityNumber());
        nameField.setText(customerDetail.getFullName());
        phoneField.setText(customerDetail.getPhoneNumber());
        addressField.setText(customerDetail.getAddress());
        rentCountField.setText(String.valueOf(customerDetail.getTotalRentals()));
        // Disable editing
        identityField.setEditable(false);
        nameField.setEditable(false);
        phoneField.setEditable(false);
        addressField.setEditable(false);
        rentCountField.setEditable(false);

    }

    public void initComponents(String title) {
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

            //header
            JPanel centerHeader = UiKit.createSectionHeader(title, "");

            JPanel centerPanel = new JPanel(new BorderLayout(16, 16));
                //form
                JPanel centerForm = new JPanel(new GridLayout(3, 2, 16, 16));
                centerForm.setOpaque(false);

                identityField = UiKit.createTextField(12);
                nameField = UiKit.createTextField(12);
                phoneField = UiKit.createTextField(12);
                addressField = UiKit.createTextField(12);
                rentCountField = UiKit.createTextField(12);

                centerForm.add(UiKit.createFieldBlock("Số định danh", identityField));
                centerForm.add(UiKit.createFieldBlock("Họ và tên", nameField));
                centerForm.add(UiKit.createFieldBlock("Số điện thoại", phoneField));
                centerForm.add(UiKit.createFieldBlock("Địa chỉ", addressField));
                centerForm.add(UiKit.createFieldBlock("Tổng số lần thuê", rentCountField));

                //table
                table = UiKit.createTable(RENTING_COLUMNS, new Object[][] {{""}});
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            centerPanel.add(centerForm, BorderLayout.NORTH);
            centerPanel.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 0));
            buttonPanel.setOpaque(false);

                JButton closeButton = UiKit.createPrimaryButton("Đóng");
                    closeButton.addActionListener(e -> dispose());

            buttonPanel.add(closeButton);

        center.add(centerHeader, BorderLayout.NORTH);
        center.add(centerPanel, BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        this.add(center);
    }
}
