package com.rental.repository;

import com.rental.data.DatabaseConnection;
import com.rental.model.kendaraan.Kendaraan;
import com.rental.model.kendaraan.KendaraanFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/* Repository untuk mengelola operasi CRUD pada entitas Kendaraan di database PostgreSQL.*/
public class KendaraanRepository 
{
    /* method untuk memetakan ResultSet menjadi objek Kendaraan.Menggunakan KendaraanFactory untuk membuat instance subclass yang tepat. */
    private Kendaraan mapResultSetToKendaraan(ResultSet rs) throws SQLException 
    {
        // Ambil data umum dari kolom
        String noPolisi = rs.getString("no_polisi");
        String jenis = rs.getString("jenis");
        String merk = rs.getString("merk");
        String model = rs.getString("model");
        int tahun = rs.getInt("tahun");
        double hargaDasar = rs.getDouble("harga_dasar");
        String status = rs.getString("status");

        return KendaraanFactory.createKendaraan(jenis, noPolisi, merk, model, tahun, hargaDasar, status);
    }
    
    /* Menyimpan data Kendaraan baru ke database (Create).*/
    public boolean save(Kendaraan kendaraan) 
    {
        String sql = "INSERT INTO kendaraan (no_polisi, jenis, merk, model, tahun, harga_dasar, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            String jenis = kendaraan.getClass().getSimpleName();
            
            pstmt.setString(1, kendaraan.getNoPolisi());
            pstmt.setString(2, jenis); 
            pstmt.setString(3, kendaraan.getMerk());
            pstmt.setString(4, kendaraan.getModel());
            pstmt.setInt(5, kendaraan.getTahun());
            pstmt.setDouble(6, kendaraan.getHargaDasar());
            pstmt.setString(7, kendaraan.getStatus());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan data kendaraan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
    }

    /*Mengambil semua data Kendaraan dari database (Read All).*/
    public List<Kendaraan> findAll() 
    {
        List<Kendaraan> kendaraanList = new ArrayList<>();
        String sql = "SELECT * FROM kendaraan ORDER BY no_polisi";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                kendaraanList.add(mapResultSetToKendaraan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data kendaraan: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
             System.err.println("Error saat membuat objek kendaraan: " + e.getMessage());
             e.printStackTrace();
        }
        return kendaraanList;
    }

    public List<Kendaraan> findAllTersedia() 
    {
        List<Kendaraan> kendaraanList = new ArrayList<>();
        String sql = "SELECT * FROM kendaraan WHERE status = 'tersedia' ORDER BY no_polisi";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
        {
            while (rs.next()) 
            {
                kendaraanList.add(mapResultSetToKendaraan(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil data kendaraan tersedia: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
             System.err.println("Error saat membuat objek kendaraan tersedia: " + e.getMessage());
             e.printStackTrace();
        }
        return kendaraanList;
    }

    /*Mengambil data Kendaraan berdasarkan nomor polisi (Read One).*/
    public Kendaraan findByNoPolisi(String noPolisi) 
    {
        Kendaraan kendaraan = null;
        String sql = "SELECT * FROM kendaraan WHERE no_polisi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, noPolisi);
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    kendaraan = mapResultSetToKendaraan(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari data kendaraan: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
             System.err.println("Error saat membuat objek kendaraan: " + e.getMessage());
             e.printStackTrace();
        }
        return kendaraan;
    }

    /*Memperbarui data Kendaraan yang sudah ada (Update).*/
    public boolean update(Kendaraan kendaraan) 
    {
        String sql = "UPDATE kendaraan SET merk = ?, model = ?, tahun = ?, harga_dasar = ?, status = ? WHERE no_polisi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, kendaraan.getMerk());
            pstmt.setString(2, kendaraan.getModel());
            pstmt.setInt(3, kendaraan.getTahun());
            pstmt.setDouble(4, kendaraan.getHargaDasar());
            pstmt.setString(5, kendaraan.getStatus());
            pstmt.setString(6, kendaraan.getNoPolisi());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui data kendaraan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /*Memperbarui hanya status ketersediaan Kendaraan. */
    public boolean updateStatus(String noPolisi, String newStatus) 
    {
        String sql = "UPDATE kendaraan SET status = ? WHERE no_polisi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, newStatus);
            pstmt.setString(2, noPolisi);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui status kendaraan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*Menghapus data Kendaraan dari database (Delete).*/
    public boolean delete(String noPolisi) 
    {
        String sql = "DELETE FROM kendaraan WHERE no_polisi = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            pstmt.setString(1, noPolisi);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal menghapus data kendaraan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /*Menghitung jumlah kendaraan berdasarkan status */
    public int countByStatus(String status) 
    {
        String sql = "SELECT COUNT(*) FROM kendaraan";
        if (status != null && !status.isEmpty()) {
            sql += " WHERE status = ?";
        }
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {
            if (status != null && !status.isEmpty()) {
                pstmt.setString(1, status);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung kendaraan berdasarkan status: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}