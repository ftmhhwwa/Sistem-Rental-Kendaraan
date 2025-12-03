package com.rental.strategy;

import com.rental.model.rental.Rental;

public interface HargaStrategy {
    double hitung(double hargaDasar, Rental rental);
}
