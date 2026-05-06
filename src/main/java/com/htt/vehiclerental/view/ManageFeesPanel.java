package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageFeesPanel extends JPanel {
    public ManageFeesPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Manage Fees");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel);
    }
    
}
