package rs.hemija.app.ui;

import rs.hemija.app.model.User;
import rs.hemija.app.service.UserService;
import rs.hemija.app.util.TableHelper;

import javax.swing.*;
import java.awt.*;

public class ExternalFrame extends JFrame{

    private User user;

    public ExternalFrame(User user){

        this.user = user;

        setTitle("Eksterni korisnik");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGUI();
    }

    private void initGUI(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        JButton laboratorijeButton = new JButton("Pregled laboratorija");
        JButton istrazivaciButton = new JButton("Pregled istrazivaca");
        JButton logoutButton = new JButton("Odjavi se");
        JButton updateButton = new JButton("Azuriraj podatke");
        JButton deleteButton = new JButton("Obrisi nalog");

        panel.add(laboratorijeButton);
        panel.add(istrazivaciButton);
        panel.add(logoutButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel);

        laboratorijeButton.addActionListener(e -> showLaboratorije());
        istrazivaciButton.addActionListener(e -> showIstrazivaci());
        logoutButton.addActionListener(e -> logout());
        updateButton.addActionListener(e -> updateAction());
        deleteButton.addActionListener(e -> deleteAction());
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

    private void showIstrazivaci(){

        String sql = """
                SELECT id_istrazivac,
                       ime,
                       prezime,
                       kvalifikacija,
                       vrsta
                FROM istrazivac
                ORDER BY prezime, ime
        """;

        TableHelper.showQuery("Istrazivaci", sql);
    }

    private void logout(){
        dispose();
        new LoginFrame().setVisible(true);
    }

    private void updateAction(){

        JTextField usernameField = new JTextField(user.username);
        JPasswordField passwordField = new JPasswordField(user.password);

        Object[] fields = {
                "Novo korisnicko ime:", usernameField,
                "Novo lozinka:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(
                this, fields, "Azuriranje naloga", JOptionPane.OK_CANCEL_OPTION);

        if(option != JOptionPane.OK_OPTION){
            return;
        }
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();

        if(newUsername.isEmpty() || newPassword.isEmpty()){
            JOptionPane.showMessageDialog(this, "Polja ne smeju biti prazna!");
            return;
        }

        boolean success = UserService.updateUser(user.username, newUsername, newPassword);

        if(success){

            user.username = newUsername;
            user.password = newPassword;
            setTitle("Eksterni korisnik - " + user.username);
            JOptionPane.showMessageDialog(this, "Nalog je uspesno azuriran!");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Korisnicko ime vec postoji ili je doslo do greske!");
        }
    }

    private void deleteAction(){

        JPasswordField passwordField = new JPasswordField();

        int option = JOptionPane.showConfirmDialog(
                this,
                passwordField,
                "Unesi lozinku za brisanje naloga",
                JOptionPane.OK_CANCEL_OPTION);

        if(option != JOptionPane.OK_OPTION){
            return;
        }

        String password = new String(passwordField.getPassword());

        boolean success = UserService.deleteUser(user.username, password);

        if(success){
            JOptionPane.showMessageDialog(this, "Nalog je uspesno obrisan!");
            logout();
        } else {
            JOptionPane.showMessageDialog(this, "Lozinka je greska!");
        }
    }
}
