package view;

import controller.PromenaStatusaControl;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PromenaStatusaView extends Stage {

    private final TextField tfIdIzvodjenje = new TextField();
    private final ComboBox<String> cbStatus =
            new ComboBox<>(FXCollections.observableArrayList(
                    "planirano",
                    "zavrseno_uspesno",
                    "zavrseno_neuspesno"));
    private final Button btPromijeni = new Button("Promeni status");

    public PromenaStatusaView() {
        btPromijeni.setOnAction(new PromenaStatusaControl(tfIdIzvodjenje, cbStatus));

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("ID Izvođenja:"), tfIdIzvodjenje);
        grid.addRow(1, new Label("Novi status:"), cbStatus);
        grid.addRow(2, btPromijeni);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        super.setScene(new Scene(grid, 400, 200));
        super.setTitle("Promena statusa");
    }
}
