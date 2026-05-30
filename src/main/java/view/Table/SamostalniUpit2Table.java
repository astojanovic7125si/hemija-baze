package view.Table;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SamostalniUpit2Dto;

import java.util.List;

public class SamostalniUpit2Table extends TableView<SamostalniUpit2Dto> {

    public SamostalniUpit2Table(List<SamostalniUpit2Dto> lista) {

        super(FXCollections.observableArrayList(lista));

        TableColumn<SamostalniUpit2Dto, Integer> tcId =
                new TableColumn<>("ID laboratorije");

        TableColumn<SamostalniUpit2Dto, String> tcNaziv =
                new TableColumn<>("Naziv laboratorije");

        TableColumn<SamostalniUpit2Dto, String> tcLokacija =
                new TableColumn<>("Lokacija");

        TableColumn<SamostalniUpit2Dto, Integer> tcBroj =
                new TableColumn<>("Broj sesija");

        tcId.setCellValueFactory(new PropertyValueFactory<>("idLab"));
        tcNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivLaboratorije"));
        tcLokacija.setCellValueFactory(new PropertyValueFactory<>("lokacija"));
        tcBroj.setCellValueFactory(new PropertyValueFactory<>("brojSesija"));

        this.getColumns().addAll(tcId, tcNaziv, tcLokacija, tcBroj);
        this.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }
}