package com.cofii2.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author C0FII
 */
public class ResourceXML {

    public static final int UPDATE_XML = 0;
    public static final int READ_XML = 1;
    //private int xmlAction = UPDATE_XML;

    // ------------------------------------------
    private void transform(Document doc, String resource) {
        FileWriter writer = null;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            Source source = new DOMSource(doc);

            //String path = ResourceXML.class.getResource(resource).getPath();
            //path = path.replace("/target/classes", "/src/main/resources");
            
            File file = new File(this.getClass().getResource(resource).getFile());
            System.out.println("to OutputStream file: " + file.getAbsolutePath());
            OutputStream os = new FileOutputStream(file);
            //File file = new File(path);
            //writer = new FileWriter(file);
            //PrintWriter printWriter = new PrintWriter(writer);
            Result result = new StreamResult(os);

            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ----------------------------------------------------------
    public ResourceXML(String resource, int xmlAction, DocumentAction dAction) {
        try {

            InputStream is = this.getClass().getResourceAsStream(resource);
            //File file = new File(is);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            dbFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(is);
            doc = dAction.action(doc);
            if (xmlAction == UPDATE_XML) {
                transform(doc, resource);
            }
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------
}
