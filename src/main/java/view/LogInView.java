package view;

import controller.LoginControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LogInView extends Stage {
    private final TextField tfUsername = new TextField();
    private final PasswordField tfPassword = new PasswordField();
    private final Button btLogin = new Button("Login");
    private final Button btGoToSignUp = new Button("Nemam nalog");

    public LogInView() {
        btLogin.setOnAction(new LoginControl(tfUsername, tfPassword, this));
        btGoToSignUp.setOnAction(e -> new SignUpView().show());

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("Username:"), tfUsername);
        grid.addRow(1, new Label("Password:"), tfPassword);
        grid.addRow(2, btLogin, btGoToSignUp);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        super.setScene(new Scene(grid, 350, 200));
        super.setTitle("Login");
    }
}
