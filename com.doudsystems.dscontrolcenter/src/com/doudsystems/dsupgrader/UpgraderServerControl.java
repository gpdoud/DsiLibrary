// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsupgrader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.doudsystems.dsutility.XmlDocument;

public class UpgraderServerControl
{
	Document document = null;
	XmlDocument xmldoc = null;
	Element root = null;
	
	public boolean hasUpgrade(String application, int curMajor, int curMinor, int curRevision)
	{
		Element eApplications = getApplicationsNode(root);
		Element eApplication = getApplicationNode(eApplications, application);
		Element eUpdates = getUpdatesNode(eApplication);
		Element eUpdate = getUpdateNode(eUpdates, curMajor, curMinor, curRevision);
		Element eFiles = getFilesNode(eUpdate);
		Element[] eFileList = getFileNode(eFiles);
		
		for(Element e : eFileList)
		{
			System.out.println("File : " + e.getAttribute("name"));
		}
		
		return true;
	}
	
	/**
	 * Returns the root node as an Element
	 * @param root
	 * @return
	 */
	private Element getApplicationsNode(Element root)
	{
		return getElement(root, "Applications");
	}

	/**
	 * Returns an Element for the applicationName or null
	 * @param applicationsNode
	 * @param applicationName
	 * @return
	 */
	private Element getApplicationNode(Element applicationsNode, String applicationName)
	{
		NodeList applicationList = applicationsNode.getElementsByTagName("Application");
		Element applicationNode = null;
		for(int idx = 0; idx < applicationList.getLength(); idx++)
		{
			applicationNode = (Element) applicationList.item(idx);
			String name = applicationNode.getAttribute("name");
			if(name.equals(applicationName))
				return applicationNode;
		}
		return null;
	}
	
	private Element getUpdatesNode(Element root)
	{
		return getElement(root, "Updates");
	}
	
	private Element getUpdateNode(Element updatesNode, int curMajor, int curMinor, int curRevision)
	{
		NodeList updateList = updatesNode.getElementsByTagName("Update");
		Element updateNode = null;
		for(int idx = 0; idx < updateList.getLength(); idx++)
		{
			updateNode = (Element) updateList.item(idx);
			String ver = updateNode.getAttribute("ver");
			int pos = ver.indexOf('.', 0);
			int major = Integer.parseInt(ver.substring(0, pos));
			int pos2 = ver.indexOf('.', pos + 1);
			int minor = Integer.valueOf(ver.substring(pos + 1, pos2));
			int revision = Integer.valueOf(ver.substring(pos2 + 1));
			if(major > curMajor)
				return updateNode;
			else if (minor > curMinor)
				return updateNode;
			else if (revision > curRevision)
				return updateNode;
		}
		return null;
	}
	
	
	
	private Element getFilesNode(Element root)
	{
		return getElement(root, "Files");
	}
	
	private Element[] getFileNode(Element filesNode)
	{
		NodeList fileList = filesNode.getElementsByTagName("File");
		Element[] fileNodeList = new Element[fileList.getLength()];
		for(int idx = 0; idx < fileList.getLength(); idx++)
		{
			fileNodeList[idx] = (Element) fileList.item(idx);
		}
		return fileNodeList;
	}
	
	private String getAttributeValue(Element root, String name)
	{
		return root.getAttribute(name);
	}
	
	private Element getElement(Element root, String tagName)
	{
		return (Element) root.getElementsByTagName(tagName).item(0);
	}
	
	private void init()
	{
		xmldoc = new XmlDocument();
		xmldoc.open("Http://www.doudsystems.com/Upgrader/Upgrader.xml");
		root = xmldoc.getDocumentElement();
		document = xmldoc.getDocument();
	}
	public UpgraderServerControl()
	{
		init();
	}

}
