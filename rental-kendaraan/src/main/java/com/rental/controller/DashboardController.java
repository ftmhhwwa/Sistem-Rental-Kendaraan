package com.rental.controller;

import com.rental.view.DashboardView;
import com.rental.repository.KendaraanRepository;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.kendaraan.KendaraanFactory; 

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DashboardController {

    private static final String STATUS_TERSEDIA = "tersedia";
    private static final String STATUS_TIDAK_TERSEDIA = "tidak tersedia";
    private static final String TITLE_SUKSES = "Sukses";
    private static final String TITLE_ERROR = "Error";
    private static final String INPUT_ERROR = "Input Error";
    private static final String HTML_CLOSE = "</h1></html>";
    private final DashboardView view;
    private final KendaraanRepository kendaraanRepo;

    public DashboardController(DashboardView view) {
        this.view = view;
        this.kendaraanRepo = new KendaraanRepository();
        loadData();
        initListeners();
    }
    
    private void initListeners() {
        // Listener untuk Tombol CRUD
        view.getBtnTambah().addActionListener(e -> saveKendaraan());
        view.getBtnHapus().addActionListener(e -> deleteKendaraan());
        view.getBtnEdit().addActionListener(e -> editKendaraan());
        view.getBtnBersih().addActionListener(e -> clearForm());
        
        // Listener untuk Tabel (Klik baris -> isi form)
        view.getTableKendaraan().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedToForm();
                }
            }
        });
    }

    public void loadData() {
        try {
            List<Kendaraan> list = kendaraanRepo.findAll();
            int total = kendaraanRepo.countByStatus(null);
            int tersedia = kendaraanRepo.countByStatus(STATUS_TERSEDIA);
            int disewa = kendaraanRepo.countByStatus(STATUS_TIDAK_TERSEDIA);
            view.getTotalKendaraan().setText("<html>Total Kendaraan<br><h1>" + total + HTML_CLOSE);
            view.getTotalTersedia().setText("<html>Tersedia<br><h1>" + tersedia + HTML_CLOSE);
            view.getTotalDisewa().setText("<html>Sedang Dirental<br><h1>" + disewa + HTML_CLOSE);
            view.getTotalDisewa().setText("<html>Sedang Dirental<br><h1>" + disewa + HTML_CLOSE);

            DefaultTableModel model = view.getModelKendaraan();
            model.setRowCount(0); 

            for (Kendaraan k : list) {
                model.addRow(new Object[]{
                    k.getNoPolisi(), 
                    k.getClass().getSimpleName(),
                    k.getMerk(),
                    k.getModel(),
                    k.getTahun(),
                    k.getHargaDasar(),
                    k.getStatus()
                });
            }
            clearForm(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Gagal memuat data: " + e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadSelectedToForm() {
        int selectedRow = view.getTableKendaraan().getSelectedRow();
        if (selectedRow == -1) {
            clearForm();
            return;
        }

        String noPolisi = (String) view.getModelKendaraan().getValueAt(selectedRow, 0);
        
        try {
            Kendaraan k = kendaraanRepo.findByNoPolisi(noPolisi);
            if (k != null) {
                view.getTxtNoPolisi().setText(k.getNoPolisi());
                view.getTxtNoPolisi().setEditable(false); 
                view.getCmbJenis().setSelectedItem(k.getClass().getSimpleName());
                view.getTxtMerk().setText(k.getMerk());
                view.getTxtModel().setText(k.getModel());
                view.getTxtTahun().setText(String.valueOf(k.getTahun()));
                view.getTxtHargaDasar().setText(String.valueOf(k.getHargaDasar()));
                view.getTxtStatus().setText(k.getStatus());
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(view, "Gagal memuat detail kendaraan: " + e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
       view.getTxtNoPolisi().setText("");
        view.getTxtNoPolisi().setEditable(true);
        view.getCmbJenis().setSelectedIndex(0);
        view.getTxtMerk().setText("");
        view.getTxtModel().setText("");
        view.getTxtTahun().setText("");
        view.getTxtHargaDasar().setText("");
        view.getTxtStatus().setText("");
        view.getTableKendaraan().clearSelection();
    }
    
    private void saveKendaraan() {
        String noPolisi = view.getTxtNoPolisi().getText();
        String jenis = (String) view.getCmbJenis().getSelectedItem();
        String merk = view.getTxtMerk().getText();
        String model = view.getTxtModel().getText();

        if (noPolisi.isEmpty() || jenis.isEmpty()|| merk.isEmpty() || model.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No Polisi jenis model Merk harus diisi.", INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int tahun = Integer.parseInt(view.getTxtTahun().getText());
            double hargaDasar = Double.parseDouble(view.getTxtHargaDasar().getText());
            
            String statusAwal = STATUS_TERSEDIA;
            Kendaraan newKendaraan = KendaraanFactory.createKendaraan(
                jenis, noPolisi, merk, model, tahun, hargaDasar, statusAwal
            );
            if (kendaraanRepo.save(newKendaraan)) { 
                JOptionPane.showMessageDialog(view, "Kendaraan baru berhasil ditambahkan (CREATE).", TITLE_SUKSES, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menyimpan kendaraan baru (No Polisi mungkin sudah ada).", TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }
            
            loadData(); 
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Tahun dan Harga Dasar harus angka yang valid.", INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
             JOptionPane.showMessageDialog(view, e.getMessage(), INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteKendaraan() {
        String noPolisi = view.getTxtNoPolisi().getText();
        String status = view.getTxtStatus().getText();

        if (noPolisi.isEmpty() || view.getTxtNoPolisi().isEditable()) {
             JOptionPane.showMessageDialog(view, "Pilih kendaraan dari tabel untuk dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!STATUS_TERSEDIA.equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(view, "Kendaraan hanya bisa dihapus jika berstatus 'tersedia'. Status saat ini: " + status, "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Yakin ingin menghapus kendaraan dengan No Polisi " + noPolisi + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

            if (kendaraanRepo.delete(noPolisi)) { 
                JOptionPane.showMessageDialog(view, "Kendaraan berhasil dihapus.", TITLE_SUKSES, JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(view, "Gagal menghapus kendaraan.", TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }   
        }

    private void editKendaraan() 
    {
        String noPolisi = view.getTxtNoPolisi().getText();
        String jenis = (String) view.getCmbJenis().getSelectedItem(); // Jenis bisa berubah jika KendaraanRepository mengizinkan
        String merk = view.getTxtMerk().getText();
        String model = view.getTxtModel().getText();

        if (noPolisi.isEmpty() || jenis.isEmpty()|| merk.isEmpty() || model.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No Polisi jenis model Merk harus diisi.", INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int tahun = Integer.parseInt(view.getTxtTahun().getText());
            double hargaDasar = Double.parseDouble(view.getTxtHargaDasar().getText());
            String status = view.getTxtStatus().getText(); 

            Kendaraan existing = kendaraanRepo.findByNoPolisi(noPolisi);
            
            if (existing != null) 
            {
                existing.setMerk(merk);
                existing.setModel(model);
                existing.setTahun(tahun);
                existing.setHargaDasar(hargaDasar);
                existing.setStatus(status); 
                if(kendaraanRepo.update(existing)) 
                { 
                    JOptionPane.showMessageDialog(view, "Data kendaraan berhasil diubah (EDIT).", TITLE_SUKSES, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, "Gagal mengubah data kendaraan.", TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 JOptionPane.showMessageDialog(view, "Kendaraan tidak ditemukan untuk diupdate.", TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
            }
            
            loadData(); 
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Tahun dan Harga Dasar harus angka yang valid.", INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat edit: " + e.getMessage(), TITLE_ERROR, JOptionPane.ERROR_MESSAGE);
        }
    }
}
