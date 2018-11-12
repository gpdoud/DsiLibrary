package plato.action;
import plato.core.Engine;
import plato.core.EngineObject;


public class Action extends EngineObject
{
	public void execute()
	{
		Engine.msg("Fire Action[" + this.getName() + "].execute()");
	}
	
	public Action(String name)
	{
		super(name);
	}
	
	public Action()
	{
		super();
	}
}
