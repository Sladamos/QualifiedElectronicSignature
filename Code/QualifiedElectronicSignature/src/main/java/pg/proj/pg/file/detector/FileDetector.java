package pg.proj.pg.file.detector;

import pg.proj.pg.file.provider.FileProvider;

import java.io.FileNotFoundException;

public interface FileDetector {
    FileProvider detectFile() throws FileNotFoundException;
}
