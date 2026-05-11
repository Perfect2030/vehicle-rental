package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class FeeInfoDialog extends JDialog {
    

    public FeeInfoDialog(String title) {
        this.setTitle("Thông tin phí");
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        initComponents(title);
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

            centerForm.add(UiKit.createFieldBlock("Mã phí", UiKit.createTextField(12)));
            centerForm.add(UiKit.createFieldBlock("Tên phí", UiKit.createTextField(12)));
            centerForm.add(UiKit.createFieldBlock("Giá phạt", UiKit.createTextField(12)));
            centerForm.add(UiKit.createFieldBlock("Mô tả lỗi", UiKit.createTextField(12)));

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

    }

    public void cancel() {
        this.dispose();
    }
    
}
