package view;

import hemija.app.Config;
import hemija.app.UserSession;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.EksperimentDto;
import view.Table.EksperimentTable;

import java.util.List;

public class EksperimentiView extends Stage {

    public EksperimentiView() {
        List<EksperimentDto> lista = EksperimentDto.readForIstrazivac(
                Config.getConnection(), UserSession.getLoggedInId());

        TableView<EksperimentDto> tabela = new EksperimentTable(lista);

        BorderPane root = new BorderPane();
        root.setCenter(tabela);

        super.setScene(new Scene(root, 800, 400));
        super.setTitle("Moji eksperimenti");
    }
}