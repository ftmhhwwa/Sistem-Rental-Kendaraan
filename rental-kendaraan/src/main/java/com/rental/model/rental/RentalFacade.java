package com.rental.model.rental;

import com.rental.model.kendaraan.Kendaraan;

public class RentalFacade 
{    
    // private KendaraanRepository kendaraanRepo;
    // private RentalRepository rentalRepo;

    public RentalFacade() 
    {
        // kendaraanRepo = new KendaraanRepository();
        // rentalRepo = new RentalRepository();
    }

    public boolean prosesSewa(Rental rental) {

        Kendaraan kendaraan = rental.getKendaraan();

        // Cek ketersediaan
        if (!kendaraan.getStatus().equalsIgnoreCase("tersedia")) {
            System.out.println("Kendaraan tidak tersedia untuk disewa");
            return false;
        }

        // Update status kendaraan
        kendaraan.setStatus("tidak tersedia");
        //kendaraanRepo.updateStatus(kendaraan.getId(), "tidak tersedia");

        // Save rental ke database
        //rentalRepo.save(rental);

        System.out.println("Sewa kendaraan berhasil diproses");
        return true;
    }

    /**
     * Proses pengembalian kendaraan
     */
    public boolean prosesPengembalian(Rental rental) {

        Kendaraan kendaraan = rental.getKendaraan();

        // Update status kendaraan → tersedia kembali
        kendaraan.setStatus("tersedia");
        //kendaraanRepo.updateStatus(kendaraan.getId(), "tersedia");

        // Tandai rental selesai
        //rentalRepo.selesaikanRental(rental.getId());

        System.out.println("Pengembalian berhasil diproses");
        return true;
    }
}
