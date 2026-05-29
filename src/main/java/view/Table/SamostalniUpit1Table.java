package view.Table;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SamostalniUpit1Dto;

import java.util.List;

public class SamostalniUpit1Table extends TableView<SamostalniUpit1Dto> {

    public SamostalniUpit1Table(List<SamostalniUpit1Dto> lista) {

        super(FXCollections.observableArrayList(lista));

        TableColumn<SamostalniUpit1Dto, Integer> tcId =
                new TableColumn<>("ID istraživača");

        TableColumn<SamostalniUpit1Dto, String> tcIme =
                new TableColumn<>("Ime");

        TableColumn<SamostalniUpit1Dto, String> tcPrezime =
                new TableColumn<>("Prezime");

        TableColumn<SamostalniUpit1Dto, Integer> tcBroj =
                new TableColumn<>("Broj uspešnih eksperimenata");

        tcId.setCellValueFactory(new PropertyValueFactory<>("idIstrazivac"));
        tcIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        tcPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        tcBroj.setCellValueFactory(new PropertyValueFactory<>("brojUspesnihEksperimenata"));

        tcId.setPrefWidth(120);
        tcIme.setPrefWidth(180);
        tcPrezime.setPrefWidth(180);
        tcBroj.setPrefWidth(250);

        this.getColumns().addAll(
                tcId,
                tcIme,
                tcPrezime,
                tcBroj
        );

        this.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }
}