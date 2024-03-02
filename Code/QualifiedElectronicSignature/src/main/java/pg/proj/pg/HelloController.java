package pg.proj.pg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.File;

public class HelloController {

    @FXML
    private Label welcomeText;

    @Setter
    private Stage stage;

    @FXML
    protected void onHelloButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*"));

        fileChooser.showOpenDialog(stage);
    }

}
