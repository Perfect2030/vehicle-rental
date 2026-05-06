package com.htt.vehiclerental.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenerateReportsPanel extends JPanel {
    public GenerateReportsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Generate Reports");
        titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
        add(titleLabel);
    }

    
}
