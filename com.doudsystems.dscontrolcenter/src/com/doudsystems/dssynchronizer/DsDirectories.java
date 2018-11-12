// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

import java.util.ArrayList;
import java.util.Collection;

public class DsDirectories extends ArrayList<DsDirectory>
{

	public DsDirectories()
	{
	}

	public DsDirectories(int initialCapacity)
	{
		super(initialCapacity);
	}

	public DsDirectories(Collection<? extends DsDirectory> c)
	{
		super(c);
	}

}
