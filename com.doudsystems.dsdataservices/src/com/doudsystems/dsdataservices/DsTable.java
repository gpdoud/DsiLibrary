// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsdataservices;

import com.doudsystems.dsdatasources.*;

public class DsTable extends DsBase
{
	private IDsDataSource dsDataSource;
	protected IDsDataSource getDataSource() { return dsDataSource; }
	protected void setDataSource(IDsDataSource dataSource) { dsDataSource = dataSource; }

	public DsTableRow createInstance()
	{
		return new DsTableRow();
	}
	
	protected DsTableRows select(String criteria, String sort)
	{
		return new DsTableRows();
	}
	
	protected void open()
	{
		dsDataSource.connect();
	}
	
	public DsTable()
	{
		super();
	}

}

