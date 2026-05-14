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

import com.htt.vehiclerental.bll.ExtraFeeTypeBLL;
import com.htt.vehiclerental.dto.ExtraFeeType;

public class ManageFeesPanel extends JPanel {

    private static final String[] FEE_COLUMNS = {
        "Mã phí", "Tên phí", "Giá phạt", "Mô tả lỗi"
    };

    private JTable table;
    private JTextField searchField;
    private JComboBox<String> sortComboBox;

    private JButton addButton, updateButton, deleteButton;

    public ManageFeesPanel() {
        initComponents();
        updateTable();
        updateButtonStates();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

        //north
        JPanel banner = UiKit.createInfoBanner("Quản lý phí", "Tạo và quản lý thông tin phí.", UiKit.INFO);
        banner.setPreferredSize(new Dimension(0, 100));

        //center 
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());


            JPanel searchBar = new JPanel(new GridLayout(1, 3, 16, 0));

                searchField = UiKit.createTextField(20);
                sortComboBox = UiKit.createComboBox(new String[] {"Mặc định", "Tên phí A-Z", "Tên phí Z-A", "Giá phạt tăng dần", "Giá phạt giảm dần"});

                JButton searchButton = UiKit.createPrimaryButton("Tìm kiếm");
                searchButton.addActionListener(e -> updateTable());

                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp", sortComboBox));
                searchBar.add(UiKit.createFieldBlock(" ", searchButton));
            searchBar.setOpaque(false);

            table = UiKit.createTable(
                FEE_COLUMNS, new Object[][] {}
            );   
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            table.getSelectionModel().addListSelectionListener(e -> updateButtonStates());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                addButton = UiKit.createPrimaryButton("Thêm phí mới");
                    addButton.addActionListener(e -> addFee());

                updateButton = UiKit.createPrimaryButton("Cập nhật thông tin phí");
                    updateButton.addActionListener(e -> updateFee());

                deleteButton = UiKit.createDangerButton("Xóa phí");
                    deleteButton.addActionListener(e -> deleteFee());

            buttonPanel.add(addButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);
        center.add(buttonPanel, BorderLayout.SOUTH);

        add(banner, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    public void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        //sort criteria
        // if (sortComboBox.getSelectedIndex() == 1)
        //

        List<ExtraFeeType> extraFeeTypes = ExtraFeeTypeBLL.searchExtraFeeTypes(searchField.getText(), sortComboBox.getSelectedIndex());

        for (ExtraFeeType feeType : extraFeeTypes) {
            model.addRow(new Object[] {
                feeType.getId(),
                feeType.getName(),
                String.format("%,d VND", feeType.getAmount()),
                feeType.getDescription()
            });
        }

        table.revalidate();
        table.repaint();
    }

    public void updateButtonStates() {
        boolean hasSelection = table.getSelectedRowCount() > 0;
        
        if (hasSelection) {
            updateButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            updateButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    public void addFee() {
        new FeeInfoDialog("Thêm phí mới").setVisible(true); 
        updateTable();
    }

    public void updateFee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phí để cập nhật thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        
        ExtraFeeType fee = ExtraFeeTypeBLL.getExtraFeeType(id);
        if (fee == null) {
            JOptionPane.showMessageDialog(this, "Không thể lấy thông tin phí.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new FeeInfoDialog("Cập nhật thông tin phí", fee).setVisible(true);
        updateTable();
    }

    public void deleteFee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phí để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        //confirm delete
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phí này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        //delete customer
        switch (ExtraFeeTypeBLL.deleteExtraFeeType(id)) {
            case ExtraFeeTypeBLL.SUCCESS:
                break;
            case ExtraFeeTypeBLL.INVALID_INPUT:
                JOptionPane.showMessageDialog(this, "Số định danh không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            case ExtraFeeTypeBLL.NOT_FOUND:
                JOptionPane.showMessageDialog(this, "Không tìm thấy phí.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            default:
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi xóa phí. Vui lòng thử lại sau.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
        }

        this.updateTable();

        updateTable();
    }
}