package com.rental.repository;

import com.rental.data.DatabaseConnection;
import com.rental.model.pelanggan.Pelanggan;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.rental.Rental;

import java.sql.*;
import java.time.LocalDate;

public class RentalRepository {

    private final PelangganRepository pelangganRepo = new PelangganRepository();
    private final KendaraanRepository kendaraanRepo = new KendaraanRepository();

    public void insert(Rental rental) throws SQLException {
        String sql = "INSERT INTO rental (pelanggan_id, no_polisi, tgl_pinjam, tgl_kembali, harga_total, strategy_name) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, rental.getPelanggan().getId());
            pst.setString(2, rental.getKendaraan().getNoPolisi());
            pst.setObject(3, rental.getTglPinjam());
            pst.setObject(4, rental.getTglKembali());
            pst.setDouble(5, rental.getHargaTotal());
            pst.setString(6, rental.getStrategyName());
            pst.executeUpdate();
        }
    }

    public Rental findById(int id) throws SQLException {
        String sql = "SELECT * FROM rental WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                Pelanggan p = pelangganRepo.findById(rs.getInt("pelanggan_id"));
                Kendaraan k = kendaraanRepo.findByNoPolisi(rs.getString("no_polisi"));

                return new Rental(
                    rs.getInt("id"),
                    k,
                    rs.getObject("tgl_pinjam", LocalDate.class),
                    rs.getObject("tgl_kembali", LocalDate.class),
                    p,
                    rs.getDouble("harga_total"),
                    rs.getString("strategy_name")
                );
            }
        }
        return null;
    }
}
