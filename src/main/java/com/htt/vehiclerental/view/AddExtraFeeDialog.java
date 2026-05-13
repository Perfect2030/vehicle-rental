package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.dto.ExtraFeeType;
import com.htt.vehiclerental.dto.RentalExtraFee;

import java.util.List;

public class AddExtraFeeDialog extends JDialog {
    private int rentalId;
    JComboBox<String> feeTypeComboBox;
    JTextField type, price, description;
    JButton addButton, cancelButton;
    List<ExtraFeeType> extraFeeTypes;
    public AddExtraFeeDialog(Object rentalId) {
        this.rentalId = (Integer) rentalId;
        setTitle("Thêm phí phát sinh");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setModal(true);
        initComponents();
        loadCBBData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        feeTypeComboBox = UiKit.createComboBox("Phí khác");

        //add action listener for feeTypeComboBox
        feeTypeComboBox.addActionListener(e -> {
            int selectedIndex = feeTypeComboBox.getSelectedIndex();
            if(selectedIndex == 0) {
                type.setText("");
                price.setText("");
                description.setText("");
                return;
            }
            if (selectedIndex >= 0 && extraFeeTypes != null && selectedIndex < extraFeeTypes.size()) {
                ExtraFeeType selectedType = extraFeeTypes.get(selectedIndex - 1); // -1 because of the default "Phí khác" item
                type.setText(selectedType.getName());
                price.setText(String.valueOf(selectedType.getAmount()));
                description.setText(selectedType.getDescription());
            }
        });

        JPanel cbbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Loại phí phát sinh");
        cbbPanel.add(titleLabel);
        cbbPanel.add(feeTypeComboBox);
        add(cbbPanel, BorderLayout.NORTH);

        JPanel body = UiKit.createSurfacePanel();
        body.setLayout(new GridLayout(3, 1, 16, 16));
        
        type = UiKit.createTextField(20);
        price = UiKit.createTextField(20);
        description = UiKit.createTextField(20);

        body.add(UiKit.createFieldBlock("Loại phí", type));
        body.add(UiKit.createFieldBlock("Giá (VND)", price));
        body.add(UiKit.createFieldBlock("Mô tả", description));

        add(body, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = UiKit.createSecondaryButton("Thêm");
        cancelButton = UiKit.createSecondaryButton("Hủy");

        addButton.addActionListener(e -> addExtraFee());

        cancelButton.addActionListener(e -> dispose());

        btnPanel.add(addButton);
        btnPanel.add(cancelButton);

        

        add(btnPanel, BorderLayout.SOUTH);
    }
    private void loadCBBData() {
        extraFeeTypes = RentalBLL.getAllExtraFeeTypes();
        if (extraFeeTypes == null) return;
        for (var type : extraFeeTypes) {
            feeTypeComboBox.addItem(type.getName());
        }
    }

    private void addExtraFee(){
        try {
            int amt = Integer.parseInt(price.getText().trim());
            String nameFee = type.getText().trim();
            var rentalExtraFee = new RentalExtraFee(0, this.rentalId, nameFee, amt, description.getText().trim());
            boolean ok = RentalBLL.addRentalExtraFee(rentalExtraFee);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Thêm phí thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá phải là số nguyên hợp lệ", "Lỗi nhập", JOptionPane.WARNING_MESSAGE);
        }
    }
}
