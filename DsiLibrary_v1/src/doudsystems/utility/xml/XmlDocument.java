/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package doudsystems.utility.xml;

import java.io.FileWriter;
import java.io.StringWriter;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/**
 * Helper class to make working with XML similar to the .Net XmlDocument class
 * @version 1.0
 * @author Greg Doud
 */
public class XmlDocument {

    /**
     * The version number of the library. (i.e. 1.0)
     */
    public static String Version = "1.0";
    /**
     * The official date the library was updated (i.e. Apr-17-2010)
     */
    public static String Updated = "Apr-17-2010";

    /**
     * adds an element to an existing element
     * @param parent the parent element of the new element
     * @param tag the tag of the new elememtn
     * @param value the text of the new element or null
     * @return returns the newly created element
     */
    public static Element addElement(Element parent, String tag, String value) {
        Element element = parent.getOwnerDocument().createElement(tag);
        parent.appendChild(element);
        element.setTextContent(value);
        return element;
    }

    /**
     * selects an element within an existing element
     * @param element the element to search the children for
     * @param tag the tag of the element to search for
     * @return the first element found
     */
    public static Element selectSingleElement(Element element, String tag) {
        NodeList elements = element.getElementsByTagName(tag);
        if(elements.getLength() > 0) {
            return (Element) elements.item(0);
        }
        return null;
    }

    /**
     * saves the XML document to a file
     * @param doc the Document to save
     * @param filename the full path of the file
     */
    public static void saveDocument(Document doc, String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            StreamResult result = new StreamResult(fw);
            DOMSource source = new DOMSource(doc);
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            trans.transform(source, result);
        } catch (TransformerConfigurationException tce) {
            System.out.println("Exception: " + tce.getMessage());
        } catch (TransformerException te) {
            System.out.println("Exception: " + te.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    /**
     * opens an existing XML file
     * @param filename the full path to the file
     * @return a Document object or null if it cannot be opened
     */
    public static Document openDocument(String filename) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filename);
        } catch (Exception e) {
            return null;
        }
        return doc;
    }

    /**
     * creates a new Document object
     * @return a Document object or null if it cannot be created
     */
    public static Document newDocument() {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (Exception e) {
            return null;
        }
        return doc;
    }

    /**
     * returns a string representation of the Document
     * @param doc the Document object
     * @return a string
     */
    public static String ToString(Document doc) {
        Transformer trans = null;
        String str = null;
        try {
            trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");
            trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource dom = new DOMSource(doc);
            trans.transform(dom, result);
            str = sw.toString();
        } catch(Exception e) {
            return null;
        }
        return str;
    }

}
