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
    private JTextField txtNoPolisi;
    private JComboBox<String> cmbJenis;
    private JTextField txtMerk;
    private JTextField txtModel;
    private JTextField txtTahun;
    private JTextField txtHargaDasar;
    private JTextField txtStatus;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnBersih;

    public DashboardView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createStatsPanel(), BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10)); 
        centerPanel.add(createTablePanel());
        centerPanel.add(createCrudFormPanel());        
        add(centerPanel, BorderLayout.CENTER);
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
                 "No Polisi","Jenis", "Merk", "Model", "Tahun", "Harga/hari", "Status"
        }, 0);
        tableKendaraan = new JTable(modelKendaraan);

        p.add(new JScrollPane(tableKendaraan), BorderLayout.CENTER);
        return p;
    }

    private JPanel createCrudFormPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        JLabel lbl = new JLabel("Form CRUD Kendaraan");
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        p.add(lbl, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridLayout(10, 2, 8, 8));
        
        txtNoPolisi = new JTextField();
        txtMerk = new JTextField();
        txtModel = new JTextField();
        txtTahun = new JTextField();
        txtHargaDasar = new JTextField();
        txtStatus = new JTextField();
        txtStatus.setEditable(false); 
        String[] jenisOptions = {"Mobil", "Motor", "Bus", "Truck", "MobilListrik", "MotorListrik"};
        cmbJenis = new JComboBox<>(jenisOptions);
        
        form.add(new JLabel("No Polisi"));
        form.add(txtNoPolisi);
        form.add(new JLabel("Jenis"));
        form.add(cmbJenis);
        form.add(new JLabel("Merk"));
        form.add(txtMerk);
        form.add(new JLabel("Model"));
        form.add(txtModel);
        form.add(new JLabel("Tahun"));
        form.add(txtTahun);
        form.add(new JLabel("Harga Dasar"));
        form.add(txtHargaDasar);
        form.add(new JLabel("Status"));
        form.add(txtStatus);

        p.add(form, BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTambah = new JButton("Create");
        btnBersih = new JButton("Clear Form");
        btnHapus = new JButton("Delete");
        btnEdit = new JButton("Edit");

        btnPanel.add(btnBersih);
        btnPanel.add(btnHapus);
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);

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
    public JButton getBtnBersih() { return btnBersih; }

    public JTextField getTxtNoPolisi() { return txtNoPolisi; }
    public JComboBox<String> getCmbJenis() { return cmbJenis; }
    public JTextField getTxtMerk() { return txtMerk; }
    public JTextField getTxtModel() { return txtModel; }
    public JTextField getTxtTahun() { return txtTahun; }
    public JTextField getTxtHargaDasar() { return txtHargaDasar; }
    public JTextField getTxtStatus() { return txtStatus; }
}
