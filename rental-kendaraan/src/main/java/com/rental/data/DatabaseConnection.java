package com.rental.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseConnection 
{
    private static final String DB_HOST = "localhost"; 
    private static final String DB_PORT = "5432";
    private static final String DB_NAME = "postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "momoezzakuh";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
   // "jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres?sslmode=require";
    //private static final String DB_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    public static Connection getConnection() throws SQLException 
    {
        // try 
        // {
        //     return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        // } catch (SQLException e) 
        // {
        //     System.err.println("❌ Gagal koneksi ke database. Pastikan: ");
        //     System.err.println("1. Server hosting berjalan.");
        //     System.err.println("2. URL, username, dan password sudah benar.");
        //     System.err.println("3. Firewall atau Security Group mengizinkan akses dari IP Anda.");
        //     throw e;
        // }
        
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             
    }
}
