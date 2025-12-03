package com.rental.strategy;

import com.rental.model.rental.Rental;

public class NormalStrategy implements HargaStrategy {

    @Override
    public double hitung(double hargaDasar, Rental rental) {
        int lama = rental.getTglPinjam().until(rental.getTglKembali()).getDays();
        if (lama <= 0) lama = 1;
        return hargaDasar * lama;
    }
}
