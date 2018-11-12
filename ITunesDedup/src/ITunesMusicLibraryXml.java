import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.io.File;
import java.io.IOException;

public class ITunesMusicLibraryXml
{
	public void Run()
	{
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = null;
			docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse (new File("c:\\users\\gdoud\\music\\itunes\\itunes music library - copy.xml"));
		
			// normalize text representation
			doc.getDocumentElement ().normalize ();
			
			System.out.printf("The root is %s\n", doc.getDocumentElement().getNodeName());
		} 	catch (ParserConfigurationException pce) {}
		  	catch (SAXException se) {}
		  	catch (IOException ioe) { System.out.printf("IOException: message is %s\n", ioe.getMessage()); }
		  	catch (Exception e) {}
	}
	
	public ITunesMusicLibraryXml() {}
}
