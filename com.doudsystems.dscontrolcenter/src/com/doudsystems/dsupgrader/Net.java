// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsupgrader;
/**
 * Copyright (c) 2006 by Doud Systems, Inc.
 * All Rights Reserved
 */

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.*;

public class Net
{
	public String connect(String protocol, String domain, int port, String fullFilePath, String proxyServer, int proxyPort)
	{
		byte[] buffer = new byte[1024];
		StringBuilder strBuilder = new StringBuilder();
		try
		{
			//Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, proxyPort));
			//URL url = new URL("http", "www.cincom.com", 80, "/us/eng/solutions/marketing/healthcare/intelligent-guided-patient-registration/index.jsp");
			//URL url = new URL("http", "www.doudsystems.com", 80, "/index.aspx");
			//URL url = new URL("http", "sharepoint.cincom.com", 80, "/Cincom/CIBA/IGRPublishProtected/IGR/QMPublish.log");
			//URL url = new URL("ftp", "ftp.cincom.com", 21, "/incoming/TextXml.xml");
			URL url = new URL(protocol, domain, port, fullFilePath);
			URLConnection connection = url.openConnection();
			InputStream stream = connection.getInputStream();
			BufferedInputStream input = new BufferedInputStream(stream);
			int cnt = 0;
			while((cnt = input.read(buffer, 0, buffer.length)) != -1)
			{
				String str = new String(buffer, 0, cnt);
				strBuilder.append(str);
			}
		}
		catch(Exception e) 
		{ e.printStackTrace(); }
		
		return strBuilder.toString();
	}

}
