package com.iisc.year2015;

import org.dom4j.Element;
import org.omg.CORBA.StringHolder;
import org.omg.CORBA.portable.Streamable;

/**
 * Created by Snap on 23.11.2015.
 */
public class Header {
    String c;
    String h;
    String s;
    public Header(String cert, String hash, String sign){
        c = cert;
        h = hash;
        s = sign;
    }
    public void generateHeader(Element root){
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
                .addText(s);
        Element keyInfo = signedInfo.addElement("ds:KeyInfo")
                .addAttribute("Id", "KeyId-1");
        Element securityTokenReference =  keyInfo.addElement("wsse:SecurityTokenReference")
                .addAttribute("wsu:Id", "STRId-1")
                .addAttribute("xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        Element wReference = securityTokenReference.addElement("wsse:Reference")
                .addAttribute("URI", "#CertId-1")
                .addAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");

    }
}
