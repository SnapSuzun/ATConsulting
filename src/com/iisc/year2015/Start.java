package com.iisc.year2015;

import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import ru.infotecs.common.util.Hex;
import ru.infotecs.crypto.ViPNetProvider;
import ru.infotecs.crypto.gost28147.Gost28147SecretKey;
import ru.infotecs.crypto.keys.ViPNetKeyProtectionParameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by Snap on 02.11.2015.
 */
public class Start {
    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, CertificateException, UnrecoverableEntryException, KeyStoreException, IOException, SignatureException, InvalidKeyException {


        Security.addProvider(new ViPNetProvider());

        Document xml = DocumentHelper.createDocument();
        Element root = xml.addElement("soap:Envelope").
                addNamespace("soap", "http://schemas.xmlsoap.org/soap/envelope/").
                addNamespace("actor", "http://smev.gosuslugi.ru/actors/smev").
                addNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd").
                addNamespace("ds", "http://www.w3.org/2000/09/xmldsig#")
                .addNamespace("atc", "http://at-sibir.ru/getDictionary")
                .addNamespace("smev", "http://smev.gosuslugi.ru/rev120315");

        X509Certificate cert = containerKeyStore();

        Element body = root.addElement("soap:Body")
                .addAttribute("wsu:Id", "body")
                .addAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");



        MessageDigest digestDriver = MessageDigest.getInstance("GOST3411-94","ViPNet");
        digestDriver.update(body.toString().getBytes());
        byte[] digestValue = digestDriver.digest();
        String hash = Base64.encodeBase64URLSafeString(digestValue);

        byte[] certBytes = cert.getEncoded();
        String certStr = Base64.encodeBase64URLSafeString(digestValue);

        Header head = new Header(certStr, hash);
        head.generateHeader(root);

        Element getDictionary = body.addElement("atc:getDictionary");
        Message message = new Message("test", "123456789");
        message.generateMessage(getDictionary);
        MessageData messageData = new MessageData("c580d006-f86f-4bdd-84be-b51de6f626c6");
        messageData.generateMessageData(getDictionary);
        System.out.println(root.asXML());


        /*Security.addProvider(new ViPNetProvider());
        MessageDigest digestDriver = MessageDigest.getInstance("GOST3411-94","ViPNet");
        digestDriver.update("".getBytes());
        byte[] digestValue = digestDriver.digest();
        //Hex.encode(digestValue, 1);
        //System.out.println(Hex.encode(digestValue, 1));

        containerKeyStore();*/

    }
    public static X509Certificate containerKeyStore() throws NoSuchProviderException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {
        KeyStore keyStore = KeyStore.getInstance("ViPNetContainer", "ViPNet");

        keyStore.load(null, null);
        InputStream inputStream = new FileInputStream("D:\\Univer\\RTele\\lesson_9_crypto\\token");
        keyStore.load(inputStream, null);

        String alias = "key";
        char[] password = "1234567890".toCharArray();
        Key key = keyStore.getKey(alias, password);

        X509Certificate cert = (X509Certificate)keyStore.getCertificate(alias);
        //System.out.println(key);
        return cert;
    }
    public void createHeader(Element root)
    {
        Element  header = root.addElement("soap:Header");
        Element security =  header.addElement("wsse:Security");
        security.addAttribute("soap:actor", "http://smev.gosuslugi.ru/actors/smev");
        Element securityToken = security.addElement("wsse:BinarySecurityToken");
        securityToken.addAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
        securityToken.addAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
        securityToken.addAttribute("wsu:Id", "CertId-1");
        securityToken.addText("CERT_HERE");
        Element signature = security.addElement("ds:Signature");
        signature.addAttribute("Id","Signature-1");
        Element sigInfo = signature.addElement("ds:SignedInfo");
        sigInfo.addElement("ds:CanonicalizationMethod").addAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        sigInfo.addElement("ds:SignatureMethod").addAttribute("Algorithm", "http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411");
        Element reference = sigInfo.addElement("ds:Reference");
        reference.addAttribute("URI", "#body");
        reference.addElement("ds:Transforms").addElement("ds:Transform").addAttribute("Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        reference.addElement("ds:DigestMethod").addAttribute("Algorithm", "http://www.w3.org/2001/04/xmldsig-more#gostr3411");
        reference.addElement("ds:DigestValue").addText("HASH_HERE");
    }

    public void addNamespaces(Element root)
    {
        root.addNamespace("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        root.addNamespace("ds", "http://www.w3.org/2000/09/xmldsig#");
        root.addNamespace("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        root.addNamespace("atc", "http://at-sibir.ru/getDictionary");
        root.addNamespace("smev", "http://smev.gosuslugi.ru/rev120315");
    }
}
