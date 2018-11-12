// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.test;

import com.doudsystems.dsdataservices.*;
import com.doudsystems.dsdatasources.*;

public class Customer extends DsTable
{

	protected DsTableRows select(String criteria, String sort)
	{
		return new DsTableRows();
	}
	
	public Customer()
	{
		super();
		setDataSource(new DsDataSourceAccess("E:/Java/JavaTest.mdb", "Customer"));
		open();
	}

}
