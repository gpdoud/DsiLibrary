// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsdatasources;

public interface IDsDataSource
{
	
	public void initialization();
	public void connect();
	public boolean getFirst();
	public boolean getNext();
	public boolean eof();
}
