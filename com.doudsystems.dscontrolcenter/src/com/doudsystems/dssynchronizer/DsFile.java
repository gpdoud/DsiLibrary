package com.doudsystems.dssynchronizer;

import java.io.File;
import java.net.URI;

@SuppressWarnings("serial")
public class DsFile extends File
{
	/**
	 * Determines whether the DsFile represents a text file
	 * @return true if the file is text; otherwise false;
	 */
	public boolean isText()
	{
		String textExts = "asp|aspx|cs|csv|html|htm|ini|java|txt|xml";
		String fileName = this.getName();
		int pos = fileName.lastIndexOf(".");
		if(pos == -1)
			return false;
		String ext = fileName.substring(pos + 1);
		if(textExts.contains(ext))
			return true;
		return false;
	}
	
	public DsFile(String arg0)
	{
		super(arg0);
	}

	public DsFile(URI arg0)
	{
		super(arg0);
	}

	public DsFile(String arg0, String arg1)
	{
		super(arg0, arg1);
	}

	public DsFile(File arg0, String arg1)
	{
		super(arg0, arg1);
	}

}
