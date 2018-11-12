// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

import java.io.File;
import java.net.URI;

public class DsDirectory extends File
{

	public DsDirectories getDirectories()
	{
		File fs[] = listFiles();
		DsDirectories dsDirectories = new DsDirectories();
		for(int idx = 0; idx < fs.length; idx++)
		{
			File f = fs[idx];

			if(!f.isDirectory())
				continue;

			String path = f.getAbsolutePath();
			dsDirectories.add(new DsDirectory(path));
		}
		return dsDirectories;
	}
	
	public DsFiles getFiles()
	{
		File fs[] = listFiles();
		DsFiles dsFiles = new DsFiles();
		for(int idx = 0; idx < fs.length; idx++)
		{
			File f = fs[idx];

			if(!f.isFile())
				continue;

			String path = f.getAbsolutePath();
			dsFiles.add(new DsFile(path));
		}
		return dsFiles;
	}


	public DsDirectory(String pathname)
	{
		super(pathname);
	}

	public DsDirectory(URI uri)
	{
		super(uri);
	}

	public DsDirectory(String parent, String child)
	{
		super(parent, child);
	}

	public DsDirectory(File parent, String child)
	{
		super(parent, child);
	}

}
