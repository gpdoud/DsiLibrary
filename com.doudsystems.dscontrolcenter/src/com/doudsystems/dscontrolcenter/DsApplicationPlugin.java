// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dscontrolcenter;

public class DsApplicationPlugin
{
	private String name = null; 
	private String className = null;
	private boolean active = false;		// turns plugin off
	
	public DsApplicationPlugin()
	{
	}
	
	public DsApplicationPlugin(String name, String className)
	{
		this(name, className, true);
	}

	public DsApplicationPlugin(String name, String className, boolean active)
	{
		setName(name);
		setClassName(className);
		setActive(active);
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
