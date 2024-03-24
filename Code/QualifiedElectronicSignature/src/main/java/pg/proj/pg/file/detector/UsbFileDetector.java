package pg.proj.pg.file.detector;

import lombok.AllArgsConstructor;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UsbFileDetector implements FileDetector {

    private final USBDeviceDetectorManager usbDeviceDetectorManager = new USBDeviceDetectorManager();

    private final String fileName;

    private final FileExtension fileExtension;

    @Override
    public FileProvider detectFile() throws FileNotFoundException {
        List<USBStorageDevice> devices = usbDeviceDetectorManager.getRemovableDevices();
        File file = devices.stream()
                .map(USBStorageDevice::getRootDirectory)
                .map(root -> root  + fileName + "." + fileExtension.strValue())
                .map(File::new)
                .filter(File::exists)
                .findFirst().orElseThrow(() -> new FileNotFoundException("File not found"));
        return FileProviderImpl.createFromFile(file);
    }

}
