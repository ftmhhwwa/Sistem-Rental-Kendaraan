package com.rental.view;

import javax.swing.*;
import java.awt.*;

public class FormRentalView extends JPanel {

    public FormRentalView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Form Rental Kendaraan");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(10, 2, 8, 8));

        form.add(new JLabel("NIK Customer"));
        form.add(new JTextField());
        
        form.add(new JLabel("Nama Customer"));
        form.add(new JTextField());

        form.add(new JLabel("No HP Customer"));
        form.add(new JTextField());
        
        form.add(new JLabel("Alamat Customer"));
        form.add(new JTextField());

        form.add(new JLabel("Kendaraan"));
        form.add(new JComboBox<>(new String[]{"— pilih kendaraan —"}));

        form.add(new JLabel("Tanggal Mulai"));
        form.add(new JTextField("DD-MM-YYYY"));

        form.add(new JLabel("Tanggal Selesai"));
        form.add(new JTextField("DD-MM-YYYY"));

        form.add(new JLabel("Total Harga"));
        form.add(new JTextField()); 
        
        form.add(new JLabel(""));
        form.add(new JButton("Hitung Harga"));
        
        form.add(new JLabel(""));
        form.add(new JButton("Submit Rental"));

        add(form, BorderLayout.CENTER);
    }
}