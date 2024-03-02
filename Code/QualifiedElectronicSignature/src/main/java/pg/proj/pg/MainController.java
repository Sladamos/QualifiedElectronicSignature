package pg.proj.pg;

import javafx.fxml.FXML;
import lombok.Setter;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import java.io.IOException;

@Setter
public class MainController {

    private FileSelector fileSelector;

    @FXML
    protected void onEncryptClicked() {
        try {
            FileProvider sourceFileProvider = fileSelector.selectFile();
        } catch (IOException | NullPointerException e) {
            onErrorOccurred();
        }
    }

    @FXML
    protected void onDecryptClicked() {
        try {
            FileProvider sourceFileProvider = fileSelector.selectFile();
            System.out.println(sourceFileProvider.getFileInfo().canonicalPath());
        } catch (IOException | NullPointerException e) {
            onErrorOccurred();
        }
    }

    private void onErrorOccurred() {
        System.out.println("Error occurred not implemented");
    }

}
