package com.rental.view;

import com.rental.controller.DashboardController;
import com.rental.controller.PengembalianController;
import com.rental.controller.RentalController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private DashboardView dashboardView;
    private FormRentalView rentalView;
    private PengembalianView pengembalianView;
    private static final String DASHBOARD = "dashboard";

        private JButton styledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        return btn;
    }

    public MainView() {
        setTitle("Sistem Rental Kendaraan");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ----- HEADER -----
        add(createHeader(), BorderLayout.NORTH);

        // ----- SIDEBAR -----
        add(createSidebar(), BorderLayout.WEST);

        // ----- MAIN CONTENT -----
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        dashboardView = new DashboardView();
        rentalView = new FormRentalView();
        pengembalianView = new PengembalianView();

        mainPanel.add(dashboardView, DASHBOARD);
        mainPanel.add(rentalView, "rental");
        mainPanel.add(pengembalianView, "pengembalian");

        // --- Inisialisasi Controller ---
        DashboardController dashboardController = new DashboardController(dashboardView);
        RentalController rentalController = new RentalController(rentalView); 
        PengembalianController pengembalianController = new PengembalianController(pengembalianView); 
        
        // Hubungkan Controller agar bisa refresh dashboard
        rentalController.setDashboardController(dashboardController); 
        pengembalianController.setDashboardController(dashboardController);
        rentalController.setPengembalianController(pengembalianController);

        add(mainPanel, BorderLayout.CENTER);

        cardLayout.show(mainPanel, DASHBOARD);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel("Sistem Rental Kendaraan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightButtons.setOpaque(false);

        JButton aboutBtn = styledButton("About", new Color(44, 62, 80));
        JButton exitBtn = styledButton("Exit", new Color(44, 62, 80));

        aboutBtn.addActionListener(e -> showAbout());
        exitBtn.addActionListener(e -> System.exit(0));

        rightButtons.add(aboutBtn);
        rightButtons.add(exitBtn);

        header.add(title, BorderLayout.WEST);
        header.add(rightButtons, BorderLayout.EAST);

        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 0, 15));
        sidebar.setBackground(new Color(44, 62, 80));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        sidebar.add(createSidebarButton("Dashboard", DASHBOARD));
        sidebar.add(createSidebarButton("Rental Kendaraan", "rental"));
        sidebar.add(createSidebarButton("Pengembalian", "pengembalian"));

        return sidebar;
    }

    private JButton createSidebarButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1), 
                BorderFactory.createEmptyBorder(10, 20, 10, 20) 
        ));

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addActionListener(e -> cardLayout.show(mainPanel, cardName));

        return btn;
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(
                this,
                "Aplikasi dibuat oleh:\n Tim Panconglumerrr\n Alda Pujama (241511066)\n Fatimah Hawwa Alkhansa (241511074)",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
