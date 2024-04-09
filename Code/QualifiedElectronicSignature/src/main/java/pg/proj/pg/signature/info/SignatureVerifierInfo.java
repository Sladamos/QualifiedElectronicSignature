package pg.proj.pg.signature.info;

import pg.proj.pg.cipher.info.CipherInfo;
import pg.proj.pg.cipher.type.CipherType;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.file.operator.FileContentOperator;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.key.generator.KeyGen;
import pg.proj.pg.key.generator.PrivateKeyGen;
import pg.proj.pg.key.generator.PublicKeyGen;
import pg.proj.pg.key.info.KeyInfo;
import pg.proj.pg.signature.type.SignatureType;

import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Base64;

public record SignatureVerifierInfo(Signature signature, PublicKeyGen keyGen,
                                    KeyInfo keyInfo, SignatureType signatureType) {

    public static SignatureVerifierInfo createFromPEMFile(FileSelector fileSelector,
                                               FileContentOperator fileContentOperator,
                                               PublicKeyGen rsaKeyGen, SignatureType signatureType) {
        try {
            FileProvider provider = fileSelector.selectFile();
            FileInfo fileInfo = provider.getFileInfo();
            byte[] keyBytes = fileContentOperator.loadByteFileContent(fileInfo);
            byte[] decodedBytes = Base64.getDecoder().decode(keyBytes);
            KeyInfo keyInfo = new KeyInfo(decodedBytes);
            Signature signature = Signature.getInstance(signatureType.getSignatureType());
            return new SignatureVerifierInfo(signature, rsaKeyGen, keyInfo, signatureType);

        } catch (IllegalArgumentException | NoSuchAlgorithmException e) {
            throw new BasicAppError("Unable to get all necessary verifier information");
        }
    }
}
