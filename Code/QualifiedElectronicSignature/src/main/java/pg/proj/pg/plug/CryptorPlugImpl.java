package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private FileSelector fileSelector;

    @Override
    public void onEncryptCalled() {
        FileProvider sourceFileProvider = fileSelector.selectFile();
        System.out.println(sourceFileProvider.getFileInfo().canonicalPath());
    }

    @Override
    public void onDecryptCalled() {
        FileProvider sourceFileProvider = fileSelector.selectFile();
        System.out.println(sourceFileProvider.getFileInfo().canonicalPath());
    }
}
