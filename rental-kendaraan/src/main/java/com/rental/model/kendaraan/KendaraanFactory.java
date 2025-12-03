package com.rental.model.kendaraan;

public class KendaraanFactory 
{
     public static Kendaraan createKendaraan(String jenis,int noPolisi,String merk,String model,int tahun,double hargaDasar,String status) 
     {
        jenis = jenis.toLowerCase();

        switch (jenis) 
        {
            case "mobil":
                return new Mobil(noPolisi, merk, model, tahun, hargaDasar, status);

            case "truk":
                return new Truck(noPolisi, merk, model, tahun, hargaDasar, status);

            case "mobil_listrik":
            case "mobillistrik":
                return new MobilListrik(noPolisi, merk, model, tahun, hargaDasar, status);

            case "motor":
                return new Motor(noPolisi, merk, model, tahun, hargaDasar, status);

            case "motor_listrik":
            case "motorlistrik":
                return new MotorListrik(noPolisi, merk, model, tahun, hargaDasar, status);

            case "bus":
                return new Bus(noPolisi, merk, model, tahun, hargaDasar, status);

            default:
                throw new IllegalArgumentException("Jenis kendaraan tidak dikenal: " + jenis);
        }
    }
}
