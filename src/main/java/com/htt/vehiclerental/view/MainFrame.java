package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;

public class MainFrame extends JFrame {

    private JPanel sidebar;
    private JPanel mainContent;
    private JButton selectedButton = null;


    public MainFrame() {
        setTitle("Vehicle Rental System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);

        mainContent = createMainContent();

        add(mainContent, BorderLayout.CENTER);        


    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(6, 1));
       
        //add buttons
        JButton btnDashboard = UiKit.createSidebarButton("Dashboard");
        JButton btnManageVehicles = UiKit.createSidebarButton("Quản lý xe");
        JButton btnManageCustomers = UiKit.createSidebarButton("Quản lý khách hàng");
        JButton btnManageRentals = UiKit.createSidebarButton("Quản lý thuê xe");
        JButton btnManageFees = UiKit.createSidebarButton("Quản lý phí phát sinh");
        JButton btnGenerateReports = UiKit.createSidebarButton("Báo cáo doanh thu");

        btnDashboard.addActionListener(e -> {
            showPanel("Dashboard");
            handleClick.actionPerformed(e);
        });
        btnManageVehicles.addActionListener(e -> {
            showPanel("ManageVehicles");
            handleClick.actionPerformed(e);
        });
        btnManageCustomers.addActionListener(e -> {
            showPanel("ManageCustomers");
            handleClick.actionPerformed(e);
        });
        btnManageRentals.addActionListener(e -> {
            showPanel("ManageRentals");
            handleClick.actionPerformed(e);
        });
        btnManageFees.addActionListener(e -> {
            showPanel("ManageFees");
            handleClick.actionPerformed(e);
        });
        btnGenerateReports.addActionListener(e -> {
            showPanel("GenerateReports");
            handleClick.actionPerformed(e);
        });

        sidebar.add(btnDashboard);
        sidebar.add(btnManageVehicles);
        sidebar.add(btnManageCustomers);
        sidebar.add(btnManageRentals);
        sidebar.add(btnManageFees);
        sidebar.add(btnGenerateReports);

        return sidebar;
    }

    ActionListener handleClick = e -> {
    JButton clicked = (JButton) e.getSource();

    // reset button cũ
    if (selectedButton != null) {
        UiKit.setSidebarButtonState(selectedButton, false);
    }

    // set button mới
    UiKit.setSidebarButtonState(clicked, true);

    selectedButton = clicked;
};

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
