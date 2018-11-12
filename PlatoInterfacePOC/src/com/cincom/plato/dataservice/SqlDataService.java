package com.cincom.plato.dataservice;

public class SqlDataService implements IDataService
{

	public Boolean connect(String server, String database, String user, String password)
	{
		System.out.format("Need to connect for SQL databases...\n");
		return null;
	}
	
	public Object[][] getAllData()
	{
		return new Object[0][0];
	}
	
	public void debug()
	{
		System.out.format("Using SqlDataService\n");
	}

}
