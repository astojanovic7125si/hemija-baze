package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import hemija.app.Config;
import hemija.app.UserSession;

import java.sql.*;

public class BrisanjeSesijeControl implements EventHandler<ActionEvent> {

    private static final Alert GRESKA = new Alert(Alert.AlertType.ERROR,
            "Brisanje nije dozvoljeno", ButtonType.OK);

    private final TextField tfIdSesija;

    public BrisanjeSesijeControl(TextField tfIdSesija) {
        this.tfIdSesija = tfIdSesija;
    }

    @Override
    public void handle(ActionEvent event) {
        String idStr = tfIdSesija.getText();
        if (idStr.isBlank()) return;

        int idSesija = Integer.parseInt(idStr);
        int idIstrazivac = UserSession.getLoggedInId();

        String query = "CALL obrisi_sesiju_istrazivaca(?, ?)";
        try {
            PreparedStatement ps = Config.getConnection().prepareStatement(query);
            ps.setInt(1, idSesija);
            ps.setInt(2, idIstrazivac);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Alert alert = new Alert(
                        Alert.AlertType.INFORMATION,
                        rs.getString("poruka"));

                alert.showAndWait();
            }

            tfIdSesija.clear();
        } catch (SQLException e) {
            GRESKA.showAndWait();
        }
    }
}
