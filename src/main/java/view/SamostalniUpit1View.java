package view;

import hemija.app.Config;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SamostalniUpit1Dto;
import view.Table.SamostalniUpit1Table;

import java.util.List;

public class SamostalniUpit1View extends Stage {

    public SamostalniUpit1View() {

        List<SamostalniUpit1Dto> lista =
                SamostalniUpit1Dto.readAll(Config.getConnection());

        TableView<SamostalniUpit1Dto> tabela =
                new SamostalniUpit1Table(lista);

        BorderPane root = new BorderPane();
        root.setCenter(tabela);

        super.setScene(new Scene(root, 800, 400));
        super.setTitle("Samostalni upit 1 - uspešni eksperimenti po istraživaču");
    }
}