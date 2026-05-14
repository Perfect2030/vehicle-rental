package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.htt.vehiclerental.bll.CustomerBLL;
import com.htt.vehiclerental.dto.Customer;

public class CustomerInfoDialog extends JDialog {
    private JTextField identityField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField addressField;

    private boolean isEditMode = false;
    private int editingCustomerId = -1;

    public CustomerInfoDialog(String title) {
        this.setTitle("Thông tin khách hàng");
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        
        initComponents(title);
    }

    public CustomerInfoDialog(String title, Customer customer) {
        this.setTitle("Thông tin khách hàng");
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.isEditMode = true;
        this.editingCustomerId = customer.getId();
        
        initComponents(title);

        identityField.setText(customer.getIdentityNumber());
        nameField.setText(customer.getFullName());
        phoneField.setText(customer.getPhoneNumber());
        addressField.setText(customer.getAddress());

        identityField.setEditable(false);
    }

    public void initComponents(String title) {

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

            //center header
            JPanel centerHeader = UiKit.createSectionHeader(title, "");

            //center form
            JPanel centerForm = new JPanel(new GridLayout(4, 1, 16, 16));
            centerForm.setOpaque(false);

            identityField = UiKit.createTextField(12);
            nameField = UiKit.createTextField(12);
            phoneField = UiKit.createTextField(12);
            addressField = UiKit.createTextField(12);

            centerForm.add(UiKit.createFieldBlock("Số định danh", identityField));
            centerForm.add(UiKit.createFieldBlock("Họ và tên", nameField));
            centerForm.add(UiKit.createFieldBlock("Số điện thoại", phoneField));
            centerForm.add(UiKit.createFieldBlock("Địa chỉ", addressField));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
            buttonPanel.setOpaque(false);

                JButton saveButton = UiKit.createPrimaryButton("Lưu thông tin");
                    saveButton.addActionListener(e -> saveInfo());

                JButton cancelButton = UiKit.createSecondaryButton("Hủy");
                    cancelButton.addActionListener(e -> cancel());

            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

        center.add(centerHeader, BorderLayout.NORTH);
        center.add(centerForm, BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        this.add(center);
    }

    public void saveInfo() {
        if (identityField.getText().isEmpty() || nameField.getText().isEmpty() || phoneField.getText().isEmpty() || addressField.getText().isEmpty()) {
            UiKit.showErrorDialog(this, "Vui lòng điền đầy đủ thông tin khách hàng.");
            return;
        }

        Customer newCustomer = new Customer();
        
        newCustomer.setId(editingCustomerId);
        newCustomer.setIdentityNumber(identityField.getText());
        newCustomer.setFullName(nameField.getText());
        newCustomer.setPhoneNumber(phoneField.getText());
        newCustomer.setAddress(addressField.getText());

        if (isEditMode) {
            switch (CustomerBLL.updateCustomer(newCustomer)) {
                case CustomerBLL.SUCCESS:
                    break;
                case CustomerBLL.NOT_FOUND:
                    UiKit.showErrorDialog(this, "Không tìm thấy khách hàng. Vui lòng thử lại.");
                    return;
                case CustomerBLL.INVALID_INPUT:
                    UiKit.showErrorDialog(this, "Thông tin khách hàng không hợp lệ. Vui lòng kiểm tra lại.");
                    return;
                default:
                    UiKit.showErrorDialog(this, "Không thể cập nhật thông tin khách hàng. Vui lòng thử lại.");
                    return;
            }
        } else {
            switch (CustomerBLL.addCustomer(newCustomer)) {
                case CustomerBLL.SUCCESS:
                    break;
                case CustomerBLL.CUSTOMER_EXISTS:
                    UiKit.showErrorDialog(this, "Khách hàng với số định danh này đã tồn tại.");
                    return;
                case CustomerBLL.INVALID_INPUT:
                    UiKit.showErrorDialog(this, "Thông tin khách hàng không hợp lệ. Vui lòng kiểm tra lại.");
                    return;
                default:
                    UiKit.showErrorDialog(this, "Không thể lưu thông tin khách hàng. Vui lòng thử lại.");
                    return;
            }
        }

        this.dispose();
    }

    public void cancel() {
        this.dispose();
    }
}
