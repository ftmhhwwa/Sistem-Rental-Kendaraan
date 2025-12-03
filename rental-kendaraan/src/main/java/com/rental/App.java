package com.rental;
import java.sql.Connection;
import java.sql.SQLException;
import com.rental.data.DatabaseConnection;
import javax.swing.SwingUtilities;
import com.rental.view.MainView;

public class App 
{
    public static void main( String[] args )
    {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) 
        {
            System.out.println("Gagal konek: " + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
