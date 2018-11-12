// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.test;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

import com.doudsystems.dsdataservices.*;

public class Main
{

	void run()
	{
		Customer cust = new Customer();
		cust.select(null, null);
	}
	
	public static void main(String[] args)
	{
		Main pgm = new Main();
		pgm.run();
		//pgm.testJDBC();
	}

	void testJDBC()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
            String filename = "E:/Java/JavaTest.mdb";
            database+= filename.trim() + ";DriverID=22;READONLY=true}"; // add on to the end 			
            java.sql.Connection conn = java.sql.DriverManager.getConnection(database, "", "");
            java.sql.Statement sql = conn.createStatement();
            java.sql.ResultSet rs = sql.executeQuery("Select * from Customer");
            while(rs.next())
            {
            	int id = rs.getInt("Id");
            	String name = rs.getString("Name");
            	boolean male = rs.getBoolean("Male");
            	int age = rs.getInt("Age");
            	Date birthday = rs.getDate("Birthday");
            	DateFormat df = DateFormat.getDateInstance();
            	System.out.format("Id[%d] Name[%s] Male[%b] Age[%d] Birthday[%s]\n", 
            		id, name, male, age, df.format(birthday));
            }
            
		} 
		catch (java.sql.SQLException e) { e.printStackTrace(); }
		catch (java.lang.ClassNotFoundException e) { e.printStackTrace(); }
	}
}
