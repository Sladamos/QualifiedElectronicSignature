package pg.proj.pg.file.selector;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.extension.FileExtensionProvider;
import pg.proj.pg.file.extension.FileExtensionProviderImpl;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JavaFXFileSelector implements FileSelector {

    private final Stage stage;

    private final Set<FileExtension> allowedExtensions;

    private final FileExtensionProvider fileExtensionProvider = new FileExtensionProviderImpl();

    @Override
    public FileProvider selectFile() {
        sendErrorIfNoExtensionsAreAllowed();
        sendErrorIfStageIsNotSpecified();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().addAll(getFiltersFromExtensions());
        //TODO send communicate
        File file = fileChooser.showOpenDialog(stage);
        sendErrorIfFileIsNotSelected(file);
        FileProvider provider = createFileProviderFromFile(file);
        //TODO send communicate
        return provider;
    }

    private void sendErrorIfStageIsNotSpecified() {
        if(stage == null) {
            throw new CriticalAppError("Unable to display file selector because stage is not set");
        }
    }

    private void sendErrorIfNoExtensionsAreAllowed() {
        if(allowedExtensions.isEmpty()) {
            throw new CriticalAppError("Unable to pick any file because extensions aren't specified");
        }
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
        return allowedExtensions
                .stream()
                .map(FileExtension::strValue)
                .map(e -> new FileChooser.ExtensionFilter(StringUtils.capitalize(e), "*." + e))
                .collect(Collectors.toList());
    }

}
