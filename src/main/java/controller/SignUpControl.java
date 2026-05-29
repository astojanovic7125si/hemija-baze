package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import hemija.app.Config;
import hemija.app.UserFileManager;

import java.sql.*;

public class SignUpControl implements EventHandler<ActionEvent> {

    private static final Alert POSTOJI = new Alert(Alert.AlertType.ERROR,
            "Username već postoji", ButtonType.OK);
    private static final Alert NE_POSTOJI_ID = new Alert(Alert.AlertType.ERROR,
            "ID istraživača ne postoji u bazi", ButtonType.OK);
    private static final Alert USPEH = new Alert(Alert.AlertType.INFORMATION,
            "Registracija uspješna!", ButtonType.OK);

    private final TextField tfUsername;
    private final TextField tfPassword;
    private final TextField tfId;

    public SignUpControl(TextField tfUsername, TextField tfPassword, TextField tfId) {
        this.tfUsername = tfUsername;
        this.tfPassword = tfPassword;
        this.tfId = tfId;
    }

    @Override
    public void handle(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String idStr = tfId.getText();

        if (username.isBlank() || password.isBlank() || idStr.isBlank()) return;

        if (UserFileManager.usernameExists(username)) {
            POSTOJI.showAndWait();
            return;
        }

        int id = Integer.parseInt(idStr);
        if (!idPostojiUBazi(id)) {
            NE_POSTOJI_ID.showAndWait();
            return;
        }

        UserFileManager.register(username, password, id);
        USPEH.showAndWait();
    }

    private boolean idPostojiUBazi(int id) {
        String query = "SELECT id_istrazivac FROM istrazivac WHERE id_istrazivac = ?";
        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
