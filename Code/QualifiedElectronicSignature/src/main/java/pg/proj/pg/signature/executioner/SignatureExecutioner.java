package pg.proj.pg.signature.executioner;

public interface SignatureExecutioner {
    byte[] sign(byte[] source);
}
