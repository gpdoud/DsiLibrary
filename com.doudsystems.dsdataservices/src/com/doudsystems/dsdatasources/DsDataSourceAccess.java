// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dsdatasources;

import com.doudsystems.dsdataservices.*;
import java.sql.*;

public class DsDataSourceAccess extends DsDataSourceJdbc
{

	protected String dbFilename = null;
	
	public void initialization()
	{
		super.initialization();
	}
	
	public void connect()
	{
		driver = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
        driver += dbFilename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end
        super.connect();
	}
	
	public DsDataSourceAccess()
	{
		this(null, null);
	}
	
	public DsDataSourceAccess(String dbFilename)
	{
		this(dbFilename, null);
	}
	
	public DsDataSourceAccess(String dbFilename, String dbTablename)
	{
		super();
		this.dbFilename = dbFilename;
		this.dbTablename = dbTablename;
	}
	
	public String getDbFilename() { return dbFilename; }
	public void setDbFilename(String dbFilename) { this.dbFilename = dbFilename; }
	public String getDbTablename() { return dbTablename; }
	public void setDbTablename(String dbTablename) { this.dbTablename = dbTablename; }
	
}
