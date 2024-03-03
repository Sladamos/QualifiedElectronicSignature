package pg.proj.pg.file.selector;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.extension.FileExtensionProvider;
import pg.proj.pg.file.extension.FileExtensionProviderImpl;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JavaFXFileSelector implements FileSelector {

    private final Stage stage;

    private final FileExtensionProvider fileExtensionProvider = new FileExtensionProviderImpl();

    @Override
    public FileProvider selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.getExtensionFilters().addAll(getFiltersFromExtensions());
        File file = fileChooser.showOpenDialog(stage);
        sendErrorIfFileIsNotSelected(file);
        return createFileProviderFromFile(file);
    }

    private void sendErrorIfFileIsNotSelected(File file) {
        if(file == null) {
            throw new BasicAppError("File selection was aborted");
        }
    }

    private FileProvider createFileProviderFromFile(File file) {
        FileExtension extension = fileExtensionProvider.fromFileName(file.getName());
        try {
            FileInfo fileInfo = new FileInfo(file.getCanonicalPath(), file.getName(), extension);
            return new FileProviderImpl(fileInfo);
        } catch (IOException err) {
            throw new BasicAppError(err.getMessage());
        }
    }

    private Collection<FileChooser.ExtensionFilter> getFiltersFromExtensions() {
        return FileExtension.getAllowedExtensions()
                .stream()
                .map(e -> new FileChooser.ExtensionFilter(StringUtils.capitalize(e), "*." + e))
                .collect(Collectors.toList());
    }

}
