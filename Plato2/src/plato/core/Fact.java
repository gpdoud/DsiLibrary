package plato.core;

public class Fact extends EngineObject
{
	public Fact(String name)
	{
		super(name);
	}
	
	public String toString()
	{
		return "FACT: id(" + this.getId() + "), name(" + this.getName() + ")";
	}
}
