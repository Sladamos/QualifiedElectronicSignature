package pg.proj.pg.file.selector;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.detector.FileDetector;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.provider.FileProvider;

import java.io.FileNotFoundException;

@AllArgsConstructor
public class PreDetectedFileSelector implements FileSelector {

    private final FileSelector fileSelector;

    private final FileDetector fileDetector;

    @Override
    public FileProvider selectFile() {
        try {
            return fileDetector.detectFile();
        } catch (FileNotFoundException e) {
            return fileSelector.selectFile();
        }
    }

}
