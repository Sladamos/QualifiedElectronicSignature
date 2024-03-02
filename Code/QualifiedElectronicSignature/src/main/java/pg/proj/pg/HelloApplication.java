package pg.proj.pg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
        stage.setTitle("Qualified !");
        stage.setScene(scene);

        HelloController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}