package com.rental.model.kendaraan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

class KendaraanFactoryTest 
{
    private final String noPolisi = "T001XX";
    private final String merk = "TestMerk";
    private final String model = "TestModel";
    private final int tahun = 2025;
    private final double hargaDasar = 100000.0;
    private final String status = "tersedia";

    // --- 2.1. Pengujian Jenis Kendaraan yang Valid ---
    @ParameterizedTest
    @CsvSource({
        "mobil, Mobil",
        "truck, Truck",
        "mobil_listrik, MobilListrik",
        "mobillistrik, MobilListrik",
        "motor, Motor",
        "motor_listrik, MotorListrik",
        "motorlistrik, MotorListrik",
        "bus, Bus"
    })
    void testCreateKendaraan_ValidJenis(String jenisInput, String expectedSimpleName) 
    {
        Kendaraan kendaraan = KendaraanFactory.createKendaraan(jenisInput, noPolisi, merk, model, tahun, hargaDasar, status);
        assertNotNull(kendaraan, "Objek Kendaraan tidak boleh null untuk jenis: " + jenisInput);
        assertEquals(expectedSimpleName, kendaraan.getClass().getSimpleName(),
                     "Factory gagal membuat tipe yang diharapkan untuk input: " + jenisInput);
        assertEquals(noPolisi, kendaraan.getNoPolisi());
    }

    // --- 2.2. Pengujian Jenis Kendaraan yang Tidak Dikenal ---
    
    @Test
    void testCreateKendaraan_InvalidJenis() 
    {
        String jenisTidakDikenal = "sepeda_roda_tiga";
        assertThrows(IllegalArgumentException.class, () -> {
            KendaraanFactory.createKendaraan(
                jenisTidakDikenal, noPolisi, merk, model, tahun, hargaDasar, status
            );
        }, "Harus melempar IllegalArgumentException untuk jenis yang tidak dikenal.");
    }
}
