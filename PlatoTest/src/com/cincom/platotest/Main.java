package com.cincom.platotest;

import com.cincom.plato.*;
import com.cincom.plato.rule.*;
import com.cincom.plato.dataservice.*;

public class Main
{
	void TestInterfaceArchitecture()
	{
		int ans = 0;
		try
		{
			System.out.format("Enter 1 for SQL or 2 for XML: ");
			ans = System.in.read();
		} 
		catch (java.io.IOException e)
		{
			System.err.println("Answer must be either 1 or 2.");
			System.exit(-1);
		}
		IDataService dataService = null;
		switch(ans)
		{
			case 49: // ASCII value for 1
				dataService = new SqlDataService();
				break;
			case 50: // ASCII value for 2
				dataService = new XmlDataService();
				break;
		}
		dataService.connect("a", "b", "c", "d");
		Customers customers = new Customers(dataService);
		
		Customer[] customerList = customers.getAll();
		for(Customer customer : customerList)
		{
			System.out.format("Customer: nbr(%d), name(%s), credit(%d)\n", 
					customer.getNbr(), customer.getName(), customer.getCredit());
		}
	}
	
	void run1()
	{
		log("Start testing rules ...");
		com.cincom.plato.rule.ValueMatchingRule vmr = new com.cincom.plato.rule.ValueMatchingRule();
		Data dDoud = new Data("Last", "Doud");
		Data dGreg = new Data("First", "Greg");
		Data dCindy = new Data("Name", "Cindy");
		Data dGeorge = new Data("Name", "George");
		vmr.relate(dDoud, dGreg);
		vmr.relate(dDoud, dCindy);
		vmr.selected(dDoud);
		log(String.format("%s and %s are related? %b", 
				dDoud.getValue(), dGreg.getValue(), vmr.isRelated(dDoud, dGreg)));
		log(String.format("%s and %s are related? %b", 
				dDoud.getValue(), dCindy.getValue(), vmr.isRelated(dDoud, dCindy)));
		log(String.format("%s and %s are related? %b", 
				dDoud.getValue(), dGeorge.getValue(), vmr.isRelated(dDoud, dGeorge)));
		log("End testing rules ...");
	}
	
	void run()
	{
		log("Start of Main ...");
		Entity e = new Entity("Employee", "Employees for the company");
		e.createField("Id");
		e.createField("Name");
		e.createField("Age");
		e.createField("Salary");
		e.createField("Birthday");
		
		Row r1 = e.createRow();
		r1.set("Id", "01892");
		r1.set("Name", "Greg Doud");
		r1.set("Age", "49");
		r1.set("Salary", "20000");
		r1.set("Birthday", "8/27/1957");

		Row r2 = e.createRow();
		r2.set("Id", "12345");
		r2.set("Name", "Cindy Doud");
		r2.set("Age", "52");
		r2.set("Salary", "30000");
		r2.set("Birthday", "7/6/1954");
		
		Row r3 = e.createRow();
		r3.set("Id", "54321");
		r3.set("Name", "Nick Doud");
		r3.set("Age", "21");
		r3.set("Salary", "40000");
		r3.set("Birthday", "10/28/1985");
		
		Row r4 = e.createRow();
		r4.set("Id", "98765");
		r4.set("Name", "Ken Doud");
		r4.set("Age", "16");
		r4.set("Salary", "50000");
		r4.set("Birthday", "5/11/1991");
		
		e.dump();

		Entity j = new Entity("Job", "Available Jobs");
		j.createField("JobId");
		j.createField("Description");
		j.createField("Pay");
		
		Row j1 = j.createRow();
		j1.set("JobId", "001");
		j1.set("Description", "Manager");
		j1.set("Pay", "35000");
		
		Row j2 = j.createRow();
		j2.set("JobId", "002");
		j2.set("Description", "Supervisor");
		j2.set("Pay", "30000");
		
		Row j3 = j.createRow();
		j3.set("JobId", "003");
		j3.set("Description", "Maintenance");
		j3.set("Pay", "240000");
		
		j.dump();
		
		IBaseRule r = new ValueMatchingRule();
		Relation relation = Relations.createRelation(e, j, r);
		
		Data dId = r1.getData("Id");
		Data dJobId = j1.getData("JobId");
		IBaseRule rule = relation.getRule();
		
		rule.relate(dId, dJobId);
		
		relation.getRule().relate(r2.getData("Id"), j1.getData("JobId"));
		
		log("Greg and Manager are related. " + rule.isRelated(dId, dJobId));
		log("Cindy and Manager are related. " + rule.isRelated(r2.getData("Id"), j1.getData("JobId")));
		log("Nick and Manager are related. " + rule.isRelated(r3.getData("Id"), j1.getData("JobId")));
				
		log("End of Main ...");
	}
	
	public static void main(String[] args)
	{
		Main pgm = new Main();
		//pgm.run();
		pgm.TestInterfaceArchitecture();
	}

	private static void log(Object message)
	{
		System.out.format("%s\n", message.toString());
	}
}
