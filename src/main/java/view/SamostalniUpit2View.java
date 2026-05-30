package view;

import hemija.app.Config;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.SamostalniUpit2Dto;
import view.Table.SamostalniUpit2Table;

import java.util.List;

public class SamostalniUpit2View extends Stage {

    public SamostalniUpit2View() {

        List<SamostalniUpit2Dto> lista =
                SamostalniUpit2Dto.readAll(Config.getConnection());

        TableView<SamostalniUpit2Dto> tabela =
                new SamostalniUpit2Table(lista);

        BorderPane root = new BorderPane();
        root.setCenter(tabela);

        super.setScene(new Scene(root, 900, 400));
        super.setTitle("Samostalni upit 2 - laboratorije po broju sesija");
    }
}