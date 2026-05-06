package com.htt.vehiclerental.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

    private final Map<String, JButton> navButtons = new LinkedHashMap<>();
    private JPanel mainContent;
    private JLabel pageTitleLabel;
    private JLabel pageSubtitleLabel;

    public MainFrame() {
        setTitle("Hệ thống quản lý cho thuê xe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 860));
        setSize(1366, 860);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UiKit.APP_BACKGROUND);

        add(createSidebar(), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);

        showPanel("Dashboard");
    }

    public JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(270, 0));
        sidebar.setBackground(UiKit.SIDEBAR);

        JPanel brandPanel = new JPanel();
        brandPanel.setOpaque(false);
        brandPanel.setLayout(new javax.swing.BoxLayout(brandPanel, javax.swing.BoxLayout.Y_AXIS));
        brandPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(28, 22, 20, 22));

        JLabel title = new JLabel("Vehicle Rental");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Bảng điều khiển quản trị");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(new Color(0xB7, 0xC4, 0xD6));

        JLabel hint = new JLabel("Thiết kế giao diện hoàn chỉnh");
        hint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        hint.setForeground(new Color(0x8D, 0x9A, 0xAD));

        brandPanel.add(title);
        brandPanel.add(javax.swing.Box.createVerticalStrut(8));
        brandPanel.add(subtitle);
        brandPanel.add(javax.swing.Box.createVerticalStrut(6));
        brandPanel.add(hint);

        JPanel menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new javax.swing.BoxLayout(menuPanel, javax.swing.BoxLayout.Y_AXIS));
        menuPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 14, 8, 14));

        addSidebarButton(menuPanel, "Dashboard", "Tổng quan", e -> showPanel("Dashboard"));
        addSidebarButton(menuPanel, "ManageVehicles", "Quản lý xe", e -> showPanel("ManageVehicles"));
        addSidebarButton(menuPanel, "ManageCustomers", "Quản lý khách hàng", e -> showPanel("ManageCustomers"));
        addSidebarButton(menuPanel, "ManageRentals", "Quản lý thuê xe", e -> showPanel("ManageRentals"));
        addSidebarButton(menuPanel, "ManageFees", "Quản lý phí phát sinh", e -> showPanel("ManageFees"));
        addSidebarButton(menuPanel, "GenerateReports", "Báo cáo doanh thu", e -> showPanel("GenerateReports"));

        JPanel footer = new JPanel();
        footer.setOpaque(false);
        footer.setLayout(new javax.swing.BoxLayout(footer, javax.swing.BoxLayout.Y_AXIS));
        footer.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 22, 22, 22));

        JLabel footerLabel = new JLabel("Phiên bản demo giao diện");
        footerLabel.setForeground(new Color(0xB7, 0xC4, 0xD6));
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel footerHint = new JLabel("Sẵn sàng nối với DB sau.");
        footerHint.setForeground(new Color(0x8D, 0x9A, 0xAD));
        footerHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerHint.setAlignmentX(LEFT_ALIGNMENT);

        footer.add(footerLabel);
        footer.add(javax.swing.Box.createVerticalStrut(4));
        footer.add(footerHint);

        sidebar.add(brandPanel, BorderLayout.NORTH);
        sidebar.add(menuPanel, BorderLayout.CENTER);
        sidebar.add(footer, BorderLayout.SOUTH);
        return sidebar;
    }

    private void addSidebarButton(JPanel container, String panelName, String title, ActionListener listener) {
        JButton button = UiKit.createSidebarButton(title);
        button.addActionListener(listener);
        button.setAlignmentX(LEFT_ALIGNMENT);
        container.add(button);
        container.add(javax.swing.Box.createVerticalStrut(10));
        navButtons.put(panelName, button);
    }

    private JPanel createWorkspace() {
        JPanel workspace = new JPanel(new BorderLayout(0, 18));
        workspace.setBackground(UiKit.APP_BACKGROUND);
        workspace.setBorder(javax.swing.BorderFactory.createEmptyBorder(22, 22, 22, 22));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new javax.swing.BoxLayout(heading, javax.swing.BoxLayout.Y_AXIS));

        pageTitleLabel = new JLabel("Dashboard");
        pageTitleLabel.setFont(UiKit.TITLE_FONT);
        pageTitleLabel.setForeground(UiKit.TEXT);

        pageSubtitleLabel = new JLabel("Tổng quan hoạt động và điều hướng tới các phân hệ quản trị.");
        pageSubtitleLabel.setFont(UiKit.BODY_FONT);
        pageSubtitleLabel.setForeground(UiKit.MUTED);

        heading.add(pageTitleLabel);
        heading.add(javax.swing.Box.createVerticalStrut(6));
        heading.add(pageSubtitleLabel);

        JLabel badge = new JLabel("Demo UI");
        badge.setOpaque(true);
        badge.setBackground(new Color(0xDB, 0xE7, 0xFF));
        badge.setForeground(UiKit.PRIMARY);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        badge.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 14, 8, 14));
        badge.setHorizontalAlignment(SwingConstants.CENTER);

        topBar.add(heading, BorderLayout.WEST);
        topBar.add(badge, BorderLayout.EAST);

        mainContent = createMainContent();

        workspace.add(topBar, BorderLayout.NORTH);
        workspace.add(mainContent, BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createMainContent() {
        JPanel content = new JPanel(new CardLayout(0, 0));
        content.setOpaque(false);

        content.add(new DashboardPanel(), "Dashboard");
        content.add(new ManageVehiclesPanel(), "ManageVehicles");
        content.add(new ManageCustomersPanel(), "ManageCustomers");
        content.add(new ManageRentalsPanel(), "ManageRentals");
        content.add(new ManageFeesPanel(), "ManageFees");
        content.add(new GenerateReportsPanel(), "GenerateReports");

        return content;
    }

    public void showPanel(String panelName) {
        CardLayout cl = (CardLayout) mainContent.getLayout();
        cl.show(mainContent, panelName);
        refreshSidebarState(panelName);
        refreshHeader(panelName);
    }

    private void refreshSidebarState(String panelName) {
        navButtons.forEach((name, button) -> UiKit.setSidebarButtonState(button, name.equals(panelName)));
    }

    private void refreshHeader(String panelName) {
        switch (panelName) {
            case "ManageVehicles" -> {
                pageTitleLabel.setText("Quản lý xe");
                pageSubtitleLabel.setText("Thiết lập hồ sơ xe, trạng thái và giá thuê theo từng dòng xe.");
            }
            case "ManageCustomers" -> {
                pageTitleLabel.setText("Quản lý khách hàng");
                pageSubtitleLabel.setText("Lưu trữ thông tin khách hàng và tra cứu nhanh theo định danh.");
            }
            case "ManageRentals" -> {
                pageTitleLabel.setText("Quản lý thuê xe");
                pageSubtitleLabel.setText("Tạo hợp đồng thuê, theo dõi thời gian và tính tổng chi phí.");
            }
            case "ManageFees" -> {
                pageTitleLabel.setText("Quản lý phí phát sinh");
                pageSubtitleLabel.setText("Thiết lập loại phí và ghi nhận chi phí bổ sung cho mỗi đơn thuê.");
            }
            case "GenerateReports" -> {
                pageTitleLabel.setText("Báo cáo doanh thu");
                pageSubtitleLabel.setText("Tổng hợp số liệu vận hành, doanh thu và trạng thái hệ thống.");
            }
            default -> {
                pageTitleLabel.setText("Dashboard");
                pageSubtitleLabel.setText("Tổng quan hoạt động và điều hướng tới các phân hệ quản trị.");
            }
        }
    }
}
