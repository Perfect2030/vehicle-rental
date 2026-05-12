package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class AddExtraFeeDialog extends JDialog {
    
    JComboBox<String> feeTypeComboBox;
    JTextField type, price, amount, description;
    JButton addButton, cancelButton;
    public AddExtraFeeDialog(Object rentalId) {
        setTitle("Thêm phí phát sinh");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        feeTypeComboBox = UiKit.createComboBox("Phí trễ hạn", "Phí hư hỏng", "Phí khác");
        JPanel cbbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Loại phí phát sinh");
        cbbPanel.add(titleLabel);
        cbbPanel.add(feeTypeComboBox);
        add(cbbPanel, BorderLayout.NORTH);

        JPanel body = UiKit.createSurfacePanel();
        body.setLayout(new GridLayout(4, 1, 16, 16));
        
        type = UiKit.createTextField(20);
        price = UiKit.createTextField(20);
        amount = UiKit.createTextField(20);
        description = UiKit.createTextField(20);

        body.add(UiKit.createFieldBlock("Loại phí", type));
        body.add(UiKit.createFieldBlock("Giá", price));
        body.add(UiKit.createFieldBlock("Số lượng", amount));
        body.add(UiKit.createFieldBlock("Mô tả", description));

        add(body, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = UiKit.createSecondaryButton("Thêm");
        cancelButton = UiKit.createSecondaryButton("Hủy");

        cancelButton.addActionListener(e -> dispose());

        btnPanel.add(addButton);
        btnPanel.add(cancelButton);

        

        add(btnPanel, BorderLayout.SOUTH);
    }
}
