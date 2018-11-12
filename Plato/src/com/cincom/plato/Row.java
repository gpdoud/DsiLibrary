package com.cincom.plato;

/**
 * This class represents a row of data within an entity
 * @author gdoud
 *
 */
public class Row
{
	/**
	 * Copy of all the fields defined for the entity
	 */
	private Fields fields;
	/**
	 * Holds one Data instance for each field defined for
	 * the entity.
	 */
	private Data[] dataValues;
	
	/**
	 * Selects a value (like clicking a value in a list)
	 * @param name - the name of the field to select
	 */
	public void select(String name)
	{
		
	}

	/**
	 * Retrieves the data within the row for the selected field
	 * @param name - the name of the field which must have been
	 * previously defined for the entity
	 * @return a String representation of the value for the field
	 */
	public String get(String name)
	{
		return this.getData(name).getValue().toString();
	}
	
	/**
	 * Stores a value for the selected field within the row
	 * @param name - the name of the field which must have been
	 * previously defined for the entity
	 * @param value - the value to store
	 */
	public void set(String name, Object value)
	{
		this.getData(name).setValue(value);
	}
	
	/**
	 * Returns the Data item with the associated name
	 * @param name - the name of the data item
	 * @return a Data item
	 */
	public Data getData(String name)
	{
		return findData(name);
	}
	
	/**
	 * Sets the value of the Data item with the associated name
	 * @param name - the name of the data item
	 * @param value - the value to store
	 */
	private void setData(String name, Object value)
	{
		Data data = findData(name);
		if(data != null)
			data.setValue(value);
	}

	/**
	 * Returns a Field with the associated name
	 * @param name - the name of the field to return
	 * @return a Field object
	 */
	private Field getField(String name) 
	{ 
		return findField(name);
	}
	
	/**
	 * Sets the value of the Field object associated with name
	 * @param name - The name to find
	 * @param value - The value to set
	 */
	private void setField(String name, Object value) 
	{ 
		Field field = findField(name);
		if(field == null)
			return;
	}

	/**
	 * Finds the Data object with the associated name
	 * @param name - the name to find
	 * @return - a Data object
	 */
	private Data findData(String name)
	{
		for(Data data : dataValues)
		{
			if(data.getName().equals(name))
				return data;
		}
		return null;
	}
	
	/**
	 * Returns a Field object with the associated naem
	 * @param name - the name to find
	 * @return - a Field object
	 */
	private Field findField(String name)
	{
		for(Field field : fields)
		{
			if(field.getName().equals(name))
				return field;
		}
		return null; 
	}

	/**
	 * Constructor
	 * @param fields - the fields object from the entity
	 */
	public Row(Fields fields)
	{
		this.fields = fields;
		dataValues = new Data[fields.size()];
		for(int idx = 0; idx < fields.size(); idx++)
		{
			Field field = this.fields.get(idx);
			dataValues[idx] = new Data(field.getName(), "");
		}
	}
}
