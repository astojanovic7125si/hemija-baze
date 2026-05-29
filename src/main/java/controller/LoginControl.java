package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import view.MainView;
import hemija.app.UserFileManager;
import hemija.app.UserSession;

public class LoginControl implements EventHandler<ActionEvent> {

    private static final Alert GRESKA = new Alert(Alert.AlertType.ERROR,
            "Pogrešan username ili password", ButtonType.OK);

    private final TextField tfUsername;
    private final TextField tfPassword;
    private final javafx.stage.Stage loginStage;

    public LoginControl(TextField tfUsername, TextField tfPassword, javafx.stage.Stage loginStage) {
        this.tfUsername = tfUsername;
        this.tfPassword = tfPassword;
        this.loginStage = loginStage;
    }

    @Override
    public void handle(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        int id = UserFileManager.login(username, password);
        if (id == -1) {
            GRESKA.showAndWait();
            return;
        }

        UserSession.setLoggedInId(id);
        loginStage.close();
        new MainView().show();
    }
}
