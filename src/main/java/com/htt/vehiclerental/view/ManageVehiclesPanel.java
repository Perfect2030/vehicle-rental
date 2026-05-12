package com.htt.vehiclerental.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.Vehicle;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

public class ManageVehiclesPanel extends JPanel {
        private static final String[] VEHICLE_COLUMNS = {
                        "Biển số", "Hãng xe", "Mẫu xe", "Loại xe", "Phân khối", "Giá thuê/ngày", "Trạng thái"
        };

        JTextField searchField = UiKit.createTextField(20);
        JComboBox<String> statusComboBox = UiKit
                        .createComboBox(new String[] { "Tất cả", "Sẵn sàng", "Đang cho thuê", "Bảo trì" });
        JComboBox<String> sortComboBox = UiKit.createComboBox(new String[] {
                        "Mặc định", "Giá thuê tăng dần", "Giá thuê giảm dần",
                        "Phân khối tăng dần",
                        "Phân khối giảm dần"
        });

        private JTable vehicleTable;

        public ManageVehiclesPanel() {
                initComponents();
        }

        private void initComponents() {
                setBorder(UiKit.createCardBorder());
                setBackground(UiKit.APP_BACKGROUND);
                setLayout(new BorderLayout(0, 16));

                // north
                JPanel northPanel = new JPanel(new BorderLayout(0, 16));
                northPanel.setOpaque(false);

                JPanel banner = UiKit.createInfoBanner("Quản lý xe",
                                "Thiết lập và quản lý thông tin xe.", UiKit.INFO);
                banner.setPreferredSize(new Dimension(0, 100));

                JPanel info = new JPanel(new GridLayout(1, 4, 16, 0));
                info.setOpaque(false);

                String totalVehicles = "120";
                String rentedVehicles = "5";
                String availableVehicles = "105";
                String maintenanceVehicles = "10";
                JPanel card1 = UiKit.createMetricCard("Tổng số xe", totalVehicles, "", UiKit.PRIMARY);
                JPanel card2 = UiKit.createMetricCard("Số xe đang cho thuê", rentedVehicles, "", UiKit.WARNING);
                JPanel card4 = UiKit.createMetricCard("Số xe đang bảo trì", maintenanceVehicles, "", UiKit.INFO);
                JPanel card3 = UiKit.createMetricCard("Số xe sẵn có", availableVehicles, "", UiKit.SUCCESS);

                info.add(card1);
                info.add(card2);
                info.add(card3);
                info.add(card4);

                northPanel.add(banner, BorderLayout.NORTH);
                northPanel.add(info, BorderLayout.CENTER);

                //
                JPanel centerPanel = UiKit.createSurfacePanel();
                centerPanel.setLayout(new BorderLayout(16, 16));
                centerPanel.setBorder(UiKit.createCardBorder());

                // search and filter
                JPanel searchBar = new JPanel(new GridLayout(1, 3, 16, 0));
                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Trạng thái", statusComboBox));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp theo", sortComboBox));
                searchBar.setOpaque(false);

                vehicleTable = UiKit.createTable(VEHICLE_COLUMNS, new Object[][] {});

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm xe mới");
                addButton.addActionListener(e -> addVehicle());
                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin xe");
                updateButton.addActionListener(e -> updateVehicle());
                JButton deleteButton = UiKit.createDangerButton("Xóa xe");
                deleteButton.addActionListener(e -> deleteVehicle());
                JButton createRentalButton = UiKit.createPrimaryButton("Tạo hợp đồng thuê");
                createRentalButton.addActionListener(e -> createRental());

                buttonPanel.add(addButton);
                buttonPanel.add(updateButton);
                buttonPanel.add(deleteButton);
                buttonPanel.add(createRentalButton);

                centerPanel.add(searchBar, BorderLayout.NORTH);
                centerPanel.add(UiKit.createTableScrollPane(vehicleTable), BorderLayout.CENTER);
                centerPanel.add(buttonPanel, BorderLayout.SOUTH);

                //
                add(northPanel, BorderLayout.NORTH);
                add(centerPanel, BorderLayout.CENTER);
                ReloadData();
        }

        private void ReloadData() {
                DefaultTableModel model = (DefaultTableModel) vehicleTable.getModel();
                model.setRowCount(0); // Xóa tất cả dữ liệu hiện tại

                for (Vehicle vehicle : VehicleBLL.getAllVehicles()) {
                        model.addRow(new Object[] { vehicle.getLicensePlate(),
                                        vehicle.getBrand(),
                                        vehicle.getModel(),
                                        vehicle.getVehicleType().getDisplayName(),
                                        vehicle.getDisplacement(),
                                        vehicle.getPricePerDay(),
                                        vehicle.getStatusEnum().getDisplayName()
                        });
                }

                model.fireTableDataChanged();
        }

        private void addVehicle() {
                new VehicleInfoDialog("Thêm xe mới", null).setVisible(true);
                this.ReloadData();
        }

        private void updateVehicle() {
                int[] selectedRows = vehicleTable.getSelectedRows();

                if (selectedRows.length == 1) {
                        String licensePlate = (String) vehicleTable.getValueAt(selectedRows[0], 0);
                        new VehicleInfoDialog("Cập nhật thông tin xe", licensePlate).setVisible(true);
                        this.ReloadData();
                } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe để cập nhật.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void deleteVehicle() {
                int[] selectedRows = vehicleTable.getSelectedRows();

                if (selectedRows.length > 0) {
                        int confirm = JOptionPane.showConfirmDialog(this,
                                        "Bạn có chắc chắn muốn xóa " + selectedRows.length + " xe đã chọn?", "Xác nhận",
                                        JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                                for (int row : selectedRows) {
                                        VehicleBLL.deleteVehicle((String) vehicleTable.getValueAt(row, 0));
                                }
                                this.ReloadData();
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe để xóa.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

        private void createRental() {
                int[] selectedRows = vehicleTable.getSelectedRows();

                if (selectedRows.length == 1) {
                        String licensePlate = (String) vehicleTable.getValueAt(selectedRows[0], 0);
                        // new RentalInfoDialog("Tạo hợp đồng thuê", licensePlate).setVisible(true);
                } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe để tạo hợp đồng thuê.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

}
