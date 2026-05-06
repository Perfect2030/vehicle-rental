package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageRentalsPanel extends JPanel {
    public ManageRentalsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Manage Rentals");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel);
    }
    
}
