package rs.hemija.app.ui;

import rs.hemija.app.db.Db;
import rs.hemija.app.model.User;
import rs.hemija.app.util.TableHelper;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ResearcherFrame extends JFrame{

    private User user;

    public ResearcherFrame(User user) {

        setTitle("Istrazivac");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.user=user;
        initGUI();
    }

    private void initGUI(){
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4,1,10,10));

        JButton eksperimentiButton = new JButton("Moji eksperimenti");

        JButton statusButton = new JButton("Promeni status izvodjenja");

        JButton obrisiSesijuButton = new JButton("Obrisi sesiju");

        JButton logoutButton = new JButton("Odjavi se");

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        panel.add(eksperimentiButton);
        panel.add(statusButton);
        panel.add(obrisiSesijuButton);
        panel.add(logoutButton);

        add(panel);

        eksperimentiButton.addActionListener(e -> showMyExperiments());

        statusButton.addActionListener(e -> updateStatus());

        obrisiSesijuButton.addActionListener(e -> deleteSession());

        logoutButton.addActionListener(e -> logout());

    }

    private void showMyExperiments(){

        String sql = """
                SELECT i.id_izvodjenje,
                       e.naziv AS eksperiment,
                       e.tip,
                       l.naziv AS laboratorija,
                       i.datum,
                       i.status,
                       iz.uloga
                FROM izvodjac iz
                JOIN izvodjenje i
                    ON iz.id_izvodjenje = i.id_izvodjenje
                JOIN eksperiment e
                    ON i.id_eksperiment = e.id_eksperiment
                JOIN laboratorija l
                    ON i.id_lab = l.id_lab
                WHERE iz.id_istrazivac = %d
                ORDER BY i.datum DESC
        """.formatted(user.idIstrazivac);

        TableHelper.showQuery("Moji eksperimenti",sql);
    }

    private void updateStatus(){

        String id = JOptionPane.showInputDialog(this,"Unesite ID izvodjenja:");

        String status = JOptionPane.showInputDialog(this,"Novi status:");

        String sql = """
                UPDATE izvodjenje
                SET status = ?
                WHERE id_izvodjenje = ?
                """;

        try (Connection connection = Db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, status);
            ps.setInt(2, Integer.parseInt(id));

            int rows = ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Azurirano redova: " + rows);
        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(this,"Greska!");
        }
    }

    private void deleteSession(){

        String id = JOptionPane.showInputDialog(this,"Unesite ID sesije:");

        String sql = """
                DELETE s
                FROM sesija s
                JOIN izvodjenje i
                    ON s.id_izvodjenje = i.id_izvodjenje
                JOIN izvodjac iz
                    ON i.id_izvodjenje = iz.id_izvodjenje
                WHERE s.id_sesija = ?
                    AND iz.id_istrazivac = ?
                """;

        try (Connection connection = Db.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, Integer.parseInt(id));
            ps.setInt(2, user.idIstrazivac);

            int rows = ps.executeUpdate();

            JOptionPane.showMessageDialog(this,"Obrisano sesija: " + rows);
        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Greska!");
        }
    }

    private void logout(){
        dispose();
        new LoginFrame().setVisible(true);
    }
}
