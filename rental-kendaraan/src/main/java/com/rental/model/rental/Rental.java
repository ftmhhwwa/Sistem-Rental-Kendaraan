package com.rental.model.rental;
import java.time.LocalDate;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.pelanggan.Pelanggan;
// import com.rental.strategy.HargaStrategy;

public class Rental 
{
    private int id;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;
    private LocalDate tglPinjam;
    private LocalDate tglKembali;
    //private HargaStrategy hargaStrategy;

     public Rental(int id,Kendaraan kendaraan, LocalDate tglPinjam, LocalDate tglKembali) 
     {
        this.id = id;
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        //this.hargaStrategy = hargaStrategy;
    }

    public int getId() { return id; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public Kendaraan getKendaraan() { return kendaraan; }
    public LocalDate getTglPinjam() { return tglPinjam; }
    public LocalDate getTglKembali() { return tglKembali; }

    // public double hitungTotal() 
    //{
    //     return hargaStrategy.hitung(kendaraan.getHargaDasar(), this);
    // }

    // public void setHargaStrategy(HargaStrategy hargaStrategy) 
    //{
    //     this.hargaStrategy = hargaStrategy;
    // }
}
