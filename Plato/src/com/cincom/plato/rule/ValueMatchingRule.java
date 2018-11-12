package com.cincom.plato.rule;

import com.cincom.plato.Data;
import com.cincom.plato.rule.IBaseRule;
import java.util.ArrayList;

public class ValueMatchingRule implements IBaseRule
{
	public class DataRelationship
	{
		private Data data1;
		public Data getData1() { return data1; }
		public void setData1(Data data1) { this.data1 = data1; }
		
		private Data data2;
		public Data getData2() { return data2; }
		public void setData2(Data data2) { this.data2 = data2; }
		
		public DataRelationship(Data data1, Data data2)
		{
			this.data1 = data1;
			this.data2 = data2;
		}
	}

	ArrayList<DataRelationship> relatedDataTable = new ArrayList<DataRelationship>();

	/**
	 * After the user selects a value, this method marks the data items 
	 * that become invalid because of the selection
	 * @param data - a Data instance
	 */
	public void selected(Data data)
	{
		for(DataRelationship dr : relatedDataTable)
		{
			dr.getData1().setSelected(false);
			dr.getData1().setConstrained(true);
			dr.getData2().setSelected(false);
			dr.getData2().setConstrained(true);
			
			if(dr.getData1().equals(data))
			{
				dr.getData1().setSelected(true);
				dr.getData2().setConstrained(false);
			}
			if(dr.getData2().equals(data))
			{
				dr.getData2().setSelected(true);
				dr.getData1().setConstrained(false);
			}
		}
	}
	
	/**
	 * Tests whether the two data instances have been related by a rule
	 * @param data1 - A Data instance
	 * @param data2 - A Data instance
	 * @return True if the two data instances are related; otherwise false.
	 */
	public Boolean isRelated(Data data1, Data data2)
	{
		for(DataRelationship dr : relatedDataTable)
		{
			if(dr.getData1() == data1 && dr.getData2() == data2)
				return true;
			if(dr.getData1() == data2 && dr.getData2() == data1)
				return true;
		}
		return false;
	}
	
	/**
	 * Relates the two data instances
	 * @param data1 - A Data instance
	 * @param data2 - A Data instance
	 */
	public void relate(Data data1, Data data2)
	{
		DataRelationship dr = new DataRelationship(data1, data2);
		relatedDataTable.add(dr);
	}

	public ValueMatchingRule()
	{
		super();
	}
}
