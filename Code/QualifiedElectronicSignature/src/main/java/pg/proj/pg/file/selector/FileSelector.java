package pg.proj.pg.file.selector;

import pg.proj.pg.file.provider.FileProvider;

import java.io.IOException;

public interface FileSelector {
    FileProvider selectFile();
}
