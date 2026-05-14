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
import java.time.format.DateTimeFormatter;

import com.htt.vehiclerental.bll.RentalBLL;
import com.htt.vehiclerental.dto.RentalView;

public class ManageRentalsPanel extends JPanel {
    private JTextField searchField;
    private JComboBox<String> sortComboBox;
    private JTable table;
   // private DefaultTableModel tableModel;
    private JButton searchButton;
    private JButton detailButton;
    private JButton completedButton;
    private JButton giaoxeButton;
    private JButton huyButton;
    private JPanel info;

    private JLabel totalRentalsLabel, activeRentalsLabel, completedRentalsLabel, overdueRentalsLabel, waitingForPickupLabel, cancelledLabel;

    public ManageRentalsPanel() {
        initComponents();
        updateTable();
    }

    private void initComponents() {
        setBorder(UiKit.createCardBorder());
        setBackground(UiKit.APP_BACKGROUND);
        setLayout(new BorderLayout(0, 16));

         // north
        JPanel northPanel = new JPanel(new BorderLayout(0, 16));
        northPanel.setOpaque(false);

        JPanel banner = UiKit.createInfoBanner("Quản lý thuê xe",
                "Thiết lập và quản lý thông tin thuê xe.", UiKit.INFO);
        banner.setPreferredSize(new Dimension(0, 100));

        info = new JPanel(new GridLayout(1, 6, 16, 16));
        info.setOpaque(false);

        totalRentalsLabel = UiKit.createMetricValueLabel("Đang tải...");
        activeRentalsLabel = UiKit.createMetricValueLabel("Đang tải...");
        completedRentalsLabel = UiKit.createMetricValueLabel("Đang tải...");
        overdueRentalsLabel = UiKit.createMetricValueLabel("Đang tải...");
        waitingForPickupLabel = UiKit.createMetricValueLabel("Đang tải...");
        cancelledLabel = UiKit.createMetricValueLabel("Đang tải...");

        JPanel card1 = UiKit.createMetricCard("Tổng số đơn thuê", totalRentalsLabel, "", UiKit.PRIMARY);
        JPanel card2 = UiKit.createMetricCard("Số đơn đang cho thuê", activeRentalsLabel, "", UiKit.INFO);
        JPanel card3 = UiKit.createMetricCard("Số đơn đã hoàn thành", completedRentalsLabel, "", UiKit.SUCCESS);
        JPanel card4 = UiKit.createMetricCard("Số đơn quá hạn", overdueRentalsLabel, "", UiKit.WARNING);
        JPanel card5 = UiKit.createMetricCard("Số đơn chờ giao xe", waitingForPickupLabel, "", UiKit.WARNING);
        JPanel card6 = UiKit.createMetricCard("Số đơn đã hủy", cancelledLabel, "", UiKit.DANGER);


        info.add(card1);
        info.add(card2);
        info.add(card3);
        info.add(card4);
        info.add(card5);
        info.add(card6);

        northPanel.add(banner, BorderLayout.NORTH);
        northPanel.add(info, BorderLayout.CENTER);

        // center
        JPanel center = UiKit.createSurfacePanel();
        center.setLayout(new BorderLayout(16, 16));
        center.setBorder(UiKit.createCardBorder());

        //center north
        JPanel searchBar = new JPanel(new GridLayout(1, 3, 16, 0));
        searchBar.setOpaque(false);

        searchField = UiKit.createTextField(20);
        sortComboBox = UiKit.createComboBox(new String[] {"Tất cả","Đã tạo", "Đang cho thuê", "Đã hoàn thành","Đã hủy" });
        searchButton = UiKit.createPrimaryButton("Tìm kiếm");

        searchButton.addActionListener(e -> updateTable());

        searchBar.add(UiKit.createFieldBlock("Tìm Kiếm", searchField));
        searchBar.add(UiKit.createFieldBlock("Trạng thái", sortComboBox));
        searchBar.add(UiKit.createFieldBlock(" ", searchButton));


        table = UiKit.createTable(
        new String[] { "Mã số thuê", "Tên khách hàng", "Biển số xe", "Hãng xe", "Mẫu xe", "Ngày thuê", "Ngày trả dự kiến", "Trạng thái" },
        new Object[0][0]);

        center.add(searchBar, BorderLayout.NORTH);
        center.add(UiKit.createTableScrollPane(table), BorderLayout.CENTER);


        //SOUTH
        JPanel centerSouth = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        centerSouth.setOpaque(false);
        detailButton = UiKit.createPrimaryButton("Xem chi tiết");
        completedButton = UiKit.createPrimaryButton("Trả xe");
        giaoxeButton = UiKit.createPrimaryButton("Giao xe");
        huyButton = UiKit.createSecondaryButton("Hủy đơn thuê");

        // add action listeners
        // truyền vào id của thuê xe để hiển thị chi tiết trong dialog ( cột dầu tiên của table )
        detailButton.addActionListener(e -> {
            new RentalDetailDialog(table.getValueAt(table.getSelectedRow(), 0)).setVisible(true);
        });
        completedButton.addActionListener(e -> {new RentalCompletionDialog(table.getValueAt(table.getSelectedRow(), 0)).setVisible(true);
                                                    updateTable();
                                                });

        giaoxeButton.addActionListener(e -> {
            // gọi hàm giao xe trong bll với id của thuê xe
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn giao xe cho đơn thuê này?", "Xác nhận giao xe", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
            boolean success = RentalBLL.startRental((Integer) table.getValueAt(table.getSelectedRow(), 0));
            if (success) {                JOptionPane.showMessageDialog(this, "Giao xe thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Giao xe thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            updateTable();
        });

        huyButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy đơn thuê này?", "Xác nhận hủy", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = RentalBLL.cancelRental((Integer) table.getValueAt(table.getSelectedRow(), 0));
                if (success) {
                    JOptionPane.showMessageDialog(this, "Hủy đơn thuê thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Hủy đơn thuê thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                updateTable();
            }
        });

        detailButton.setEnabled(false);
        completedButton.setEnabled(false);
        giaoxeButton.setEnabled(false);
        huyButton.setEnabled(false);

        table.getSelectionModel().addListSelectionListener(e -> {
            boolean isSelected = table.getSelectedRow() != -1;
            detailButton.setEnabled(isSelected);

            if (!isSelected) {
                completedButton.setEnabled(false);
                return;
            }

            String status = (String) table.getValueAt(table.getSelectedRow(), 7);
            completedButton.setEnabled(status.equals("ACTIVE"));
            giaoxeButton.setEnabled(status.equals("CREATED"));
            huyButton.setEnabled(status.equals("CREATED"));
        });

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        centerSouth.add(detailButton);
        centerSouth.add(completedButton);
        centerSouth.add(giaoxeButton);
        centerSouth.add(huyButton);
        center.add(centerSouth, BorderLayout.SOUTH);

        // add to main panel
        add(northPanel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }
    
    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<RentalView> rentals = RentalBLL.searchRentalsViews(searchField.getText(), sortComboBox.getSelectedItem().toString());
        for (RentalView row : rentals) {
            model.addRow(new Object[] {
                row.getRentalId(),
                row.getCustomerName(),
                row.getLicensePlate(),
                row.getBrand(),
                row.getModel(),
                row.getStartTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                row.getExpectedReturnTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                row.getStatus()
            });
        }    

        totalRentalsLabel.setText(String.format("%,d", RentalBLL.getTotalRentals()));
        activeRentalsLabel.setText(String.format("%,d", RentalBLL.getActiveRentals()));
        completedRentalsLabel.setText(String.format("%,d", RentalBLL.getCompletedRentals()));
        overdueRentalsLabel.setText(String.format("%,d", RentalBLL.getOverdueRentals()));
        waitingForPickupLabel.setText(String.format("%,d", RentalBLL.getWaitingForPickupRentals()));
        cancelledLabel.setText(String.format("%,d", RentalBLL.getCancelledRentals()));
 
    }

    public void setSearchField(String keyword) {
        searchField.setText(keyword);
        updateTable();
    }
}
