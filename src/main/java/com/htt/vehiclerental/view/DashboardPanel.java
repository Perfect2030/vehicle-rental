package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel welcomeLabel = new JLabel("Welcome to Vehicle Rental System Dashboard!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(18f));
        add(welcomeLabel);
    }
}
