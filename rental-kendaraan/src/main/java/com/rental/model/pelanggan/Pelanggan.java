package com.rental.model.pelanggan;

public class Pelanggan {
    private int id;
    private String nama;
    private String nik;
    private String noHP;
    private String alamat;
    private String email;

    public Pelanggan() { }

    public Pelanggan(int id, String nama, String nik, String noHP, String alamat, String email) {
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.noHP = noHP;
        this.alamat = alamat;
        this.email = email;
    }

    public Pelanggan(String nama, String nik, String noHP, String alamat, String email) {
        this.nama = nama;
        this.nik = nik;
        this.noHP = noHP;
        this.alamat = alamat;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return nama != null && !nama.trim().isEmpty() &&
               noHP != null && !noHP.trim().isEmpty();
    }

    @Override
    public String toString() {
        return nama + " (" + noHP + ")";
    }
}
