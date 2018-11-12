// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.

package com.doudsystems.dsutility;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlDocument 
{
	private Document doc = null;
	private Element root = null;

	public Element getDocumentElement()
	{
		return root;
	}
	
	public Document getDocument()
	{
		return doc;
	}
	
	public void open(String xmlFileName)
	{
		try
		{
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFileName);
			root = (Element) doc.getDocumentElement();
		} 
		catch (ParserConfigurationException e) 
		{ e.printStackTrace(); }
		catch (IOException e) 
		{ e.printStackTrace(); }
		catch (SAXException e)
		{ e.printStackTrace(); }
	}
	
	public XmlDocument()
	{
	}
}
