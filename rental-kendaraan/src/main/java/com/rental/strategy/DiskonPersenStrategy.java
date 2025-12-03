package com.rental.strategy;

import com.rental.model.rental.Rental;

public class DiskonPersenStrategy implements HargaStrategy {

    private double persen;

    public DiskonPersenStrategy(double persen) {
        this.persen = persen;
    }

    @Override
    public double hitung(double hargaDasar, Rental rental) {
        int lama = rental.getTglPinjam().until(rental.getTglKembali()).getDays();
        if (lama <= 0) lama = 1;

        double total = hargaDasar * lama;
        double potongan = total * (persen / 100);

        return total - potongan;
    }
}
