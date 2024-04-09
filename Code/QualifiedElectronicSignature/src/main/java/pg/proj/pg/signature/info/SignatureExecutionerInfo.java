package pg.proj.pg.signature.info;

import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.key.generator.PrivateKeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.signature.type.SignatureType;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public record SignatureExecutionerInfo(Signature signature, PrivateKeyGen keyGen,
                                       KeyInfo keyInfo, SignatureType signatureType) {
    public static SignatureExecutionerInfo createFromBinaryFile(FileSelector fileSelector,
                                                                FileContentOperator fileContentOperator,
                                                                PrivateKeyGen rsaKeyGen,
                                                                SignatureType signatureType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            byte[] keyBytes = fileContentOperator.loadByteFileContent(fileInfo);
            KeyInfo keyInfo = new KeyInfo(keyBytes);
            Signature signature = Signature.getInstance(signatureType.getSignatureType());
            return new SignatureExecutionerInfo(signature, rsaKeyGen, keyInfo, signatureType);
        } catch (NoSuchAlgorithmException e) {
            throw new BasicAppError("Unable to get all necessary signer information");
        }
    }
}
