package plato.action;
import plato.core.Engine;
import plato.core.Fact;


public class RetractFactAction extends Action
{

	Fact fact;
	public Fact getFact() { return this.fact; }
	public void setFact(Fact fact) { this.fact = fact; }
	
	public void execute()
	{
		super.execute();
		Engine.msg("RetractFactAction[" + this.getName() + "].execute()");
		if(this.getEngine() == null)
			return;
		this.getEngine().retractFact(this.getFact());
	}
	
	public RetractFactAction(String name)
	{
		super(name);
	}
	
	public RetractFactAction()
	{
		super();
	}
}
