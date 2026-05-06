package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageVehiclesPanel extends JPanel {
    public ManageVehiclesPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = UiKit.createSectionTitleLabel("ManageVehiclesPanel");
        add(titleLabel);
    }
    
}
