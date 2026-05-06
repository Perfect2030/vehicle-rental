package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageCustomersPanel extends JPanel {
    public ManageCustomersPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Manage Customers");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel);
    }
    
}