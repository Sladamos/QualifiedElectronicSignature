package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import java.io.IOException;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private FileSelector fileSelector;

    @Override
    public void onEncryptCalled() {
        try {
            FileProvider sourceFileProvider = fileSelector.selectFile();
        } catch (IOException | NullPointerException e) {
            onErrorOccurred();
        }
    }

    @Override
    public void onDecryptCalled() {
        try {
            FileProvider sourceFileProvider = fileSelector.selectFile();
            System.out.println(sourceFileProvider.getFileInfo().canonicalPath());
        } catch (IOException | NullPointerException e) {
            onErrorOccurred();
        }
    }

    private void onErrorOccurred() {
        System.out.println("Error occurred not implemented");
    }
}
