package pg.proj.pg.xml.parser;

import org.xml.sax.InputSource;
import pg.proj.pg.author.info.AuthorInfo;
import pg.proj.pg.document.details.DocumentDetails;
import pg.proj.pg.document.info.DocumentInfo;
import pg.proj.pg.error.definition.BasicAppError;
import pg.proj.pg.file.extension.FileExtension;
import pg.proj.pg.file.info.FileInfo;
import pg.proj.pg.signature.info.SignatureInfo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringReader;
import java.time.Instant;
import java.util.Base64;


public class XadesSignatureXmlParser implements SignatureXmlParser {
    @Override
    public SignatureInfo fromXml(String xml) {
        try {
            Document document = parseXmlDocument(xml);
            Element root = document.getDocumentElement();
            DocumentDetails details = parseDetails(root);
            AuthorInfo authorInfo = parseAuthorInfo(root);
            Instant signatureDate = parseSignatureDate(root);
            byte[] signature = parseSignature(root);
            DocumentInfo documentInfo = new DocumentInfo(details, authorInfo, signatureDate);
            return new SignatureInfo(documentInfo, signature);
        } catch (Exception e) {
            throw new BasicAppError("Unable to parse XML");
        }
    }

    private Document parseXmlDocument(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    private DocumentDetails parseDetails(Element root) {
        Element detailsElement = (Element) root.getElementsByTagName("details").item(0);
        String lastModificationDate = getElementTextContent(detailsElement, "lastModificationDate");
        String creationDate = getElementTextContent(detailsElement, "creationDate");
        String fileName = getElementTextContent(detailsElement, "fileName");
        String fileExtensionStr = getElementTextContent(detailsElement, "fileExtension");
        FileExtension fileExtension = FileExtension.fromString(fileExtensionStr);
        long size = Long.parseLong(getElementTextContent(detailsElement, "length"));
        return new DocumentDetails(size, Instant.parse(creationDate),
                Instant.parse(lastModificationDate), fileName, fileExtension);
    }

    private AuthorInfo parseAuthorInfo(Element root) {
        Element contentElement = (Element) root.getElementsByTagName("content").item(0);
        String author = getElementTextContent(contentElement, "author");
        return new AuthorInfo(author);
    }

    private byte[] parseSignature(Element root) {
        Element contentElement = (Element) root.getElementsByTagName("content").item(0);
        String signatureBase64 = getElementTextContent(contentElement, "signature");
        return Base64.getDecoder().decode(signatureBase64);
    }

    private Instant parseSignatureDate(Element root) {
        Element contentElement = (Element) root.getElementsByTagName("content").item(0);
        String date = getElementTextContent(contentElement, "signatureDate");
        return Instant.parse(date);
    }

    private String getElementTextContent(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
