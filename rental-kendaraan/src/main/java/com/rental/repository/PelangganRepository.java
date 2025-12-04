package com.rental.repository;

import com.rental.data.DatabaseConnection;
import com.rental.model.pelanggan.Pelanggan;

import java.sql.*;

public class PelangganRepository {

    public void insert(Pelanggan p) throws SQLException {
        String sql = "INSERT INTO pelanggan (nama, nik, no_hp, alamat) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, p.getNama());
            pst.setString(2, p.getNik());
            pst.setString(3, p.getNoHP());
            pst.setString(4, p.getAlamat());
            pst.executeUpdate();
        }
    }

    public Pelanggan findById(int id) throws SQLException {
        String sql = "SELECT id, nama, nik, no_hp, alamat FROM pelanggan WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Pelanggan(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nik"),
                    rs.getString("no_hp"),
                    rs.getString("alamat")
                );
            }
        }
        return null;
    }

    public Pelanggan findByNik(String nik) throws SQLException {
        String sql = "SELECT id, nama, nik, no_hp, alamat FROM pelanggan WHERE nik = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, nik);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Pelanggan(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("nik"),
                    rs.getString("no_hp"),
                    rs.getString("alamat")
                );
            }
        }
        return null;
    }
}
