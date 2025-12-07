package com.rental;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.rental.data.DatabaseConnection;
import javax.swing.SwingUtilities;
import com.rental.view.MainView;

public class App 
{
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        try (Connection conn = DatabaseConnection.getConnection()) {
            logger.info("Koneksi berhasil!");
        } catch (SQLException e) 
        {
            logger.severe("Gagal konek: " + e.getMessage());
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
