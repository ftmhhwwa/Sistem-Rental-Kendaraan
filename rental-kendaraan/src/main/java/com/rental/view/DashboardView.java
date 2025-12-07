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
    private static final String FONT_ARIAL = "Arial";

    public DashboardView() {

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 247, 250));

        add(createStatsPanel(), BorderLayout.NORTH);

        // ==========================================
        // BAGIAN KONTEN TENGAH (TABLE BESAR + FORM KECIL)
        // ==========================================
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.80); 
        splitPane.setDividerSize(10);

        splitPane.setLeftComponent(createTablePanel());
        splitPane.setRightComponent(createCrudFormPanel());

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createStatsPanel() {
        JPanel p = new JPanel(new GridLayout(1, 3, 15, 15));
        p.setOpaque(false);

        totalKendaraan = createCard("Total Kendaraan", new Color(66, 133, 244));
        totalTersedia = createCard("Tersedia", new Color(52, 168, 83));
        totalDisewa = createCard("Sedang Dirental", new Color(234, 67, 53));

        p.add(wrap(totalKendaraan));
        p.add(wrap(totalTersedia));
        p.add(wrap(totalDisewa));

        return p;
    }

    private JLabel createCard(String title, Color color) {
        JLabel label = new JLabel("<html>" + title + "<br><h1>0</h1></html>", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(color);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                new EmptyBorder(10, 10, 10, 10)
        ));
        return label;
    }

    private JPanel wrap(JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(c);
        return p;
    }

    // ======================================================
    // PANEL TABEL (DISET BESAR)
    // ======================================================
    private JPanel createTablePanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JLabel lbl = new JLabel("   Data Kendaraan");
        lbl.setFont(new Font(FONT_ARIAL, Font.BOLD, 16));

        p.add(lbl, BorderLayout.NORTH);

        modelKendaraan = new DefaultTableModel(new String[]{"No Polisi","Jenis", "Merk", "Model", "Tahun", "Harga/hari", "Status"}, 0);

        tableKendaraan = new JTable(modelKendaraan);
        tableKendaraan.getTableHeader().setBackground(new Color(0, 0, 0));
        tableKendaraan.getTableHeader().setForeground(Color.WHITE);
        tableKendaraan.getTableHeader().setFont(new Font(FONT_ARIAL, Font.BOLD, 12));
        tableKendaraan.setRowHeight(22);
        tableKendaraan.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scroll = new JScrollPane(tableKendaraan);
        scroll.setPreferredSize(new Dimension(800, 400));

        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    // ======================================================
    // PANEL FORM CRUD (DIBUAT LEBIH KECIL)
    // ======================================================
    private JPanel createCrudFormPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setOpaque(false);

        JLabel lbl = new JLabel("   Form CRUD Kendaraan");
        lbl.setFont(new Font(FONT_ARIAL, Font.BOLD, 16));
        p.add(lbl, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(10, 2, 8, 8));
        form.setOpaque(false);

        txtNoPolisi = new JTextField();
        txtMerk = new JTextField();
        txtModel = new JTextField();
        txtTahun = new JTextField();
        txtHargaDasar = new JTextField();
        txtStatus = new JTextField();
        txtStatus.setEditable(false);

        String[] jenisOptions = {"Mobil", "Motor", "Bus", "Truck", "MobilListrik", "MotorListrik"};
        cmbJenis = new JComboBox<>(jenisOptions);

        form.add(new JLabel("   No Polisi"));
        form.add(txtNoPolisi);

        form.add(new JLabel("   Jenis"));
        form.add(cmbJenis);

        form.add(new JLabel("   Merk"));
        form.add(txtMerk);

        form.add(new JLabel("   Model"));
        form.add(txtModel);

        form.add(new JLabel("   Tahun"));
        form.add(txtTahun);

        form.add(new JLabel("   Harga Dasar"));
        form.add(txtHargaDasar);

        form.add(new JLabel("   Status"));
        form.add(txtStatus);

        p.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        btnTambah = styledButton("Create", new Color(70, 130, 180));
        btnEdit = styledButton("Edit", new Color(80, 200, 120));
        btnHapus = styledButton("Delete", new Color(250, 80, 83));
        btnBersih = styledButton("Clear Form", new Color(109, 129, 150));

        btnPanel.add(btnBersih);
        btnPanel.add(btnHapus);
        btnPanel.add(btnTambah);
        btnPanel.add(btnEdit);

        p.add(btnPanel, BorderLayout.SOUTH);

        p.setPreferredSize(new Dimension(350, 0)); // form lebih kecil

        return p;
    }

    private JButton styledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        return btn;
    }

    // GETTER
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
