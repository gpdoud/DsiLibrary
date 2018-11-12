package plato.action;
import plato.core.Engine;
import plato.core.Fact;


public class AssertFactAction extends Action
{
	Fact fact;
	public Fact getFact() { return this.fact; }
	public void setFact(Fact fact) { this.fact = fact; }
	
	public void execute()
	{
		super.execute();
		Engine.msg("AssertFactAction[" + this.getName() + "].execute()");
		if(this.getEngine() == null)
			return;
		this.getEngine().assertFact(this.getFact());
	}
	
	public AssertFactAction(String name)
	{
		super(name);
	}
	
	public AssertFactAction()
	{
		super();
	}
}
