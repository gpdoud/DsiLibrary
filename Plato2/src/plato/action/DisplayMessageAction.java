package plato.action;
import plato.core.Engine;


public class DisplayMessageAction extends Action
{
	String message;
	public String getMessage() { return this.message; }
	public void setMessage(String message) { this.message = message; }
	
	public void execute()
	{
		super.execute();
		Engine.msg("DisplayMessageAction[" + this.getName() + "].execute()");
		Engine.msg(this.getMessage());
	}
	
	public DisplayMessageAction(String name, String message)
	{
		super(name);
		this.setMessage(message);
	}
	
	public DisplayMessageAction(String name)
	{
		super(name);
	}
	
	public DisplayMessageAction()
	{
		super();
	}
}
