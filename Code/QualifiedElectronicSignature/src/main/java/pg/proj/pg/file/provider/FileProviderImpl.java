package pg.proj.pg.file.provider;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.info.FileInfo;

@AllArgsConstructor
public class FileProviderImpl implements FileProvider {

    private final FileInfo template;

    public FileInfo getFileInfo() {
        return new FileInfo(template.canonicalPath(), template.fileName(), template.extension());
    }
}
