package pg.proj.pg.file.detector;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import java.io.File;
import java.io.FileNotFoundException;

@AllArgsConstructor
public class DesktopFileDetector implements FileDetector {

    private final String desktopPath = System.getProperty("user.home") + File.separator +"Desktop";

    private final String fileName;

    private final FileExtension fileExtension;

    @Override
    public FileProvider detectFile() throws FileNotFoundException {
        String filePath = desktopPath + File.separator + fileName + "." + fileExtension.strValue();
        File file = new File(filePath);
        if(!file.exists()) {
            throw new FileNotFoundException("File not found");
        }

        return FileProviderImpl.createFromFile(file);
    }

}
