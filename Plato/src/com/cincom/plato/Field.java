package com.cincom.plato;

public class Field
{
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	private String type;
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public Field(String name, String type)
	{
		this.setName(name);
		this.setType(type);
	}
	
	public Field() {}
}
