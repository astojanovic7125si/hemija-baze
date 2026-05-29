package view;

import controller.BrisanjeSesijeControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BrisanjeSesijeView extends Stage {

    private final TextField tfIdSesija = new TextField();
    private final Button btObrisi = new Button("Obriši sesiju");

    public BrisanjeSesijeView() {
        btObrisi.setOnAction(new BrisanjeSesijeControl(tfIdSesija));

        GridPane grid = new GridPane();
        grid.addRow(0, new Label("ID Sesije:"), tfIdSesija);
        grid.addRow(1, btObrisi);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        super.setScene(new Scene(grid, 300, 150));
        super.setTitle("Brisanje sesije");
    }
}
