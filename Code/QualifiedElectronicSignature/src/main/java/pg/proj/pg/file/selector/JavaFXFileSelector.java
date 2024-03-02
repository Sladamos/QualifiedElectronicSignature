package pg.proj.pg.file.selector;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    public FileProvider selectFile() throws IOException { //TODO switch to error
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        fileChooser.getExtensionFilters().addAll(getFiltersFromExtensions());
        File file = fileChooser.showOpenDialog(stage);
        throwExceptionIfFileIsNotSelected(file);
        return createFileProviderFromFile(file);
    }

    private void throwExceptionIfFileIsNotSelected(File file) {
        if(file == null) {
            throw new NullPointerException("File selection was aborted");
        }
    }

    private FileProvider createFileProviderFromFile(File file) throws IOException {
        FileExtension extension = fileExtensionProvider.fromFileName(file.getName());
        FileInfo fileInfo = new FileInfo(file.getCanonicalPath(), file.getName(), extension);
        return new FileProviderImpl(fileInfo);
    }

    private Collection<FileChooser.ExtensionFilter> getFiltersFromExtensions() {
        return FileExtension.getAllowedExtensions()
                .stream()
                .map(e -> new FileChooser.ExtensionFilter(StringUtils.capitalize(e), "*." + e))
                .collect(Collectors.toList());
    }

}
