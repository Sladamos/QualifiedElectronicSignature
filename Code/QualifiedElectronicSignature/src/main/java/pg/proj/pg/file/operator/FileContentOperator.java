package pg.proj.pg.file.operator;

import pg.proj.pg.file.info.FileInfo;

public interface FileContentOperator {

    String loadStrFileContent(FileInfo sourceFile);

    byte[] loadByteFileContent(FileInfo sourceFile);

    void saveByteFileContent(FileInfo destinationFile, byte[] fileContent);

    void saveStrFileContent(FileInfo destinationFile, String fileContent);
}
