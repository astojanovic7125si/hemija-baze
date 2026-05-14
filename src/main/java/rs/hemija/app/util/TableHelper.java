package rs.hemija.app.util;

import rs.hemija.app.db.Db;

import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

public class TableHelper {

    public static void showQuery(String title, String sql){

        try (Connection connection = Db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            DefaultTableModel model = new DefaultTableModel();

            for (int i = 1; i <= columnCount; i++) {

                model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()){
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {

                    row[i-1]  = rs.getObject(i);
                }
                model.addRow(row);
            }

            JTable table = new JTable(model);
            JFrame frame = new JFrame(title);

            frame.add(new  JScrollPane(table));

            frame.setSize(800,400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, "Greska pri izvrsavanju upita!");
        }

    }
}
