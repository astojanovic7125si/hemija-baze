package rs.hemija.app.ui;

import rs.hemija.app.model.User;
import rs.hemija.app.service.UserService;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JTextField idIstrazivacField;

    public SignupFrame() {
        setTitle("Registracija korisnika");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initGUI();
    }

    private void initGUI() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        roleBox = new JComboBox<>(new String[]{
                "EKSTERNI",
                "ISTRAZIVAC",
                "ADMIN"
        });

        idIstrazivacField = new JTextField();
        idIstrazivacField.setEnabled(false);

        roleBox.addActionListener(e -> {
            String role = (String) roleBox.getSelectedItem();
            idIstrazivacField.setEnabled("ISTRAZIVAC".equals(role));
        });

        JButton registerButton = new JButton("Registruj se");
        JButton cancelButton = new JButton("Nazad");

        panel.add(new JLabel("Korisnicko ime:"));
        panel.add(usernameField);

        panel.add(new JLabel("Lozinka:"));
        panel.add(passwordField);

        panel.add(new JLabel("Uloga:"));
        panel.add(roleBox);

        panel.add(new JLabel("ID istrazivaca:"));
        panel.add(idIstrazivacField);

        panel.add(cancelButton);
        panel.add(registerButton);

        add(panel);

        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> dispose());
    }

    private void register() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String role = (String) roleBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Korisnicko ime i lozinka ne smeju biti prazni.");
            return;
        }

        Integer idIstrazivac = null;

        if ("ISTRAZIVAC".equals(role)) {
            String idText = idIstrazivacField.getText().trim();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Za istrazivaca morate uneti ID istrazivaca.");
                return;
            }

            try {
                idIstrazivac = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID istrazivaca mora biti broj.");
                return;
            }
        }

        User newUser = new User(username, password, role, idIstrazivac);

        boolean success = UserService.registerUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Korisnik je uspesno registrovan.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Korisnicko ime vec postoji.");
        }
    }
}