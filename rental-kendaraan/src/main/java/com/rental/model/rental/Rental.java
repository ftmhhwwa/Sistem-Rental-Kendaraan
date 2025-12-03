package com.rental.model.rental;
import java.time.LocalDate;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.pelanggan.Pelanggan;

public class Rental 
{
    private int id;
    private Pelanggan pelanggan;
    private Kendaraan kendaraan;
    private LocalDate tglPinjam;
    private LocalDate tglKembali;
    private double hargaTotal;
    private String strategyName;

     public Rental(int id,Kendaraan kendaraan, LocalDate tglPinjam, LocalDate tglKembali, Pelanggan pelanggan, double hargaTotal, String strategyName) 
     {
        this.id = id;
        this.pelanggan = pelanggan;
        this.kendaraan = kendaraan;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.hargaTotal = hargaTotal;
        this.strategyName = strategyName;
    }

    public int getId() 
    { 
        return id; 
    }

    public Pelanggan getPelanggan() 
    { 
        return pelanggan; 
    }

    public Kendaraan getKendaraan() 
    { 
        return kendaraan; 
    }

    public LocalDate getTglPinjam() 
    { 
        return tglPinjam; 
    }

    public LocalDate getTglKembali() 
    { 
        return tglKembali; 
    }

    public double getHargaTotal() 
    { 
        return hargaTotal; 
    }
    public void setHargaTotal(double hargaTotal) 

    { 
        this.hargaTotal = hargaTotal; 
    }

    public String getStrategyName() 
    { 
        return strategyName; 
    }

    public void setStrategyName(String strategyName) 
    { 
        this.strategyName = strategyName; 
    }
}
