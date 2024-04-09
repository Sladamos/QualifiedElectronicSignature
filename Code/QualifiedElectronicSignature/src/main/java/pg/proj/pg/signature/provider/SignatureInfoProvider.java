package pg.proj.pg.signature.provider;

import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.signature.info.SignatureInfo;

public interface SignatureInfoProvider {
    SignatureInfo getSignatureInfo(FileInfo fileInfo);
}
