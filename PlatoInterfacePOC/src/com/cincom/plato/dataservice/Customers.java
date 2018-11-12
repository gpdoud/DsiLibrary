package com.cincom.plato.dataservice;

import java.util.*;

public class Customers extends ArrayList
{
	IDataService ds = null;

	public Customer[] getAll()
	{
		Object[][] rows = ds.getAllData();
		Customer[] customers = new Customer[rows.length];
		for(int idx = 0; idx < rows.length; idx++)
		{
			int nbr = Integer.parseInt(String.valueOf(rows[idx][0]));
			String name = String.valueOf(rows[idx][1]);
			int credit = Integer.parseInt(String.valueOf(rows[idx][2]));
			Customer customer = new Customer(nbr, name, credit);
			customers[idx] = customer;
		}
		return customers;
	}
	
	public Customers(IDataService ds)
	{
		this.ds = ds;
	}
}
