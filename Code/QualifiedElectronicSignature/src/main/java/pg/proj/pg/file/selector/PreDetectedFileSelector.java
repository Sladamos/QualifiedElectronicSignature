package pg.proj.pg.file.selector;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.detector.FileDetector;
import pg.proj.pg.file.provider.FileProvider;

import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
public class PreDetectedFileSelector implements FileSelector {

    private final FileSelector fileSelector;

    private final List<FileDetector> fileDetectors;

    @Override
    public FileProvider selectFile() {
        for(FileDetector fileDetector : fileDetectors) {
            try {
                return fileDetector.detectFile();
            } catch (FileNotFoundException ignored) {
            }
        }
        return fileSelector.selectFile();
    }

}
