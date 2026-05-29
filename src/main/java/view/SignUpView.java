package view;

import controller.SignUpControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SignUpView extends Stage {
    private final TextField tfUsername = new TextField();
    private final PasswordField tfPassword = new PasswordField();
    private final TextField tfId = new TextField();
    private final Button btRegister = new Button("Registruj se");

    public SignUpView() {
        btRegister.setOnAction(new SignUpControl(tfUsername, tfPassword, tfId));

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Username:"), tfUsername);
        grid.addRow(1, new Label("Password:"), tfPassword);
        grid.addRow(2, new Label("ID Istraživača:"), tfId);
        grid.addRow(3, btRegister);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        super.setScene(new Scene(grid, 350, 250));
        super.setTitle("Registracija");
    }
}
