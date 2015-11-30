package com.iisc.year2015;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Element;
import org.omg.CORBA.StringHolder;
import org.omg.CORBA.portable.Streamable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by Snap on 23.11.2015.
 */
public class Header {
    String c;
    String h;
    public Header(String cert, String hash){
        c = cert;
        h = hash;
    }
    public void generateHeader(Element root) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, SignatureException, NoSuchProviderException, InvalidKeyException, IOException {
        Element header =  root.addElement("soap:Header");
        Element security = header.addElement("wsse:Security");
        Element binarySecurityToken =  security.addElement("wsse:BinarySecurityToken").
                addAttribute("Encoding", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary").
                addAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3").
                addAttribute("wsu:Id", "CertId-1").
                addAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd").
                addText(c);
        Element signature = security.addElement("ds:Signature");
        signature.addAttribute("Id", "Signature-1");
        Element signedInfo = signature.addElement("ds:SignedInfo");
        Element canonicalizationMethod = signedInfo.addElement("ds:CanonicalizationMethod").
                addAttribute("Algorhitm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        Element signatureMethod = signedInfo.addElement("ds:SignatureMethod").
                addAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        Element reference = signature.addElement("ds:Reference").
                addAttribute("URI", "#Body");
        Element transforms = reference.addElement("ds:Transforms");
        Element transform = transforms.addElement("ds:Transform").
                addAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        Element digestMethod = reference.addElement("ds:DigestMethod").
                addAttribute("Algorithm", "http://www.w3.org/2001/04/xmldsig-more#gostr3411");
        Element digestValue = reference.addElement("ds:DigestValue")
                .addText(h);
        Element signatureValue = signature.addElement("ds:SignatureValue")
                .addText(getSign(signedInfo));
        Element keyInfo = signedInfo.addElement("ds:KeyInfo")
                .addAttribute("Id", "KeyId-1");
        Element securityTokenReference =  keyInfo.addElement("wsse:SecurityTokenReference")
                .addAttribute("wsu:Id", "STRId-1")
                .addAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        Element wReference = securityTokenReference.addElement("wsse:Reference")
                .addAttribute("URI", "#CertId-1")
                .addAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");

    }

    private static String getSign(Element signedInfo) throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException, InvalidKeyException, SignatureException {
        KeyStore keyStore = KeyStore.getInstance("ViPNetContainer", "ViPNet");

        keyStore.load(null, null);
        InputStream inputStream = new FileInputStream("D:\\Univer\\RTele\\lesson_9_crypto\\token");
        keyStore.load(inputStream, null);

        String alias = "key";
        char[] password = "1234567890".toCharArray();
        PrivateKey key = (PrivateKey)keyStore.getKey(alias, password);

        Signature signatureDriver = Signature.getInstance(
                "GOST3411-94withGOST3410-2001", // название алгоритма
                "ViPNet"                        // название провайдера
        );

        signatureDriver.initSign(key);

        byte[] nextDataChunk = signedInfo.toString().getBytes();
        signatureDriver.update(nextDataChunk);

        byte[] signatureValue = signatureDriver.sign();

        return Base64.encodeBase64URLSafeString(signatureValue.toString().getBytes());
    }
}
