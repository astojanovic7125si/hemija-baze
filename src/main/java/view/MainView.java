package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView extends Stage {

    private final Button btEksperimenti = new Button("Pregled eksperimenata");
    private final Button btPromenaStatusa = new Button("Promena statusa izvođenja");
    private final Button btBrisanjeSesije = new Button("Brisanje sesije");
    private final Button btSamostalniUpit1 = new Button("Samostalni upit 1");
    private final Button btOdjava = new Button("Odjavi se");

    public MainView() {
        btEksperimenti.setOnAction(e -> new EksperimentiView().show());
        btPromenaStatusa.setOnAction(e -> new PromenaStatusaView().show());
        btBrisanjeSesije.setOnAction(e -> new BrisanjeSesijeView().show());
        btSamostalniUpit1.setOnAction(e -> new SamostalniUpit1View().show());

        btOdjava.setOnAction(e -> {
            this.close();
            new LogInView().show();
        });

        btEksperimenti.setMaxWidth(Double.MAX_VALUE);
        btPromenaStatusa.setMaxWidth(Double.MAX_VALUE);
        btBrisanjeSesije.setMaxWidth(Double.MAX_VALUE);
        btSamostalniUpit1.setMaxWidth(Double.MAX_VALUE);
        btOdjava.setMaxWidth(Double.MAX_VALUE);

        VBox vbox = new VBox(
                15,
                btEksperimenti,
                btPromenaStatusa,
                btBrisanjeSesije,
                btSamostalniUpit1,
                btOdjava
        );

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        super.setScene(new Scene(vbox, 400, 330));
        super.setTitle("Hemija - istraživač");
    }
}