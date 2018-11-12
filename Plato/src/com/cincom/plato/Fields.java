package com.cincom.plato;

import java.util.ArrayList;

public class Fields extends ArrayList<Field>
{
	public Field createInstance()
	{
		Field field = new Field();
		this.add(field);
		return field;
	}
	
	public Fields()
	{
	}

	private static final long serialVersionUID = -6392717420163352088L;
}
