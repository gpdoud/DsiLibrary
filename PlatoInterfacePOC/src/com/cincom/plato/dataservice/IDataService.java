package com.cincom.plato.dataservice;

public interface IDataService
{
	Boolean connect(String server, String database, String user, String password);
	Object[][] getAllData();
	void debug();
}
