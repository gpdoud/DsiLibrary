package com.cincom.plato;

import java.util.ArrayList;

public class Rows extends ArrayList<Row>
{
	private Fields fields;
	
	public Row createInstance()
	{
		Row row = new Row(fields);
		this.add(row);
		return row;
	}
	
	public Rows(Fields fields)
	{
		this.fields = fields;
	}

	private static final long serialVersionUID = 8901606960475882751L;
}
