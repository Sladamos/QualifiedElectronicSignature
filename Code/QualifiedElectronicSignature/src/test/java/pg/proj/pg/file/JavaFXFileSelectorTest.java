package pg.proj.pg.file;

import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.JavaFXFileSelector;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class JavaFXFileSelectorTest {

    @Test
    void shouldThrowCriticalAppErrorOnSelectFileWhenStageIsNotSet(){
        JavaFXFileSelector uut = new JavaFXFileSelector(null, "Title", Set.of(FileExtension.PDF), FileChooser::new);

        assertThatThrownBy(uut::selectFile).isInstanceOf(CriticalAppError.class);
    }

    @Test
    void shouldThrowCriticalAppErrorOnSelectFileWhenAllowedExtensionsAreEmpty(){
        Stage stage = mock(Stage.class);
        JavaFXFileSelector uut = new JavaFXFileSelector(stage, "Title", Set.of(), FileChooser::new);

        assertThatThrownBy(uut::selectFile).isInstanceOf(CriticalAppError.class);
    }

    @Test
    void shouldSetTitleAndExtensionsBeforeOpeningDialog(){
        String title = "Title";
        File file = getXmlFile();
        Stage stage = mock(Stage.class);
        FileChooser fileChooser = mock(FileChooser.class);
        when(fileChooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
        when(fileChooser.showOpenDialog(stage)).thenReturn(file);
        JavaFXFileSelector uut = new JavaFXFileSelector(stage, title, Set.of(FileExtension.PDF), () -> fileChooser);

        uut.selectFile();

        InOrder inOrder = inOrder(fileChooser);
        inOrder.verify(fileChooser).setTitle(title);
        inOrder.verify(fileChooser).getExtensionFilters();
        inOrder.verify(fileChooser).showOpenDialog(stage);
    }

    @Test
    void shouldCreateValidFileProvider() throws IOException {
        String title = "Title";
        File file = getXmlFile();
        Stage stage = mock(Stage.class);
        FileChooser fileChooser = mock(FileChooser.class);
        when(fileChooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
        when(fileChooser.showOpenDialog(stage)).thenReturn(file);
        FileExtension fileExtension = FileExtension.XML;
        JavaFXFileSelector uut = new JavaFXFileSelector(stage, title, Set.of(fileExtension), () -> fileChooser);

        FileProvider fileProvider = uut.selectFile();
        FileInfo fileInfo = fileProvider.getFileInfo();

        assertAll("Should create valid file info",
                () -> assertEquals(file.getName(), fileInfo.fileName()),
                () -> assertEquals(file.getCanonicalPath(), fileInfo.canonicalPath()),
                () -> assertEquals(fileExtension, fileInfo.extension())
        );


    }

    @Test
    void shouldThrowBasicAppErrorWhenFileSelectionWasAborted(){
        Stage stage = mock(Stage.class);
        FileChooser fileChooser = mock(FileChooser.class);
        when(fileChooser.getExtensionFilters()).thenReturn(FXCollections.observableArrayList());
        when(fileChooser.showOpenDialog(stage)).thenReturn(null);
        JavaFXFileSelector uut = new JavaFXFileSelector(stage, "Title", Set.of(FileExtension.PDF), () -> fileChooser);

        assertThatThrownBy(uut::selectFile).isInstanceOf(BasicAppError.class);
    }

    private File getXmlFile() {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource("signature.xml")).getFile());
    }

}
