package controller;

import hemija.app.UserSession;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import hemija.app.Config;
import model.IzvodjenjeDto;

public class PromenaStatusaControl implements EventHandler<ActionEvent> {

    private final TextField tfIdIzvodjenje;
    private final ComboBox<String> cbStatus;

    public PromenaStatusaControl(TextField tfIdIzvodjenje, ComboBox<String> cbStatus) {
        this.tfIdIzvodjenje = tfIdIzvodjenje;
        this.cbStatus = cbStatus;
    }

    @Override
    public void handle(ActionEvent event) {
        String idStr = tfIdIzvodjenje.getText();
        String noviStatus = cbStatus.getValue();

        if (idStr.isBlank() || noviStatus == null) return;

        int idIzvodjenje = Integer.parseInt(idStr);
        try {

            IzvodjenjeDto.updateStatus(
                    Config.getConnection(),
                    idIzvodjenje,
                    noviStatus,
                    UserSession.getLoggedInId());

            Alert alert = new Alert(
                    Alert.AlertType.INFORMATION,
                    "Status uspešno promenjen!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(
                    Alert.AlertType.ERROR,
                    e.getMessage());

            alert.showAndWait();
        }
        tfIdIzvodjenje.clear();
        cbStatus.setValue(null);
    }
}