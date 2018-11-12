// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter;
import org.w3c.dom.*;
import com.doudsystems.dsutility.XmlDocument;

public class DsControlCenterConfiguration
{
	private static final String DsControlCenterConfigurationFileName = 
		"DsControlCenterConfiguration.xml";	
	private static final String PluginTag = "Plugin";
	private XmlDocument doc = null;
	private Element root = null;
	private DsApplicationPlugins plugins = null;
	
	public DsApplicationPlugins getPlugins()
	{
		return plugins;
	}
	
	private void loadApplicationPlugins()
	{
		plugins = new DsApplicationPlugins();
		NodeList list = root.getElementsByTagName(PluginTag);
		for(int idx = 0; idx < list.getLength(); idx++)
		{
			Element element = (Element) list.item(idx);
			DsApplicationPlugin plugin = new DsApplicationPlugin();
			plugin.setName(element.getAttribute("name"));
			plugin.setClassName(element.getAttribute("class"));
			plugin.setActive(true);
			plugins.add(plugin);
		}
	}
	
	private void Init()
	{
		doc = new XmlDocument();
		doc.open(DsControlCenterConfigurationFileName);
		root = doc.getDocumentElement();
		
		loadApplicationPlugins();
	}
	
	public DsControlCenterConfiguration() 
	{
		Init();
	}
}
