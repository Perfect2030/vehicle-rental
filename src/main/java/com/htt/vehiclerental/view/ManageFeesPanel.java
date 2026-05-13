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

    public ManageFeesPanel() {
        initComponents();
        updateTable();
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

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm phí mới");
                    addButton.addActionListener(e -> addFee());

                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin phí");
                    updateButton.addActionListener(e -> updateFee());

                JButton deleteButton = UiKit.createDangerButton("Xóa phí");
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
                feeType.getAmount(),
                feeType.getDescription()
            });
        }

        table.revalidate();
        table.repaint();
    }

    public void addFee() {
        new FeeInfoDialog("Thêm phí mới").setVisible(true); 
        updateTable();
    }

    public void updateFee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            UiKit.showErrorDialog(this, "Vui lòng chọn một phí để cập nhật thông tin.");
             return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        
        ExtraFeeType fee = ExtraFeeTypeBLL.getExtraFeeType(id);
        if (fee == null) {
            UiKit.showErrorDialog(this, "Không thể lấy thông tin phí.");
            return;
        }

        new FeeInfoDialog("Cập nhật thông tin phí", fee).setVisible(true);
        updateTable();
    }

    public void deleteFee() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            UiKit.showErrorDialog(this, "Vui lòng chọn một phí để xóa.");
             return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        //confirm delete
        int confirm = UiKit.showConfirmDialog(this, "Bạn có chắc muốn xóa phí này?");
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