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

    public MainFrame() {
        setTitle("Vehicle Rental System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        mainContent = createMainContent();

        add(mainContent, BorderLayout.CENTER);        


    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(7, 1));
        //add buttons
        JButton btnDashboard = new JButton("Dashboard");
        JButton btnManageVehicles = new JButton("Quản lý xe");
        JButton btnManageCustomers = new JButton("Quản lý khách hàng");
        JButton btnManageRentals = new JButton("Quản lý thuê xe");
        JButton btnManageFees = new JButton("Quản lý phí phát sinh");
        JButton btnGenerateReports = new JButton("Báo cáo doanh thu");

        btnDashboard.addActionListener(e -> showPanel("Dashboard"));
        btnManageVehicles.addActionListener(e -> showPanel("ManageVehicles"));
        btnManageCustomers.addActionListener(e -> showPanel("ManageCustomers"));
        btnManageRentals.addActionListener(e -> showPanel("ManageRentals"));
        btnManageFees.addActionListener(e -> showPanel("ManageFees"));
        btnGenerateReports.addActionListener(e -> showPanel("GenerateReports"));

        sidebar.add(btnDashboard);
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

        mainContent.add(new DashboardPanel(), "Dashboard");
        mainContent.add(new ManageVehiclesPanel(), "ManageVehicles");
        mainContent.add(new ManageCustomersPanel(), "ManageCustomers");
        mainContent.add(new ManageRentalsPanel(), "ManageRentals");
        mainContent.add(new ManageFeesPanel(), "ManageFees");
        mainContent.add(new GenerateReportsPanel(), "GenerateReports");

        return mainContent;
    }

    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainContent.getLayout();
        cl.show(mainContent, panelName);
    }
    
}
