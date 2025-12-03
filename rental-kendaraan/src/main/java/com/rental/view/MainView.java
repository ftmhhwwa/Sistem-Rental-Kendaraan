package com.rental.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    private CardLayout layout;
    private JPanel mainPanel;

    public DashboardView dashboardView;
    public FormRentalView rentalView;
    public PengembalianView pengembalianView;

    public JButton menuDashboard; //
    public JButton menuRental; //
    public JButton menuPengembalian; //

    public MainView() {
        setTitle("Sistem Rental Kendaraan");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        layout = new CardLayout();
        mainPanel = new JPanel(layout);

        dashboardView = new DashboardView();
        rentalView = new FormRentalView();
        pengembalianView = new PengembalianView();

        mainPanel.add(dashboardView, "dashboard");
        mainPanel.add(rentalView, "rental");
        mainPanel.add(pengembalianView, "pengembalian");

        JPanel sidebar = createSidebar();
        
        add(sidebar, BorderLayout.WEST); //
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel p = new JPanel(new GridLayout(10, 1));
        p.setBackground(new Color(44, 62, 80));
        p.setPreferredSize(new Dimension(200, 0));

        menuDashboard = new JButton("Dashboard");
        menuRental = new JButton("Rental");
        menuPengembalian = new JButton("Pengembalian");

        menuDashboard.addActionListener(e -> layout.show(mainPanel, "dashboard"));
        menuRental.addActionListener(e -> layout.show(mainPanel, "rental"));
        menuPengembalian.addActionListener(e -> layout.show(mainPanel, "pengembalian"));

        style(menuDashboard);
        style(menuRental);
        style(menuPengembalian);

        p.add(menuDashboard);
        p.add(menuRental);
        p.add(menuPengembalian);

        return p;
    }

    private void style(JButton btn) {
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setFocusPainted(false);
    }

    public void showPage(String name) {
        layout.show(mainPanel, name);
    }
}
