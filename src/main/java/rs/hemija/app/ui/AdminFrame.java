package rs.hemija.app.ui;

import rs.hemija.app.db.Db;
import rs.hemija.app.model.User;
import rs.hemija.app.util.TableHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminFrame extends JFrame{

    private User user;

    public AdminFrame(User user){

        this.user = user;

        setTitle("Admin");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGUI();
    }

    private void initGUI(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1,10,10));

        JButton laboratorijeButton = new JButton("Pregled laboratorija");

        JButton eksperimentiButton = new JButton("Pregled eksperimenata");

        JButton sesijeButton = new JButton("Pregled sesija");

        JButton updateSessionButton = new JButton("Izmeni sesiju");

        JButton deleteLabButton = new JButton("Obrisi laboratoriju");

        JButton logoutButton = new JButton("Odjavi se");

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        panel.add(laboratorijeButton);
        panel.add(eksperimentiButton);
        panel.add(sesijeButton);
        panel.add(updateSessionButton);
        panel.add(deleteLabButton);
        panel.add(logoutButton);

        add(panel);

        laboratorijeButton.addActionListener(e -> showLaboratorije());

        eksperimentiButton.addActionListener(e -> showEksperimenti());

        sesijeButton.addActionListener(e -> showSesije());

        updateSessionButton.addActionListener(e -> updateSession());

        deleteLabButton.addActionListener(e -> deleteLab());

        logoutButton.addActionListener(e -> logout());


    }
    private void showLaboratorije(){

        String sql = """
                SELECT id_lab,
                       naziv,
                       lokacija,
                       specijalizacija
                FROM laboratorija
                ORDER BY naziv
                """;

        TableHelper.showQuery("Laboratorije", sql);

    }

    private void showEksperimenti(){
        String sql = """
                SELECT e.id_eksperiment,
                       e.naziv,
                       e.tip,
                       e.cilj,
                       t.naziv AS teorija
                FROM eksperiment e
                JOIN teorija t
                    ON e.id_teorija = t.id_teorija
                ORDER BY e.id_eksperiment
                """;

        TableHelper.showQuery("Eksperimenti", sql);

    }

    private void showSesije(){

        String sql = """
                SELECT s.id_sesija,
                       s.datum,
                       s.vreme_pocetka,
                       s.vreme_zavrsetka,
                       e.naziv AS eksperiment,
                       i.status
                FROM sesija s
                JOIN izvodjenje i 
                    ON s.id_izvodjenje = i.id_izvodjenje
                JOIN eksperiment e
                    ON i.id_eksperiment = e.id_eksperiment
                ORDER BY s.datum DESC
        """;

        TableHelper.showQuery("Sesije", sql);
    }
    private void logout(){
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void updateSession(){

        JTextField idField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField startField = new JTextField();
        JTextField endField = new JTextField();

        Object[] fields = {
                "ID sesije:", idField,
                "Novi datum (YYYY-MM-DD):", dateField,
                "Novo vreme pocetka (HH:MM:SS):", startField,
                "Novo vreme zavrsetka (HH:MM:SS):", endField
        };

        int option = JOptionPane.showConfirmDialog(
                this, fields, "Izmena sesije", JOptionPane.OK_CANCEL_OPTION);

        if(option != JOptionPane.OK_OPTION){
            return;
        }

        try{

            int id = Integer.parseInt(idField.getText().trim());

            String sql = """
                    UPDATE sesija
                    SET datum = ?,
                        vreme_pocetka = ?,
                        vreme_zavrsetka = ?
                    WHERE id_sesija = ?
                    """;

            try (Connection connection = Db.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1, dateField.getText().trim());
                ps.setString(2, startField.getText().trim());
                ps.setString(3, endField.getText().trim());
                ps.setInt(4, id);

                int rows = ps.executeUpdate();

                if(rows > 0){
                    JOptionPane.showMessageDialog(this, "Sesija je uspesno izmenjena!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nema sesije sa unetim ID-jem!");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska pri izmeni sesije!");
        }

    }

    private void deleteLab() {
        String input = JOptionPane.showInputDialog(this, "Unesite ID laboratorije:");

        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int idLab = Integer.parseInt(input.trim());

            try (Connection connection = Db.getConnection()) {
                connection.setAutoCommit(false);

                String checkSql = """
                    SELECT COUNT(*)
                    FROM izvodjenje
                    WHERE id_lab = ?
                    """;

                try (java.sql.PreparedStatement ps = connection.prepareStatement(checkSql)) {
                    ps.setInt(1, idLab);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            connection.rollback();
                            JOptionPane.showMessageDialog(this,
                                    "Laboratorija ne moze biti obrisana " +
                                            "jer postoje izvodjenja u toj laboratoriji.");
                            return;
                        }
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM lab_resurs WHERE id_lab = ?")) {
                    ps.setInt(1, idLab);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM alat WHERE id_lab = ?")) {
                    ps.setInt(1, idLab);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(
                        "DELETE FROM laboratorija WHERE id_lab = ?")) {
                    ps.setInt(1, idLab);

                    int rows = ps.executeUpdate();

                    if (rows > 0) {
                        connection.commit();
                        JOptionPane.showMessageDialog(this, "Laboratorija je uspesno obrisana.");
                    } else {
                        connection.rollback();
                        JOptionPane.showMessageDialog(this, "Laboratorija nije pronadjena.");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greska pri brisanju laboratorije.");
        }
    }
}
