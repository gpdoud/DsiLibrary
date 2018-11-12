package plato.core;
import plato.action.Action;
import plato.action.Actions;
import plato.condition.Conditions;


public class Rule extends EngineObject
{
	Boolean hasFired;
	public Boolean getHasFired() { return this.hasFired; }
	public void setHasFired(Boolean hasFired) { this.hasFired = hasFired; }
	
	public Conditions requiredConditions = new Conditions();
	public Actions actions = new Actions();
	
	public void Fire()
	{
		if(getHasFired())
			return;
		
		for(Action action : actions)
		{
			action.execute();
			this.setHasFired(true);
		}
	}
	
	public Rule()
	{
		super();
	}
	
	public Rule(String name)
	{
		super(name);
		this.setHasFired(false);
	}

	public String toString()
	{
		return "RULE: id(" + this.getId() + "), name(" + this.getName() + ")";
	}
}
