package com.coders_kitchen.pherousa.atom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Service("atomFeedEntryBuilder")
@PropertySource({"classpath:application.properties"})
public class AtomFeedEntryBuilder {

    public static final String PROTOCOL = "http";
    public static final String HOST = "localhost";
    public static final String ENTRY = "entry";
    public static final String XMLNS = "xmlns";
    public static final String ATOM = "http://www.w3.org/2005/Atom";
    public static final String LINK = "link";
    public static final String HREF = "href";
    public static final String REL = "rel";
    public static final String RELATED = "related";
    public static final String RESOURCE_LINK = "%s://%s:%s/%s/%s";

    @Value("${server.port}")
    private String port;

    @Value("${resource}")
    private String resource;

    public String buildAtomFeedEntry(String id) {
        String atomFeedEntry = "";
        try {
            Document document = buildAtomFeedEntryDocument(id);
            atomFeedEntry = transformDocumentToString(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return atomFeedEntry;
    }

    private Document buildAtomFeedEntryDocument(String id) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = docFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element entry = document.createElement(ENTRY);
        Attr xmlns = document.createAttribute(XMLNS);
        xmlns.setValue(ATOM);
        entry.setAttributeNode(xmlns);
        document.appendChild(entry);

        Element link = document.createElement(LINK);
        Attr href = document.createAttribute(HREF);
        href.setValue(String.format(RESOURCE_LINK, PROTOCOL, HOST, port, resource, id));
        Attr rel = document.createAttribute(REL);
        rel.setValue(RELATED);
        link.setAttributeNode(href);
        link.setAttributeNode(rel);
        entry.appendChild(link);

        return document;
    }

    private String transformDocumentToString(Document document) throws TransformerException {
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.transform(new DOMSource(document), result);
        return stringWriter.getBuffer().toString();
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
