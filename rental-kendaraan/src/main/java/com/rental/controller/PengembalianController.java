package com.rental.controller;

import com.rental.view.PengembalianView;
import com.rental.repository.RentalRepository;
import com.rental.model.rental.Rental;
import com.rental.model.rental.RentalFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PengembalianController 
{
    private final PengembalianView view;
    private final RentalRepository rentalRepo;
    private final RentalFacade rentalFacade;
    private DashboardController dashboardController; 
    private Map<Integer, Rental> activeRentalMap = new HashMap<>();

    public PengembalianController( PengembalianView view) 
    {
        this.view = view;
        this.rentalRepo = new RentalRepository();
        this.rentalFacade = new RentalFacade();        
        loadActiveRentals();
        initListeners();
    }
    
    public void setDashboardController(DashboardController dc) { this.dashboardController = dc; }
    
    private void initListeners() {
        view.getTableRental().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = view.getTableRental().columnAtPoint(e.getPoint());
                int row = view.getTableRental().rowAtPoint(e.getPoint());
                
                // Cek apakah kolom yang diklik adalah kolom tombol "Status" (Index 6)
                if (column == 5 && row >= 0) { 
                    // Panggil logika penyelesaian rental
                    view.getTableRental().editCellAt(row, column);
                    
                    // Ambil ID rental dari kolom pertama (index 0)
                    int rentalId = (Integer) view.getModelRental().getValueAt(row, 0); 
                    selesaikanRental(rentalId, row);
                }
            }
        });
        //view.getButton().addActionListener(e -> selesaikanRental());
    }

    public void loadActiveRentals() {
        try {
            List<Rental> list = rentalRepo.findAll(); // Memanggil method baru di Repo
            activeRentalMap.clear();
            
            DefaultTableModel model = view.getModelRental();
            model.setRowCount(0); 

            for (Rental r : list) {
                activeRentalMap.put(r.getId(), r);
                model.addRow(new Object[]{
                    r.getId(),
                    r.getPelanggan().getNama(),
                    r.getKendaraan().getNoPolisi(),
                    r.getTglPinjam(),
                    r.getTglKembali(),
                    String.format("Rp %.2f", r.getHargaTotal())
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal memuat data rental: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selesaikanRental(int rentalId, int row) 
    {
       // Gunakan ID yang sudah didapatkan dari MouseListener
        Rental rental = activeRentalMap.get(rentalId);

        if (rental == null) {
            JOptionPane.showMessageDialog(view, "Data rental tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, 
                "Yakin rental ID " + rentalId + " sudah selesai? Kendaraan akan kembali tersedia.", 
                "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (rentalFacade.prosesPengembalian(rental)) {
                    JOptionPane.showMessageDialog(view, "Pengembalian berhasil diproses. Kendaraan sudah tersedia.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Hapus baris dari tabel secara instan (UI)
                    view.getModelRental().removeRow(row); 
                    
                    // Muat ulang sisa data dan refresh map
                    loadActiveRentals(); 
                    
                    // Refresh Dashboard
                    if (dashboardController != null) {
                        dashboardController.loadData();
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal memproses pengembalian (Kesalahan internal).", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(view, "Gagal memproses pengembalian: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                 e.printStackTrace();
            }
        }
    }
}
