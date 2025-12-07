package com.rental.model.rental;

import java.sql.SQLException;
import java.util.logging.Logger;

import com.rental.model.kendaraan.Kendaraan;
import com.rental.repository.*;

public class RentalFacade 
{    
    private KendaraanRepository kendaraanRepo;
    private RentalRepository rentalRepo;
    private static final Logger logger = Logger.getLogger(RentalFacade.class.getName());
    private static final String STATUS_TERSEDIA = "tersedia";

    public RentalFacade() 
    {
        kendaraanRepo = new KendaraanRepository();
        rentalRepo = new RentalRepository();
    }

    public boolean prosesSewa(Rental rental)
    {
        Kendaraan kendaraan = rental.getKendaraan();

        // Cek ketersediaan
        if (!kendaraan.getStatus().equalsIgnoreCase(STATUS_TERSEDIA)) {
            logger.info("Kendaraan tidak tersedia untuk disewa");
            return false;
        }
        try{
            // Update status kendaraan
            kendaraan.setStatus("tidak tersedia");
            kendaraanRepo.updateStatus(kendaraan.getNoPolisi(), "tidak tersedia");
            rentalRepo.insert(rental);
            logger.info("Sewa kendaraan berhasil diproses");
            return true;
        }catch (Exception e) {
            logger.severe("Gagal menyimpan rental: " + e.getMessage());
            return false;
        }
    }

    /*Proses pengembalian kendaraan*/
    public boolean prosesPengembalian(Rental rental) 
    {
        Kendaraan kendaraan = rental.getKendaraan();
        try {
            kendaraan.setStatus(STATUS_TERSEDIA);
            kendaraanRepo.updateStatus(kendaraan.getNoPolisi(), STATUS_TERSEDIA);
            rentalRepo.deleteRental(rental.getId());
            logger.info("Pengembalian berhasil diproses");
            return true;
        }catch (SQLException e) {
            logger.severe("Gagal menyelesaikan pengembalian: " + e.getMessage());
            return false;
        }
    }
}
