package com.htt.vehiclerental.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public final class UiKit {

    public static final Color APP_BACKGROUND = new Color(0xF4, 0xF7, 0xFB);
    public static final Color SURFACE = Color.WHITE;
    public static final Color SIDEBAR = new Color(0x0F, 0x1B, 0x2D);
    public static final Color SIDEBAR_HIGHLIGHT = new Color(0x1D, 0x4E, 0x89);
    public static final Color PRIMARY = new Color(0x1D, 0x4E, 0xD8);
    public static final Color SUCCESS = new Color(0x10, 0x8B, 0x6A);
    public static final Color WARNING = new Color(0xEA, 0x9A, 0x1F);
    public static final Color DANGER = new Color(0xDC, 0x26, 0x26);
    public static final Color INFO = new Color(0x0F, 0x76, 0x8C);
    public static final Color TEXT = new Color(0x15, 0x23, 0x3A);
    public static final Color MUTED = new Color(0x64, 0x74, 0x8B);
    public static final Color BORDER = new Color(0xD9, 0xE2, 0xEC);

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font SECTION_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BODY_FONT_BOLD = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private UiKit() {
    }

    public static JPanel createSurfacePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE);
        panel.setBorder(createCardBorder());
        return panel;
    }

    public static Border createCardBorder() {
        return BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(16, 16, 16, 16));
    }

    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(TITLE_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel createSectionTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SECTION_FONT);
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(BODY_FONT);
        label.setForeground(MUTED);
        return label;
    }

    public static JLabel createMetricValueLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.LEFT);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        label.setForeground(TEXT);
        return label;
    }

    public static JLabel createMetricLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(SMALL_FONT);
        label.setForeground(MUTED);
        return label;
    }

    public static JPanel createMetricCard(String label, String value, String note, Color accent) {
        JPanel card = new JPanel();
        card.setLayout(new javax.swing.BoxLayout(card, javax.swing.BoxLayout.Y_AXIS));
        card.setBackground(SURFACE);
        card.setBorder(createCardBorder());
        card.setPreferredSize(new Dimension(0, 100));

        JPanel accentBar = new JPanel();
        accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 4));
        accentBar.setPreferredSize(new Dimension(100, 4));
        accentBar.setBackground(accent);
        accentBar.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel metricLabel = createMetricLabel(label);
        JLabel metricValue = createMetricValueLabel(value);
        JLabel metricNote = createSubtitleLabel(note);

        card.add(accentBar);
        card.add(javax.swing.Box.createVerticalStrut(5));
        card.add(metricLabel);
        card.add(javax.swing.Box.createVerticalStrut(0));
        card.add(metricValue);
        card.add(javax.swing.Box.createVerticalStrut(6));
        card.add(metricNote);
        return card;
    }

    public static JPanel createSectionHeader(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

        JLabel titleLabel = createTitleLabel(title);
        JLabel subtitleLabel = createSubtitleLabel(subtitle);

        panel.add(titleLabel);
        panel.add(javax.swing.Box.createVerticalStrut(6));
        panel.add(subtitleLabel);
        return panel;
    }

    public static JPanel createCardSection(String title, String subtitle, JComponent body) {
        JPanel card = createSurfacePanel();
        card.setLayout(new java.awt.BorderLayout(0, 14));

        JPanel header = createSectionHeader(title, subtitle);
        card.add(header, java.awt.BorderLayout.NORTH);
        card.add(body, java.awt.BorderLayout.CENTER);
        return card;
    }

    public static JPanel createFieldBlock(String labelText, JComponent component) {
        JPanel block = new JPanel();
        block.setOpaque(false);
        block.setLayout(new java.awt.BorderLayout(0, 6));

        JLabel label = new JLabel(labelText);
        label.setFont(BODY_FONT);
        label.setForeground(TEXT);

        block.add(label, java.awt.BorderLayout.NORTH);
        block.add(component, java.awt.BorderLayout.CENTER);
        return block;
    }

    public static JPanel createFieldBlock_Bold(String labelText, JComponent component) {
        JPanel block = new JPanel();
        block.setOpaque(false);
        block.setLayout(new java.awt.BorderLayout(0, 6));

        JLabel label = new JLabel(labelText);
        label.setFont(BODY_FONT_BOLD);
        label.setForeground(TEXT);

        block.add(label, java.awt.BorderLayout.NORTH);
        block.add(component, java.awt.BorderLayout.CENTER);
        return block;
    }

    public static JTextField createTextField(int columns) {
        JTextField field = new JTextField(columns);
        styleInputField(field);
        return field;
    }

    public static JTextArea createTextArea(int rows, int columns) {
        JTextArea area = new JTextArea(rows, columns);
        area.setFont(BODY_FONT);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(Color.WHITE);
        area.setForeground(TEXT);
        area.setCaretColor(TEXT);
        area.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        return area;
    }

    public static JComboBox<String> createComboBox(String... values) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.setFont(BODY_FONT);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        return comboBox;
    }

    public static JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY, Color.WHITE);
    }

    public static JButton createSecondaryButton(String text) {
        return createButton(text, new Color(0xE2, 0xE8, 0xF0), TEXT);
    }

    public static JButton createGhostButton(String text) {
        return createButton(text, SURFACE, PRIMARY);
    }

    public static JButton createDangerButton(String text) {
        return createButton(text, DANGER, Color.WHITE);
    }

    public static JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(SIDEBAR);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        button.setPreferredSize(new Dimension(220, 46));
        button.setBorder(new EmptyBorder(0, 16, 0, 16));
        return button;
    }

    public static void setSidebarButtonState(JButton button, boolean active) {
        button.setBackground(active ? SIDEBAR_HIGHLIGHT : SIDEBAR);
    }

    public static JPanel createButtonRow(JButton... buttons) {
        JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 12, 0));
        row.setOpaque(false);
        for (JButton button : buttons) {
            row.add(button);
        }
        return row;
    }

    public static JTable createTable(String[] columns, Object[][] rows) {
        DefaultTableModel model = new DefaultTableModel(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(32);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(BORDER);
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFont(BODY_FONT);
        table.setForeground(TEXT);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(0xDB, 0xE7, 0xFF));
        table.setSelectionForeground(TEXT);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setForeground(TEXT);
        header.setBackground(new Color(0xEC, 0xF2, 0xF8));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return table;
    }

    public static JScrollPane createTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        return scrollPane;
    }

    public static JPanel createInfoBanner(String title, String subtitle, Color accent) {
        JPanel banner = createSurfacePanel();
        banner.setLayout(new java.awt.BorderLayout(0, 8));
        banner.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(accent, 2, true),
                new EmptyBorder(18, 18, 18, 18)));

        JLabel titleLabel = createTitleLabel(title);
        JLabel subtitleLabel = createSubtitleLabel(subtitle);

        banner.add(titleLabel, java.awt.BorderLayout.NORTH);
        banner.add(subtitleLabel, java.awt.BorderLayout.CENTER);
        return banner;
    }

    public static JPanel createStatList(String... items) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        for (String item : items) {
            JLabel label = new JLabel("• " + item);
            label.setFont(BODY_FONT);
            label.setForeground(TEXT);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(label);
            panel.add(javax.swing.Box.createVerticalStrut(6));
        }
        return panel;
    }

    private static JButton createButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(foreground);
        button.setBackground(background);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        return button;
    }

    private static void styleInputField(JComponent component) {
        component.setFont(BODY_FONT);
        component.setForeground(TEXT);
        component.setBackground(Color.WHITE);
        component.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
    }

    public static void showErrorDialog(Component parent, String message) {
        javax.swing.JOptionPane.showMessageDialog(parent, message, "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
    } 

    public static int showConfirmDialog(Component parent, String message) {
        int result = javax.swing.JOptionPane.showConfirmDialog(parent, message, "Xác nhận", javax.swing.JOptionPane.OK_CANCEL_OPTION);
        return result;  
    }
}
