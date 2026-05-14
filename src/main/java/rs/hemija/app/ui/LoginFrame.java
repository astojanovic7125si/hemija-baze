package rs.hemija.app.ui;

import rs.hemija.app.model.User;
import rs.hemija.app.service.UserService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame{

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame(){

        setTitle("Hemija-Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initGUI();
    }

    private void initGUI(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign up");

        panel.add(usernameLabel);
        panel.add(usernameField);

        panel.add(passwordLabel);
        panel.add(passwordField);

        panel.add(loginButton);
        panel.add(signupButton);

        add(panel);

        loginButton.addActionListener(e -> login());

        signupButton.addActionListener(e -> {
            new SignupFrame().setVisible(true);
        });
    }

    private void login(){

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = UserService.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(null, "Invalid username or password");
            return;
        }

        JOptionPane.showMessageDialog(null, "Login successful");

        dispose();

        switch (user.role) {
            case "ADMIN":
                new AdminFrame(user).setVisible(true);
                break;
            case "ISTRAZIVAC":
                new ResearcherFrame(user).setVisible(true);
                break;
            case "EKSTERNI":
                new ExternalFrame(user).setVisible(true);
                break;
        }
    }
}
