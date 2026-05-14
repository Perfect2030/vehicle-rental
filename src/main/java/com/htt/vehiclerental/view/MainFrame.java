package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private JPanel sidebar;
    private JPanel mainContent;

    private JPanel manageVehiclesPanel, manageCustomersPanel, manageRentalsPanel, manageFeesPanel, generateReportsPanel;


    public MainFrame() {
        setTitle("Vehicle Rental System");
        setSize(1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        updateSidebar("ManageVehicles");
        add(sidebar, BorderLayout.WEST);

        mainContent = createMainContent();

        add(mainContent, BorderLayout.CENTER);        


    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(6, 1, 0 ,16));
        sidebar.setBackground(UiKit.SIDEBAR);
       
        //add buttons
        JButton btnManageVehicles = UiKit.createSidebarButton("Quản lý xe");
        JButton btnManageCustomers = UiKit.createSidebarButton("Quản lý khách hàng");
        JButton btnManageRentals = UiKit.createSidebarButton("Quản lý thuê xe");
        JButton btnManageFees = UiKit.createSidebarButton("Quản lý phí phát sinh");
        JButton btnGenerateReports = UiKit.createSidebarButton("Thống kê hệ thống");

        btnManageVehicles.addActionListener(e -> showPanel("ManageVehicles"));
        btnManageCustomers.addActionListener(e -> showPanel("ManageCustomers"));
        btnManageRentals.addActionListener(e -> showPanel("ManageRentals"));
        btnManageFees.addActionListener(e -> showPanel("ManageFees"));
        btnGenerateReports.addActionListener(e -> showPanel("GenerateReports"));

        sidebar.add(btnManageVehicles);
        sidebar.add(btnManageCustomers);
        sidebar.add(btnManageRentals);
        sidebar.add(btnManageFees);
        sidebar.add(btnGenerateReports);

        return sidebar;
    }

    public JPanel createMainContent() {
        JPanel mainContent = new JPanel(new CardLayout());
        //add các màn hình

        manageVehiclesPanel = new ManageVehiclesPanel();
        manageCustomersPanel = new ManageCustomersPanel();
        manageRentalsPanel = new ManageRentalsPanel();
        manageFeesPanel = new ManageFeesPanel();
        generateReportsPanel = new GenerateReportsPanel();

        mainContent.add(manageVehiclesPanel, "ManageVehicles");
        mainContent.add(manageCustomersPanel, "ManageCustomers");
        mainContent.add(manageRentalsPanel, "ManageRentals");
        mainContent.add(manageFeesPanel, "ManageFees");
        mainContent.add(generateReportsPanel, "GenerateReports");
        return mainContent;
    }

    public void updateSidebar(String activePanel) {
        int selectedPanelIndex = switch (activePanel) {
                            case "ManageVehicles" -> 0;
                            case "ManageCustomers" -> 1;
                            case "ManageRentals" -> 2;
                            case "ManageFees" -> 3;
                            case "GenerateReports" -> 4;
                            default -> -1;
        };

        for (int i = 0; i < sidebar.getComponentCount(); i++) {
            if (sidebar.getComponent(i) instanceof JButton) {
                JButton button = (JButton) sidebar.getComponent(i);
                if (i == selectedPanelIndex) {
                    button.setBackground(UiKit.PRIMARY);
                } else {
                    button.setBackground(UiKit.SIDEBAR);
                }
            }
        }
    }

    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainContent.getLayout();
        cl.show(mainContent, panelName);
        updateSidebar(panelName);
    }
    
    public void showPanel(String panelName, String keyword) {
        CardLayout cl = (CardLayout) mainContent.getLayout();

        switch (panelName) {
            // case "ManageVehicles":
            //     ((ManageVehiclesPanel) manageVehiclesPanel).searchAndHighlight(keyword);
            //     break;
            // case "ManageCustomers":
            //     ((ManageCustomersPanel) manageCustomersPanel).searchAndHighlight(keyword);
            //     break;
            case "ManageRentals":
                ((ManageRentalsPanel) manageRentalsPanel).setSearchField(keyword);
                break;
            // case "ManageFees":
            //     ((ManageFeesPanel) manageFeesPanel).searchAndHighlight(keyword);
            //     break;
            // case "GenerateReports":
            //     // Không có chức năng tìm kiếm, có thể bỏ qua
            //     break;
        }

        cl.show(mainContent, panelName);
        updateSidebar(panelName);
    }
}
