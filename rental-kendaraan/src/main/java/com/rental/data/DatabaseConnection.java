package com.rental.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection 
{
    private DatabaseConnection() { }

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = System.getenv().getOrDefault("DB_PASSWORD", "admin");
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static Connection getConnection() throws SQLException 
    {        
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);      
    }
}
