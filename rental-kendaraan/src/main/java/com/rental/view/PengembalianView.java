package com.rental.view;

import com.rental.model.rental.Rental;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PengembalianView extends JPanel {

    private JTable tableRental;
    private DefaultTableModel modelRental;
    private transient List<Rental> currentRentals = new ArrayList<>();
    public JTable getTableRental() { return tableRental; }
    public DefaultTableModel getModelRental() { return modelRental; }
    public List<Rental> getCurrentRentals() { return currentRentals; }

    public PengembalianView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel title = new JLabel("Pengembalian Kendaraan (Data Rental Aktif)");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }
    
    private JPanel createTablePanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        modelRental = new DefaultTableModel(new String[]{
                "ID", "Nama Customer", "No Polisi", "tanggal pinjam", "tanggal kembali", "Total Harga", "Status"
            }, 0){
            // Membuat kolom "Status" bisa berisi objek non-string seperti JButton
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) { 
                    return JButton.class;
                }
                return super.getColumnClass(columnIndex);
            }
            // Mencegah editing sel yang berisi data (hanya tombol yang bisa di-klik)
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Hanya kolom Status (tombol) yang bisa di-edit/klik
            }
        };

        tableRental = new JTable(modelRental);
        TableColumn statusColumn = tableRental.getColumnModel().getColumn(6);
        statusColumn.setCellRenderer(new ButtonRenderer());
        statusColumn.setCellEditor(new ButtonEditor(new JTextField()));
        statusColumn.setMaxWidth(120);
        p.add(new JScrollPane(tableRental), BorderLayout.CENTER);

        return p;
    }

    /**
     * Cell Renderer untuk menampilkan JButton di JTable.
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Dikembalikan");
            setBackground(new Color(46, 204, 113)); 
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JTextField tf) {
            super(tf);
            setClickCountToStart(1);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(46, 204, 113));
            button.setForeground(Color.WHITE);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(new Color(46, 204, 113));
            }
            label = "Dikembalikan";
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                //no-op
            }
            isPushed = false;
            return label; 
        }
    }
}