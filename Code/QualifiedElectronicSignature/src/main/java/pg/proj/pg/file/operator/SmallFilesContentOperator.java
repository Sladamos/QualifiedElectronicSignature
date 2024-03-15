package pg.proj.pg.file.operator;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.error.definition.CriticalAppError;
import pg.proj.pg.file.info.FileInfo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmallFilesContentOperator implements FileContentOperator {
    @Override
    public String loadStrFileContent(FileInfo sourceFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(sourceFile.canonicalPath()))).replace("\r", "");
        } catch (IOException e) {
            throw new CriticalAppError("Unable to read file");
        }
    }

    @Override
    public byte[] loadByteFileContent(FileInfo sourceFile) {
        try {

            return Files.readAllBytes(Paths.get(sourceFile.canonicalPath()));
        } catch (IOException e) {
            throw new CriticalAppError("Unable to read file");
        }
    }

    @Override
    public void saveByteFileContent(FileInfo destinationFile, byte[] encryptedContent) {
        String filePath = destinationFile.canonicalPath();
        try (FileOutputStream fos = new FileOutputStream(filePath, false)) {
            fos.write(encryptedContent);
        } catch (FileNotFoundException e) {
            throw new BasicAppError("Unable to create or find file");
        } catch (IOException e) {
            throw new BasicAppError("Unable to save content to file");
        }
    }
}
