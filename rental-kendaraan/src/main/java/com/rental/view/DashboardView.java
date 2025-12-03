package com.rental.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardView extends JPanel {

    private JLabel totalKendaraan;
    private JLabel totalTersedia;
    private JLabel totalDisewa;

    private JTable tableKendaraan;
    private DefaultTableModel modelKendaraan;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;

    public DashboardView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createStatsPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    private JPanel createStatsPanel() {
        JPanel p = new JPanel(new GridLayout(1, 3, 15, 15));

        totalKendaraan = createCard("Total Kendaraan");
        totalTersedia = createCard("Tersedia");
        totalDisewa = createCard("Sedang Dirental");

        p.add(wrap(totalKendaraan));
        p.add(wrap(totalTersedia));
        p.add(wrap(totalDisewa));

        return p;
    }

    private JLabel createCard(String title) {
        JLabel label = new JLabel("<html>" + title + "<br><h1>0</h1></html>", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

    private JPanel wrap(JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(c);
        return p;
    }

    private JPanel createTablePanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));

        JLabel lbl = new JLabel("Data Kendaraan");
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        p.add(lbl, BorderLayout.NORTH);

        modelKendaraan = new DefaultTableModel(new String[]{
                "ID", "Jenis","Merk", "Model", "Tahun", "No Polisi", "Harga/hari", "Status"
        }, 0);
        tableKendaraan = new JTable(modelKendaraan);

        p.add(new JScrollPane(tableKendaraan), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");

        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);

        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    public JLabel getTotalKendaraan() { return totalKendaraan; }
    public JLabel getTotalTersedia() { return totalTersedia; }
    public JLabel getTotalDisewa() { return totalDisewa; }

    public DefaultTableModel getModelKendaraan() { return modelKendaraan; }
    public JTable getTableKendaraan() { return tableKendaraan; }

    public JButton getBtnTambah() { return btnTambah; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnHapus() { return btnHapus; }
}
