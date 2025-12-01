package com.rental.model.kendaraan;

public abstract class Kendaraan 
{
    //atribut-----------------------------------------------
    private int noPolisi;
    private String merk;
    private String model;
    private int tahun;
    private int kapasitas;
    private double hargaDasar;
    private String status;

    //konstruktor-----------------------------------------------
    public Kendaraan(int noPolisi, String merk, String model, int tahun, int kapasitas, double hargaDasar, String status )
    {
        this.noPolisi = noPolisi;
        this.merk = merk;
        this.model = model;
        this.tahun = tahun;
        this.kapasitas = kapasitas;
        this.hargaDasar = hargaDasar;
        this.status = "tersedia";
    }

    ///getter-----------------------------------------------
    public int getNoPolisi()
    {
        return noPolisi;
    }
    public String getMerk()
    {
        return merk;
    }
    public String getModel()
    {
        return model;
    }
    public String getStatus()
    {
        return status;
    }
    public int getTahun()
    {
        return tahun;
    }
    public int getKapasitas()
    {
        return kapasitas;
    }
    public double getHargaDasar()
    {
        return hargaDasar;
    }

    //setter-----------------------------------------------
    public void setId(int noPolisi)
    {
         this.noPolisi = noPolisi; 
    }
    public void setTipe(String merk) 
    {
        this.merk = merk; 
    }
    public void setModel(String model) 
    {
        this.model = model; 
    }
    public void setTahun(int tahun) 
    {
         this.tahun = tahun; 
    }
    public void setKapasitas(int kapasitas) 
    {
         this.kapasitas = kapasitas; 
    }
    public void setHargaDasar(double hargaDasar) 
    {
         this.hargaDasar = hargaDasar; 
    }
    public void setStatus(String status) 
    {        
        this.status = status; 
    }    

}

