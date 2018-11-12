package com.cincom.plato.dataservice;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XmlDataService implements IDataService
{

	Object[][] rows = null;

	private void CreateDocumentBuilderFactory()
	{
		Document document = null;
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File("C:\\Users\\gdoud\\Java\\PlatoInterfacePOC\\src\\com\\cincom\\plato\\dataservice\\Customers.xml"));
			NodeList customersNodes = document.getElementsByTagName("customers");
			Element customersNode = (Element) customersNodes.item(0);
			NodeList customerNodes = customersNode.getElementsByTagName("customer");
			rows = new Object[customerNodes.getLength()][3];
			for(int idx = 0; idx < customerNodes.getLength(); idx++)
			{
				Element customer = (Element) customerNodes.item(idx);
				int nbr = Integer.parseInt(customer.getAttribute("nbr"));
				String name = customer.getAttribute("name");
				int credit = Integer.parseInt(customer.getAttribute("credit"));
				rows[idx][0] = nbr;
				rows[idx][1] = name;
				rows[idx][2] = credit;
			}
		}
		catch (SAXException e) {}
		catch (IOException e) {}
		catch (ParserConfigurationException e) {}
	}
	
	public Boolean connect(String server, String database, String user, String password)
	{
		CreateDocumentBuilderFactory();
		return null;
	}
	
	public Object[][] getAllData()
	{
		return rows;
	}
	
	public void debug()
	{
		System.out.format("Using XmlDataService\n");
	}

}
