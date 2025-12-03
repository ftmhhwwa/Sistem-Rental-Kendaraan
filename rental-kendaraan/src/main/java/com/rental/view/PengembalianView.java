package com.rental.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class PengembalianView extends JPanel {

    private JTable tableRental;
    private DefaultTableModel modelRental;

    public PengembalianView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(createTablePanel(), BorderLayout.CENTER);
    }
    
    private JPanel createTablePanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));

        JLabel title = new JLabel("Pengembalian Kendaraan");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);
        
        modelRental = new DefaultTableModel(new String[]{
                "ID", "Nama Customer", "No Polisi", "tanggal pinjam", "tanggal kembali", "Total Harga", "Status"
            }, 0);
        tableRental = new JTable(modelRental);

        p.add(new JScrollPane(tableRental), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        return p;
    }
}