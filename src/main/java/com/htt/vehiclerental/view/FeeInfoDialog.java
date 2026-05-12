package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.htt.vehiclerental.bll.ExtraFeeTypeBLL;
import com.htt.vehiclerental.dto.ExtraFeeType;

public class FeeInfoDialog extends JDialog {

    JTextField codeField;
    JTextField nameField;
    JTextField priceField;
    JTextField descriptionField;

    private boolean isEditMode = false;
    
    public FeeInfoDialog(String title) {
        this.setTitle("Thông tin phí");
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        initComponents(title);

        codeField.setEnabled(false);
    }

    public FeeInfoDialog(String title, ExtraFeeType fee) {
        this.setTitle("Thông tin phí");
        this.setSize(450, 500);
        this.setLocationRelativeTo(null);
        this.setModal(true);

        this.isEditMode = true;

        initComponents(title);

        codeField.setText(String.valueOf(fee.getId()));
        nameField.setText(fee.getName());
        priceField.setText(String.valueOf(fee.getAmount()));
        descriptionField.setText(fee.getDescription());

        codeField.setEditable(false);
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

            codeField = UiKit.createTextField(12);
            nameField = UiKit.createTextField(12);
            priceField = UiKit.createTextField(12);
            descriptionField = UiKit.createTextField(12);

            centerForm.add(UiKit.createFieldBlock("Mã phí", codeField));
            centerForm.add(UiKit.createFieldBlock("Tên phí", nameField));
            centerForm.add(UiKit.createFieldBlock("Giá phạt", priceField));
            centerForm.add(UiKit.createFieldBlock("Mô tả lỗi", descriptionField));

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
        if (isEditMode && (codeField.getText().isEmpty()) || nameField.getText().isEmpty() || priceField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin phí.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ExtraFeeType fee = new ExtraFeeType();
        fee.setId(Integer.parseInt(codeField.getText().isEmpty() ? "0" : codeField.getText()));
        fee.setName(nameField.getText());
        fee.setAmount(Integer.parseInt(priceField.getText()));
        fee.setDescription(descriptionField.getText());

        if (!isEditMode)
            switch (ExtraFeeTypeBLL.addExtraFeeType(fee)) {
                case ExtraFeeTypeBLL.SUCCESS:
                    //JOptionPane.showMessageDialog(this, "Thêm phí thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case ExtraFeeTypeBLL.FEE_EXISTS:
                    JOptionPane.showMessageDialog(this, "Mã phí đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                case ExtraFeeTypeBLL.INVALID_INPUT:
                    JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi thêm phí.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        else
            switch (ExtraFeeTypeBLL.updateExtraFeeType(fee)) {
                case ExtraFeeTypeBLL.SUCCESS:
                    //JOptionPane.showMessageDialog(this, "Cập nhật phí thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case ExtraFeeTypeBLL.NOT_FOUND:
                    JOptionPane.showMessageDialog(this, "Không tìm thấy phí. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                case ExtraFeeTypeBLL.INVALID_INPUT:
                    JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật phí.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        this.dispose();
    }
    public void cancel() {
        this.dispose();
    }
    
}
