package view.Table;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.EksperimentDto;

import java.util.List;

public class EksperimentTable extends TableView<EksperimentDto> {

    public EksperimentTable(List<EksperimentDto> lista) {

        super(FXCollections.observableArrayList(lista));

        TableColumn<EksperimentDto, String> tcNaziv = new TableColumn<>("Naziv eksperimenta");
        TableColumn<EksperimentDto, String> tcTip = new TableColumn<>("Tip");
        TableColumn<EksperimentDto, String> tcLab = new TableColumn<>("Laboratorija");
        TableColumn<EksperimentDto, String> tcStatus = new TableColumn<>("Status");
        TableColumn<EksperimentDto, Integer> tcBrojSesija = new TableColumn<>("Broj sesija");
        TableColumn<EksperimentDto, Integer> tcId = new TableColumn<>("ID");

        tcNaziv.setCellValueFactory(new PropertyValueFactory<>("nazivEksperimenta"));
        tcTip.setCellValueFactory(new PropertyValueFactory<>("tipEksperimenta"));
        tcLab.setCellValueFactory(new PropertyValueFactory<>("nazivLaboratorije"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("statusIzvodjenja"));
        tcBrojSesija.setCellValueFactory(new PropertyValueFactory<>("brojSesija"));
        tcId.setCellValueFactory(new PropertyValueFactory<>("idIzvodjenje"));
        tcNaziv.setPrefWidth(350);
        tcTip.setPrefWidth(250);
        tcLab.setPrefWidth(250);
        tcStatus.setPrefWidth(150);
        tcBrojSesija.setPrefWidth(100);

        this.getColumns().addAll(
                tcId,
                tcNaziv,
                tcTip,
                tcLab,
                tcStatus,
                tcBrojSesija
        );

        this.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
    }
}