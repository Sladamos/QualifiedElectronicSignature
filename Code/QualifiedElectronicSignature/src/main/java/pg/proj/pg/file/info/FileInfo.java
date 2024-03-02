package pg.proj.pg.file.info;

import pg.proj.pg.file.extension.FileExtension;

public record FileInfo(String canonicalPath, String fileName, FileExtension extension) {

}
