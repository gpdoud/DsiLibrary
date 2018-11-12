package com.cincom.plato;

public class Data
{
	private Boolean constrained;
	public Boolean getConstrained() {return constrained;}
	public void setConstrained(Boolean constrained) {this.constrained = constrained;}
	
	private String name;
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private Boolean selected;
	public Boolean getSelected() {return selected;}
	public void setSelected(Boolean selected) {this.selected = selected;}
	
	private Object value;
	public Object getValue() {return value;}
	public void setValue(Object value) {this.value = value;}
	
	/**
	 * Checks that the name and value have the same data
	 * @param data A Data instance
	 * @return True of the values are the same; otherwise false
	 */
	public Boolean equals(Data data)
	{
		return this.getName().equals(data.getName()) && this.getValue().equals(data.getValue());
	}
	
	public Data(String name, Object value)
	{
		this.setName(name);
		this.setValue(value);
		this.setSelected(false);
		this.setConstrained(false);
	}
}
