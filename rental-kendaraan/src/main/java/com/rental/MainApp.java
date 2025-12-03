package com.rental;

import javax.swing.SwingUtilities;
import com.rental.view.MainView;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}
