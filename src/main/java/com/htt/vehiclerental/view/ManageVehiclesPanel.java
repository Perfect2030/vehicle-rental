package com.htt.vehiclerental.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.htt.vehiclerental.bll.VehicleBLL;
import com.htt.vehiclerental.dto.Vehicle;
import com.htt.vehiclerental.enums.VehicleStatus;
import com.htt.vehiclerental.enums.VehicleType;

import java.awt.*;

public class ManageVehiclesPanel extends JPanel {
        private static final String[] VEHICLE_COLUMNS = {
                        "Id", "Biển số", "Hãng xe", "Mẫu xe", "Loại xe", "Phân khối", "Giá thuê/ngày", "Trạng thái"
        };

        JTextField searchField = UiKit.createTextField(20);
        JComboBox<String> statusComboBox = UiKit.createComboBox(new String[] { "Tất cả", "Sẵn sàng", "Đang cho thuê", "Bảo trì" });
        JComboBox<String> typeComboBox = UiKit.createComboBox(new String[] { "Tất cả", "Xe côn", "Xe số", "Xe ga" });

        JComboBox<String> sortComboBox = UiKit.createComboBox(new String[] { "Mặc định", "Giá thuê tăng dần", "Giá thuê giảm dần", 
                                                                                "Phân khối tăng dần", "Phân khối giảm dần"
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
                JPanel searchBar = new JPanel(new GridLayout(1, 5, 16, 0));
                searchBar.add(UiKit.createFieldBlock("Tìm kiếm", searchField));
                searchBar.add(UiKit.createFieldBlock("Loại xe", typeComboBox));
                searchBar.add(UiKit.createFieldBlock("Trạng thái", statusComboBox));
                searchBar.add(UiKit.createFieldBlock("Sắp xếp theo", sortComboBox));

                JButton btnSearch = UiKit.createPrimaryButton("Tìm kiếm");
                btnSearch.addActionListener(e -> ReloadData());
                searchBar.add(UiKit.createFieldBlock(" ", btnSearch));

                searchBar.setOpaque(false);

                vehicleTable = UiKit.createTable(VEHICLE_COLUMNS, new Object[][] {});
                vehicleTable.removeColumn(vehicleTable.getColumnModel().getColumn(0));

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
                buttonPanel.setOpaque(false);

                JButton addButton = UiKit.createPrimaryButton("Thêm xe mới");
                addButton.addActionListener(e -> addVehicle());
                JButton updateButton = UiKit.createPrimaryButton("Cập nhật thông tin xe");
                updateButton.addActionListener(e -> updateVehicle());
                JButton deleteButton = UiKit.createDangerButton("Xóa xe");
                deleteButton.addActionListener(e -> deleteVehicle());
                JButton createRentalButton = UiKit.createPrimaryButton("Tạo hợp đồng thuê xe");
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

        private List<Vehicle> getVehiclesAfterFilterAndSort() {

                VehicleStatus status = switch (statusComboBox.getSelectedItem().toString()) {
                        case "Sẵn sàng" -> VehicleStatus.AVAILABLE;
                        case "Đang cho thuê" -> VehicleStatus.RENTED;
                        case "Bảo trì" -> VehicleStatus.MAINTENANCE;
                        default -> null;
                };

                VehicleType type = switch (typeComboBox.getSelectedItem().toString()) {
                        case "Xe côn" -> VehicleType.MANUAL;
                        case "Xe số" -> VehicleType.AUTOMATIC;
                        case "Xe ga" -> VehicleType.SCOOTER;
                        default -> null;
                };
                
                return VehicleBLL.getVehiclesAfterFilterAndSort(searchField.getText(), type, status, (String) sortComboBox.getSelectedItem());
        }

        private void ReloadData() {
                DefaultTableModel model = (DefaultTableModel) vehicleTable.getModel();
                model.setRowCount(0); // Xóa tất cả dữ liệu hiện tại

                for (Vehicle vehicle : getVehiclesAfterFilterAndSort()) {
                        model.addRow(new Object[] { 
                                        vehicle.getId(),
                                        vehicle.getLicensePlate(),
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
                new VehicleInfoDialog("Thêm xe mới", -1).setVisible(true);
                this.ReloadData();
        }

        private void updateVehicle() {
                int[] selectedRows = vehicleTable.getSelectedRows();

                if (selectedRows.length == 1 && !vehicleTable.getModel().getValueAt(vehicleTable.convertRowIndexToModel(selectedRows[0]), 7).equals("Đang cho thuê")) {
                        int id = (int) vehicleTable.getModel().getValueAt((int) vehicleTable.convertRowIndexToModel(selectedRows[0]), 0);
                        new VehicleInfoDialog("Cập nhật thông tin xe", id).setVisible(true);
                        this.ReloadData();
                } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe đang không được thuê để cập nhật.", "Lỗi",
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
                                        switch (VehicleBLL.deleteVehicle((int) vehicleTable.getModel().getValueAt(vehicleTable.convertRowIndexToModel(row), 0))) {
                                                case VehicleBLL.SUCCESS:
                                                        JOptionPane.showMessageDialog(this, "Xóa xe thành công.", "Thông báo",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                                        break;
                                                case VehicleBLL.NOT_FOUND:
                                                        JOptionPane.showMessageDialog(this, "Xe không tồn tại.", "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                case VehicleBLL.RENTAL_EXISTS:
                                                        JOptionPane.showMessageDialog(this, "Xe đang được thuê hoặc có đơn thuê trong tương lai. Vui lòng hoàn tất giao dịch thuê trước khi xóa.", "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                                case VehicleBLL.DATABASE_ERROR:
                                                        JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu.", "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                        
                                                default:
                                                        JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng thử lại sau!", "Lỗi",
                                                                JOptionPane.ERROR_MESSAGE);
                                                        break;
                                        }
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

                if (selectedRows.length == 1 && vehicleTable.getModel().getValueAt(vehicleTable.convertRowIndexToModel(selectedRows[0]), 7).equals("Sẵn sàng")) {
                        int id = (int) vehicleTable.getModel().getValueAt(vehicleTable.convertRowIndexToModel(selectedRows[0]), 0);
                        new CreateRentalDialog("Tạo hợp đồng thuê xe", id).setVisible(true);
                } else {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn một xe ở trạng thái sẵn sàng để tạo hợp đồng thuê.", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }

}
