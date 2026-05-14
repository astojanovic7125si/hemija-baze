package rs.hemija.app.ui;

import rs.hemija.app.model.User;
import rs.hemija.app.util.TableHelper;

import javax.swing.*;
import java.awt.*;

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
        panel.setLayout(new GridLayout(4,1,10,10));

        JButton laboratorijeButton = new JButton("Pregled laboratorija");

        JButton eksperimentiButton = new JButton("Pregled eksperimenata");

        JButton sesijeButton = new JButton("Pregled sesija");

        JButton logoutButton = new JButton("Odjavi se");

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        panel.add(laboratorijeButton);
        panel.add(eksperimentiButton);
        panel.add(sesijeButton);
        panel.add(logoutButton);

        add(panel);

        laboratorijeButton.addActionListener(e -> showLaboratorije());

        eksperimentiButton.addActionListener(e -> showEksperimenti());

        sesijeButton.addActionListener(e -> showSesije());

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
}
