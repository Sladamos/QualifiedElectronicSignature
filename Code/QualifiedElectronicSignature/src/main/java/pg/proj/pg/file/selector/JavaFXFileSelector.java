package pg.proj.pg.file.selector;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.error.CriticalAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JavaFXFileSelector implements FileSelector {

    private final Stage stage;

    private final String title;

    private final Set<FileExtension> allowedExtensions;

    private final Supplier<FileChooser> fileChooserSupplier;

    @Override
    public FileProvider selectFile() {
        sendErrorIfNoExtensionsAreAllowed();
        sendErrorIfStageIsNotSpecified();
        FileChooser fileChooser = fileChooserSupplier.get();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(getFiltersFromExtensions());
        File file = fileChooser.showOpenDialog(stage);
        sendErrorIfFileIsNotSelected(file);
        return FileProviderImpl.createFromFile(file);
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

    private Collection<FileChooser.ExtensionFilter> getFiltersFromExtensions() {
        return allowedExtensions
                .stream()
                .map(FileExtension::strValue)
                .map(e -> new FileChooser.ExtensionFilter(StringUtils.capitalize(e), "*." + e))
                .collect(Collectors.toList());
    }

}
