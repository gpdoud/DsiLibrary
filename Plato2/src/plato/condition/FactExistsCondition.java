package plato.condition;

import plato.core.Fact;
import plato.core.Facts;

public class FactExistsCondition extends Condition
{
	Fact fact;
	public Fact getFact() { return this.fact; }
	public void setFact(Fact fact) { this.fact = fact; }
	
	Facts facts;
	public Facts getFacts() { return this.facts; }
	public void setFacts(Facts facts) { this.facts = facts; }
	
	public Boolean evaluate()
	{
		if(this.getFacts().contains(this.getFact()))
			return true;
		else
			return false;
	}
	
	public FactExistsCondition(String name, Fact fact, Facts facts)
	{
		this(name);
		if(fact != null)
			this.setFact(fact);
		if(facts != null)
			this.setFacts(facts);
	}
	
	public FactExistsCondition(String name)
	{
		super(name);
	}
	
	public FactExistsCondition() 
	{
		super();
	}
}
