package pg.proj.pg.file.operator;

import pg.proj.pg.file.info.FileInfo;

public interface FileContentOperator {

    String loadStrFileContent(FileInfo sourceFile);

    void saveByteFileContent(FileInfo destinationFile, byte[] encryptedContent);
}
