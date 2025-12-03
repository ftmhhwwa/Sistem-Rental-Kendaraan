package com.rental.model.pelanggan;

public class Pelanggan {
    private int id;
    private String nama;
    private String nik;
    private String noHP;
    private String alamat;

    public Pelanggan() { }

    public Pelanggan(int id, String nama, String nik, String noHP, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.noHP = noHP;
        this.alamat = alamat;
    }

    public Pelanggan(String nama, String nik, String noHP, String alamat) {
        this.nama = nama;
        this.nik = nik;
        this.noHP = noHP;
        this.alamat = alamat;
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
}
