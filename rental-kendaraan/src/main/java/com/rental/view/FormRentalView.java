package com.rental.view;

import javax.swing.*;
import java.awt.*;

public class FormRentalView extends JPanel 
{
    private JTextField txtNik;
    private JTextField txtNama;
    private JTextField txtNoHp;
    private JTextField txtAlamat;
    private JComboBox<String> cmbKendaraan;
    private JTextField txtTglMulai;
    private JTextField txtTglSelesai;
    private JComboBox<String> cmbStrategy;
    private JLabel lblHargaTotal;
    private JButton btnHitungHarga;
    private JButton btnRental;

    public FormRentalView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Form Rental Kendaraan");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(12, 2, 8, 8));

        txtNik = new JTextField();
        txtNama = new JTextField();
        txtNoHp = new JTextField();
        txtAlamat = new JTextField();
        cmbKendaraan = new JComboBox<>(new String[]{"— pilih kendaraan —"});
        txtTglMulai = new JTextField("DD-MM-YYYY");
        txtTglSelesai = new JTextField("DD-MM-YYYY");

        String[] strategyOptions = {"Normal Strategy", "Weekend Strategy", "Diskon 10%", "Diskon 20%"};
        cmbStrategy = new JComboBox<>(strategyOptions);
        lblHargaTotal = new JLabel("0.00"); // Menggunakan JLabel sebagai display harga
        
        btnHitungHarga = new JButton("Hitung Harga");
        btnRental = new JButton("Proses Rental"); // Ubah nama tombol agar lebih jelas

        form.add(new JLabel("NIK Customer"));
        form.add(txtNik);
        
        form.add(new JLabel("Nama Customer"));
        form.add(txtNama);

        form.add(new JLabel("No HP Customer"));
        form.add(txtNoHp);
        
        form.add(new JLabel("Alamat Customer"));
        form.add(txtAlamat);

        form.add(new JLabel("Kendaraan"));
        form.add(cmbKendaraan);

        form.add(new JLabel("Tanggal Mulai"));
        form.add(txtTglMulai);

        form.add(new JLabel("Tanggal Selesai"));
        form.add(txtTglSelesai);

        form.add(new JLabel("Total Harga"));
        // form.add(new JTextField()); 
        
        // form.add(new JLabel(""));
        JPanel hargaPanel = new JPanel(new BorderLayout());
        hargaPanel.add(lblHargaTotal, BorderLayout.WEST);
        hargaPanel.add(btnHitungHarga, BorderLayout.EAST);
        form.add(hargaPanel);
        
        form.add(new JLabel(""));
        form.add(btnRental);

        add(form, BorderLayout.CENTER);
    }

    public JTextField getTxtNik() { return txtNik; }
    public JTextField getTxtNama() { return txtNama; }
    public JTextField getTxtNoHp() { return txtNoHp; }
    public JTextField getTxtAlamat() { return txtAlamat; }
    public JComboBox<String> getCmbKendaraan() { return cmbKendaraan; }
    public JTextField getTxtTglMulai() { return txtTglMulai; }
    public JTextField getTxtTglSelesai() { return txtTglSelesai; }
    public JComboBox<String> getCmbStrategy() { return cmbStrategy; }
    public JLabel getLblHargaTotal() { return lblHargaTotal; }    
    public JButton getBtnHitungHarga() { return btnHitungHarga; }
    public JButton getBtnRental() { return btnRental; }
}