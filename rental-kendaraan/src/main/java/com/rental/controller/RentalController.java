package com.rental.controller;

import com.rental.view.FormRentalView;
import com.rental.repository.KendaraanRepository;
import com.rental.repository.PelangganRepository;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.pelanggan.Pelanggan;
import com.rental.model.rental.Rental;
import com.rental.model.rental.RentalFacade;
import com.rental.strategy.*; 

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentalController {

    private final FormRentalView view;
    private final KendaraanRepository kendaraanRepo;
    private final PelangganRepository pelangganRepo;
    private final RentalFacade rentalFacade;
    private DashboardController dashboardController;
    private PengembalianController pengembalianController;

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");    
    private Map<String, Kendaraan> availableKendaraanMap = new HashMap<>();

    public RentalController(FormRentalView view) {
        this.view = view;
        this.kendaraanRepo = new KendaraanRepository();
        this.pelangganRepo = new PelangganRepository();
        this.rentalFacade = new RentalFacade(); 
        
        loadAvailableKendaraan();
        initListeners();
    }
    
    public void setDashboardController(DashboardController dc) { this.dashboardController = dc; }
    public void setPengembalianController(PengembalianController pc) { this.pengembalianController = pc; }

    public void initListeners() 
    {
        view.getBtnHitungHarga().addActionListener(e -> hitungHarga());
        view.getBtnRental().addActionListener(e -> prosesRental());
        // Listener saat NIK diisi/enter: Cek keberadaan pelanggan
        view.getTxtNik().addActionListener(e -> checkNik());
    }

    public void loadAvailableKendaraan() {
        try {
            List<Kendaraan> list = kendaraanRepo.findAllTersedia();
            availableKendaraanMap.clear();
            
            view.getCmbKendaraan().removeAllItems();
            view.getCmbKendaraan().addItem("— pilih kendaraan —");

            for (Kendaraan k : list) {
                // Format label: No Polisi | Jenis | Merk Model (Tahun)
                String label = String.format("%s | %s | %s %s (%d)", 
                    k.getNoPolisi(), 
                    k.getClass().getSimpleName(),
                    k.getMerk(), 
                    k.getModel(), 
                    k.getTahun());
                    
                view.getCmbKendaraan().addItem(label);
                availableKendaraanMap.put(label, k);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal memuat daftar kendaraan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void checkNik() {
        String nik = view.getTxtNik().getText().trim();
        if (nik.isEmpty()) return;
        
        try {
            Pelanggan p = pelangganRepo.findByNik(nik);
            if (p != null) {
                view.getTxtNama().setText(p.getNama());
                view.getTxtNoHp().setText(p.getNoHP());
                view.getTxtAlamat().setText(p.getAlamat());
                // Kunci field jika pelanggan sudah ada
                setPelangganFieldsEditable(false);
            } else {
                // Pelanggan baru
                view.getTxtNama().setText("");
                view.getTxtNoHp().setText("");
                view.getTxtAlamat().setText("");
                setPelangganFieldsEditable(true);
            }
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(view, "Error saat mencari NIK: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPelangganFieldsEditable(boolean editable) {
        view.getTxtNama().setEditable(editable);
        view.getTxtNoHp().setEditable(editable);
        view.getTxtAlamat().setEditable(editable);
    }

    private HargaStrategy getStrategy(String strategyName) {
        // Implementasi Harga Strategy
        switch (strategyName) {
            case "Weekend Strategy": return new WeekendStrategy(10.0); 
            case "Diskon 10%": return new DiskonPersenStrategy(10.0);
            case "Diskon 20%": return new DiskonPersenStrategy(20.0);
            case "Normal Strategy": default: return new NormalStrategy();
        }
    }  

    private void hitungHarga() 
    {
        String selectedCarLabel = (String) view.getCmbKendaraan().getSelectedItem();
        String tglMulaiStr = view.getTxtTglMulai().getText();
        String tglSelesaiStr = view.getTxtTglSelesai().getText();
        String strategyName = (String) view.getCmbStrategy().getSelectedItem();
        
        if (selectedCarLabel == null || selectedCarLabel.contains("— pilih kendaraan —")) {
            JOptionPane.showMessageDialog(view, "Pilih kendaraan terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate tglMulai = LocalDate.parse(tglMulaiStr, DATE_FORMAT);
            LocalDate tglSelesai = LocalDate.parse(tglSelesaiStr, DATE_FORMAT);
            
            if (tglSelesai.isBefore(tglMulai)) {
                 JOptionPane.showMessageDialog(view, "Tanggal selesai tidak boleh sebelum tanggal mulai.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }
            
            long durasiHari = ChronoUnit.DAYS.between(tglMulai, tglSelesai) + 1;
            
            if (durasiHari <= 0) {
                 JOptionPane.showMessageDialog(view, "Durasi rental minimal 1 hari.", "Input Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Kendaraan kendaraan = availableKendaraanMap.get(selectedCarLabel);
            if (kendaraan == null) throw new Exception("Kendaraan tidak valid/tersedia.");
            
            Rental dummyRental = new Rental(0, kendaraan, tglMulai, tglSelesai, null, 0.0, strategyName);
            HargaStrategy strategy = getStrategy(strategyName);
            
            double hargaTotal = strategy.hitung(kendaraan.getHargaDasar() * durasiHari, dummyRental);
            view.getLblHargaTotal().setText(String.format("%.2f", hargaTotal));
            JOptionPane.showMessageDialog(view, "Harga berhasil dihitung untuk " + durasiHari + " hari.", "Info", JOptionPane.INFORMATION_MESSAGE);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view, "Format tanggal salah. Gunakan DD-MM-YYYY.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal menghitung harga: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void prosesRental() 
    {
        String nik = view.getTxtNik().getText().trim();
        String nama = view.getTxtNama().getText().trim();
        String noHp = view.getTxtNoHp().getText().trim();
        String alamat = view.getTxtAlamat().getText().trim();
        String selectedCarLabel = (String) view.getCmbKendaraan().getSelectedItem();
        String tglMulaiStr = view.getTxtTglMulai().getText();
        String tglSelesaiStr = view.getTxtTglSelesai().getText();
        String strategyName = (String) view.getCmbStrategy().getSelectedItem();
        
        
        if (nik.isEmpty() || nama.isEmpty() || selectedCarLabel.contains("— pilih kendaraan —") || view.getLblHargaTotal().getText().equals("0.00")) {
            JOptionPane.showMessageDialog(view, "Semua field harus diisi dan harga harus dihitung.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try 
        {
            double hargaTotal = Double.parseDouble(view.getLblHargaTotal().getText()); 
            LocalDate tglMulai = LocalDate.parse(tglMulaiStr, DATE_FORMAT);
            LocalDate tglSelesai = LocalDate.parse(tglSelesaiStr, DATE_FORMAT);

            // Dapatkan atau Simpan Pelanggan
            Pelanggan pelanggan = pelangganRepo.findByNik(nik);
            if (pelanggan == null) 
            {
                // Pelanggan baru, simpan dan dapatkan ID 
                pelanggan = new Pelanggan(nama, nik, noHp, alamat);
                pelangganRepo.insert(pelanggan); 
            }
            
            // Dapatkan Kendaraan
            Kendaraan kendaraan = availableKendaraanMap.get(selectedCarLabel);
            if (kendaraan == null) throw new Exception("Kendaraan tidak ditemukan atau sudah tidak tersedia.");
            
            // Buat objek Rental
            Rental newRental = new Rental(0, kendaraan, tglMulai, tglSelesai, pelanggan, hargaTotal, strategyName);
            
            // Proses Sewa menggunakan Facade (Simpan Rental & Update Status Kendaraan)
            if (rentalFacade.prosesSewa(newRental)) 
            {
                JOptionPane.showMessageDialog(view, "Rental berhasil diproses! Status kendaraan diupdate.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                // Update View Lain
                if (dashboardController != null) {
                    dashboardController.loadData(); // Refresh Dashboard (statistik dan tabel kendaraan)
                }
                if (pengembalianController != null) {
                    pengembalianController.loadActiveRentals(); // Refresh tabel pengembalian
                }
                
                clearForm();
                loadAvailableKendaraan(); // Refresh daftar kendaraan
            } else {
                 JOptionPane.showMessageDialog(view, "Gagal memproses rental (Error database/status).", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(view, "Format tanggal salah. Gunakan DD-MM-YYYY.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error saat proses rental: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearForm() {
        view.getTxtNik().setText("");
        setPelangganFieldsEditable(true);
        view.getTxtNama().setText("");
        view.getTxtNoHp().setText("");
        view.getTxtAlamat().setText("");
        view.getCmbKendaraan().setSelectedIndex(0);
        view.getTxtTglMulai().setText("DD-MM-YYYY");
        view.getTxtTglSelesai().setText("DD-MM-YYYY");
        view.getCmbStrategy().setSelectedIndex(0);
        view.getLblHargaTotal().setText("0.00");
    }
}
