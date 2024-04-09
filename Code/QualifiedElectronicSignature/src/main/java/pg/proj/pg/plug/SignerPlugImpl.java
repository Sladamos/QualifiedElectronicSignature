package pg.proj.pg.plug;

import lombok.AllArgsConstructor;
import pg.proj.pg.communicate.definition.Communicate;
import pg.proj.pg.communicate.receiver.api.CommunicateReceiver;
import pg.proj.pg.document.info.provider.DocumentInfoProvider;
import pg.proj.pg.event.entity.api.OneArgEvent;
import pg.proj.pg.event.entity.impl.OneArgEventImpl;
import pg.proj.pg.file.cryptography.container.FileSignerInformationContainer;
import pg.proj.pg.file.cryptography.container.FileSignerInformationContainerImpl;
import pg.proj.pg.file.cryptography.signer.FileSigner;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.provider.FileProvider;
import pg.proj.pg.file.provider.FileProviderImpl;
import pg.proj.pg.file.selector.FileSelector;
import pg.proj.pg.signature.provider.SignatureExecutionerProvider;
import pg.proj.pg.signature.selector.SignatureExecutionerSelector;

import java.util.function.Supplier;

@AllArgsConstructor
public class SignerPlugImpl implements SignerPlug {

    private final FileSelector signFileSelector;

    private final FileSelector verifyFileSelector;

    private final FileSelector signatureFileSelector;

    private final SignatureExecutionerSelector signatureExecutionerSelector;

    private final FileSigner signer;

    private final Supplier<DocumentInfoProvider> signerDocumentInfoProviderSupplier;

    private final OneArgEvent<Communicate> communicateEvent = new OneArgEventImpl<>();

    @Override
    public void onSignCalled() {
        sendCommunicate("Select file to sign");
        FileProvider sourceFileProvider = signFileSelector.selectFile();
        sendCommunicate("Select signature");
        SignatureExecutionerProvider signatureExecutionerProvider = signatureExecutionerSelector.selectExecutioner();
        FileProvider destinationFileProvider = FileProviderImpl.fromSource(sourceFileProvider,
                FileExtension.XML, "xades");
        DocumentInfoProvider documentInfoProvider = signerDocumentInfoProviderSupplier.get();
        FileSignerInformationContainer signerInfo = new FileSignerInformationContainerImpl(documentInfoProvider,
                sourceFileProvider, destinationFileProvider, signatureExecutionerProvider);
        sendCommunicate("Signing file");
        signer.signFile(signerInfo);
        sendCommunicate("File signed properly");
    }

    @Override
    public void onVerifyCalled() {
        sendCommunicate("Select file to verify");
        FileProvider sourceFileProvider = verifyFileSelector.selectFile();
        sendCommunicate("Select file with signature");
        FileProvider signatureFileProvider = signatureFileSelector.selectFile();
        sendCommunicate("Select verifier");
        //create signature info (XadesSignatureXmlParser: SignatureInfoProvider)
        //select verifier provider - SignatureVerifierSelector
        //FileVerifierInformationContainer - signatureInfo + sourceInfo + verifierProvider
        //verify file - FileVerifier: boolean
        sendCommunicate("Verifying signature");
        //  verify : 1. is it hash of same document 2. are attributes correct
        //send communicate if is Signed or not
    }

    @Override
    public void registerCommunicatesReceiver(CommunicateReceiver communicateReceiver) {
        communicateEvent.addListener(communicateReceiver::onCommunicateOccurred);
    }

    private void sendCommunicate(String content) {
        Communicate communicate = new Communicate(content);
        communicateEvent.invoke(communicate);
    }
}
