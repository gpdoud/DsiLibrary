// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsdatasources;

import com.doudsystems.dsdataservices.*;

public class DsDataSourceJdbc extends DsBase implements IDsDataSource
{

	protected String dbTablename = null;
	protected String driver = null;
	protected java.sql.Connection connection = null;
	protected java.sql.Statement sql = null;
	protected java.sql.ResultSet rs = null;
	
	protected DsDataSourceJdbc()
	{
		super();
		initialization();
	}
	
	public void initialization()
	{
		try 
		{ 
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); 
		} catch (ClassNotFoundException e) { e.printStackTrace(); }		
	}
	
	public void connect()
	{
		try
		{
			connection = java.sql.DriverManager.getConnection(driver, "", "");
		} catch (java.sql.SQLException e) { e.printStackTrace(); }

	}
	
	public boolean getFirst()
	{
		try
		{
			sql = connection.createStatement();
		} catch (java.sql.SQLException e) { e.printStackTrace(); }
		
		try
		{
			rs = sql.executeQuery("Select * from " + dbTablename);
		} catch (java.sql.SQLException e) { e.printStackTrace(); }
		
		return false;
	}
	
	public boolean getNext()
	{
		return false;
	}
	
	public boolean eof()
	{
		return false;
	}
}
