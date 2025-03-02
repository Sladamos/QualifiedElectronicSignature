package pg.proj.pg.signature.info;

import pg.proj.pg.cipher.extractor.CipherExtractor;
import pg.proj.pg.error.BasicAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.iv.InitializationVector;
import pg.proj.pg.key.generator.PrivateKeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.signature.type.SignatureType;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public record EncryptedSignatureExecutionerInfo (SignatureExecutionerInfo signatureExecutionerInfo,
                                                 InitializationVector iv) {

    public static EncryptedSignatureExecutionerInfo createFromBinaryFile(FileSelector fileSelector,
                                                                         FileContentOperator fileContentOperator,
                                                                         CipherExtractor cipherExtractor,
                                                                         PrivateKeyGen rsaKeyGen,
                                                                         SignatureType signatureType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            byte[] keyBytes = fileContentOperator.loadByteFileContent(fileInfo);
            InitializationVector iv = cipherExtractor.extractIvFromArray(keyBytes);
            byte[] keyContent = cipherExtractor.extractKeyContentFromArray(keyBytes);
            KeyInfo keyInfo = new KeyInfo(keyContent);
            Signature signature = Signature.getInstance(signatureType.getSignatureType());
            SignatureExecutionerInfo signatureExecutionerInfo = new SignatureExecutionerInfo(signature, rsaKeyGen, keyInfo, signatureType);
            return new EncryptedSignatureExecutionerInfo(signatureExecutionerInfo, iv);
        } catch (NoSuchAlgorithmException e) {
            throw new BasicAppError("Unable to get all necessary signer information");
        }
    }
}
