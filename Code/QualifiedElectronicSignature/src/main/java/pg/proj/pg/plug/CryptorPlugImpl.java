package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.cipher.container.CipherContainer;
import pg.proj.pg.cipher.provider.CipherProvider;
import pg.proj.pg.cipher.selector.CipherSelector;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;

import java.util.Arrays;

@AllArgsConstructor
public class CryptorPlugImpl implements CryptorPlug {

    private FileSelector fileSelector;

    private CipherSelector encryptCipherSelector;

    private CipherSelector decryptCipherSelector;

    @Override
    public void onEncryptCalled() {
        //FileProvider sourceFileProvider = fileSelector.selectFile(); //TODO add
        CipherProvider encryptCipherProvider = encryptCipherSelector.selectCipher();
        CipherContainer container = encryptCipherProvider.getCipher();
        byte[] byteArray = new byte[]{1, 2};
        var test = container.encrypt(byteArray);
        System.out.println(Arrays.toString(test));
    }

    @Override
    public void onDecryptCalled() {
        //FileProvider sourceFileProvider = fileSelector.selectFile(); TODO add
        CipherProvider decryptCipherProvider = decryptCipherSelector.selectCipher();
        CipherContainer container = decryptCipherProvider.getCipher();
        byte[] byteArray = new byte[]{94, 1, 71, -78, 15, 97, 25, -41, 85, -128,
                43, 32, 87, -15, 73, -128, 62, 99, 80, 20, 86, 74, -15, 59, -6,
                -111, -16, 115, 64, 98, -128, -29, 50, -96, 41, -94, 1, 20, -101,
                -12, 5, -58, 118, -77, -115, -24, 20, -97, 74, -27, 48, 18, 54,
                -37, -13, -103, 78, 101, -53, 85, 77, 15, 54, -98, 37, 84, 50, -63,
                -62, 75, -126, -27, -91, 27, -128, -89, 8, -121, -112, -53, 99,
                -79, -100, 119, -74, -51, -12, 61, -118, -49, -53, 121, -21, -26, -37,
                72, 126, -16, -74, -80, 34, -100, -60, 121, -89, 7, 37, 64, -89, 4,
                108, 76, -33, -15, 20, -37, 33, -50, -4, -37, 3, 119, -37, 89, -83,
                31, 86, -79, 12, 12, 115, 19, 55, -63, -102, -61, -124, -18, 65, 20, -12,
                -96, 24, 33, 94, -69, 98, -21, 8, 66, 15, 119, 14, 34, 125, -56, 68, 73,
                104, 115, -58, 70, -95, -11, -88, -41, -23, 24, 99, 45, 25, 110, -78, 110,
                0, 102, -119, 84, -23, -77, 66, 117, 30, 102, -79, -106, -92, 26, -16, -93,
                -96, 111, 80, 8, -26, 84, -43, 69, 30, 23, 64, 15, 24, -122, -44, -125, -18,
                111, -103, -67, 90, -77, -20, 25, 3, 97, 64, -117, 98, 119, -99, -100, 72,
                -118, 98, -13, 42, 104, 1, 53, 3, 44, 74, -122, -110, 57, 93, 107, -128, 79,
                -112, -61, 68, -80, 114, -69, 4, 32, 70, -16, -27, 112, -38, 114, -70, -7,
                1, 41, 92, 86, -116, 103, 52, 88, 71, -124, -36, 31, 25, 17, -109, 3, -117,
                119, 112, 4, -108, 107, -79, 22, 28, -77, -56, -44, -2, 62, -52, -61, 6, 92,
                67, -30, -12, 104, 72, 15, -39, -119, -19, -75, -9, 24, -8, -89, -119, -45,
                11, 89, 14, -68, 13, -64, -18, 63, 123, 60, -64, -40, 91, 67, -20, 42, -47,
                42, -105, -118, -119, -91, -4, -97, -31, -19, -81, 86, -32, 46, 75, -52, -109,
                -18, 82, 69, 29, 76, -84, 17, -29, -20, -60, -34, 86, 84, -5, 52, 33, -105, 74, 62, 97,
                103, 34, -48, -90, -30, 64, 121, 37, -124, 0, -80, -96, 117, 124, 44, -31, 76, 12, -64, 62,
                89, -108, -127, -86, 78, 97, -124, -13, -77, -2, 40, 92, 16, 76, -4, 85, -95, -93, 8, -105, 12,
                102, 48, 20, 12, 4, 111, 106, 41, -120, -50, -25, 6, 107, -59, -95, -65, 41, -14, -25, -98, 36,
                88, 62, 72, -13, 98, 29, -18, -59, -72, 100, -6, -62, 103, 27, -103, -115, -91, 91, 98, 34, 126,
                27, -120, -6, -34, 120, 67, -119, 81, -107, -19, 104, -51, -124, -118, -30, -9, 31, -84, -56,
                -73, -116, 19, -3, 98, -70, 100, 55, -90, 98, -92, 126, 88, -67, -57, 71, 120, 116, -1, 1, 7, -79, 101,
                -36, 97, 47, -34, -127, 106, 3,
                -99, 53, 84, 81, 87, -34, 72, 52, 4, 114, -88, 86, -84, -28, 70, 49, 28, 112, 63, -55, -12
};
        var test = container.decrypt(byteArray);
        System.out.println(Arrays.toString(test));
    }
}
