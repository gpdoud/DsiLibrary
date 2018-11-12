package plato.core;
import java.util.ArrayList;


public class Rules extends ArrayList<Rule>
{
	private static final long serialVersionUID = 6537196751015634121L;

	public void dump()
	{
		for(Rule rule : this)
			Engine.msg(rule.toString());
	}
}
