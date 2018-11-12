package plato.condition;

import plato.core.EngineObject;

public class Condition extends EngineObject
{
	public Boolean evaluate()
	{
		return true;
	}
	
	public Condition(String name)
	{
		super(name);
	}
	
	public Condition() {}

	public String toString()
	{
		return "CONDITION: id(" + this.getId() + "), name(" + this.getName() + ")";
	}
}
