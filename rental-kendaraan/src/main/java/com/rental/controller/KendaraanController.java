package com.rental.controller;

import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.kendaraan.KendaraanFactory;
import com.rental.repository.KendaraanRepository;

import java.util.List;

public class KendaraanController {

    private final KendaraanRepository kendaraanRepo = new KendaraanRepository();

    // ambil semua kendaraan
    public List<Kendaraan> getAllKendaraan() {
        return kendaraanRepo.findAll();
    }

    // ambil kendaraan berdasarkan no_polisi
    public Kendaraan getByNoPolisi(String noPolisi) {
        return kendaraanRepo.findByNoPolisi(noPolisi);
    }

    // tambah kendaraan baru
    public boolean addKendaraan(String jenis, String noPolisi, String merk, String model,
                                int tahun, double hargaDasar, String status) {

        if (merk == null || merk.isEmpty() ||
            model == null || model.isEmpty() ||
            jenis == null || jenis.isEmpty() ||
            noPolisi == null || noPolisi.isEmpty()) {
            System.out.println("Input kendaraan tidak lengkap");
            return false;
        }

        Kendaraan kendaraan = KendaraanFactory.createKendaraan(
                jenis, noPolisi, merk, model, tahun, hargaDasar, status
        );

        kendaraanRepo.save(kendaraan);
        return true;
    }

    // update kendaraan
    public boolean updateKendaraan(String noPolisi, String merk, String model,
                                   int tahun, double hargaDasar, String status) {

        Kendaraan existing = kendaraanRepo.findByNoPolisi(noPolisi);
        if (existing == null) {
            System.out.println("Kendaraan tidak ditemukan");
            return false;
        }

        existing.setMerk(merk);
        existing.setModel(model);
        existing.setTahun(tahun);
        existing.setHargaDasar(hargaDasar);
        existing.setStatus(status);

        return kendaraanRepo.update(existing);
    }

    // update status kendaraan (Tersedia / Tidak Tersedia)
    public boolean updateStatus(String noPolisi, String statusBaru) {
        return kendaraanRepo.updateStatus(noPolisi, statusBaru);
    }

    // hapus kendaraan
    public boolean deleteKendaraan(String noPolisi) {
        Kendaraan kendaraan = kendaraanRepo.findByNoPolisi(noPolisi);
        if (kendaraan == null) {
            System.out.println("Kendaraan tidak ditemukan");
            return false;
        }
        return kendaraanRepo.delete(noPolisi);
    }

    // hitung total kendaraan available atau rented
    public int countByStatus(String status) {
        return kendaraanRepo.countByStatus(status);
    }

    // hitung semua kendaraan
    public int countAll() {
        return kendaraanRepo.countByStatus("");
    }
}
