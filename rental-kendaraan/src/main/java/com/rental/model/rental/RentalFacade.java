package com.rental.model.rental;

import java.sql.SQLException;

import com.rental.model.kendaraan.Kendaraan;
import com.rental.repository.*;

public class RentalFacade 
{    
    private KendaraanRepository kendaraanRepo;
    private RentalRepository rentalRepo;

    public RentalFacade() 
    {
        kendaraanRepo = new KendaraanRepository();
        rentalRepo = new RentalRepository();
    }

    public boolean prosesSewa(Rental rental)
    {
        Kendaraan kendaraan = rental.getKendaraan();

        // Cek ketersediaan
        if (!kendaraan.getStatus().equalsIgnoreCase("tersedia")) {
            System.out.println("Kendaraan tidak tersedia untuk disewa");
            return false;
        }
        try{
            // Update status kendaraan
            kendaraan.setStatus("tidak tersedia");
            kendaraanRepo.updateStatus(kendaraan.getNoPolisi(), "tidak tersedia");
            rentalRepo.insert(rental);
            System.out.println("Sewa kendaraan berhasil diproses");
            return true;
        }catch (Exception e) {
            System.err.println("Gagal menyimpan rental: " + e.getMessage());
            return false;
        }
    }

    /*Proses pengembalian kendaraan*/
    public boolean prosesPengembalian(Rental rental) 
    {
        Kendaraan kendaraan = rental.getKendaraan();
        try {
            kendaraan.setStatus("tersedia");
            kendaraanRepo.updateStatus(kendaraan.getNoPolisi(), "tersedia");
            rentalRepo.deleteRental(rental.getId());
            System.out.println("Pengembalian berhasil diproses");
            return true;
        }catch (SQLException e) {
            System.err.println("Gagal menyelesaikan pengembalian: " + e.getMessage());
            return false;
        }
    }
}
