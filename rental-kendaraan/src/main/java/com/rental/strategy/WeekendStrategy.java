package com.rental.strategy;

import com.rental.model.rental.Rental;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeekendStrategy implements HargaStrategy {

    private double tambahanPersen;

    public WeekendStrategy(double tambahanPersen) {
        this.tambahanPersen = tambahanPersen;
    }

    @Override
    public double hitung(double hargaDasar, Rental rental) {
        LocalDate start = rental.getTglPinjam();
        LocalDate end = rental.getTglKembali();
        
        int lama = start.until(end).getDays();
        if (lama <= 0) lama = 1;

        int weekendCount = 0;
        LocalDate tmp = start;

        while (!tmp.isAfter(end.minusDays(1))) {
            DayOfWeek day = tmp.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                weekendCount++;
            }
            tmp = tmp.plusDays(1);
        }

        double total = hargaDasar * lama;
        double tambah = total * (tambahanPersen / 100) * weekendCount / lama;

        return total + tambah;
    }
}
