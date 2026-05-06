package com.htt.vehiclerental;

import com.htt.vehiclerental.view.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            System.out.println("Starting Vehicle Rental Application...");
            new MainFrame().setVisible(true);
        });
    }
}