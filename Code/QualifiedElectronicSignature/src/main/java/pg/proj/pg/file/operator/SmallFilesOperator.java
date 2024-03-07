package pg.proj.pg.file.operator;

import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmallFilesOperator implements FileOperator {
    @Override
    public String loadFileContent(FileInfo fileInfo) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileInfo.canonicalPath()))).replace("\r", "");
        } catch (IOException e) {
            throw new CriticalAppError("Unable to read file.");
        }
    }
}
