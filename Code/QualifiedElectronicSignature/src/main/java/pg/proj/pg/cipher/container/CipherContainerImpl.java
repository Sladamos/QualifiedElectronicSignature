package pg.proj.pg.cipher.container;

public class CipherContainerImpl implements CipherContainer {
    @Override
    public byte[] encrypt(byte[] source) {
        return source;
    }

    @Override
    public byte[] decrypt(byte[] source) {
        return source;
    }
}
