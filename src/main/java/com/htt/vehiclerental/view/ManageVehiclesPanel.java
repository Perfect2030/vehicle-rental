package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageVehiclesPanel extends JPanel {
    public ManageVehiclesPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Manage Vehicles");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel);
    }
    
}
