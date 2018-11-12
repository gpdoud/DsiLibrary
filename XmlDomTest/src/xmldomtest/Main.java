/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xmldomtest;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Owner
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    static Document doc = null;

    public static void main(String[] args) {
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement("RootNode");
            doc.appendChild(root);
            root.setAttribute("name", "greg");

            Transformer trans = TransformerFactory.newInstance().newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource dom = new DOMSource(doc);
            trans.transform(dom, result);
            String str = sw.toString();
            System.out.println(str);

        } catch (Exception e) {
            System.out.println("Exception thrown message: " + e.getMessage());
        }
    }
}
