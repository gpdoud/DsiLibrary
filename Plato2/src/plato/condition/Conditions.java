package plato.condition;

import java.util.ArrayList;

import plato.core.Engine;

public class Conditions extends ArrayList<Condition>
{
	private static final long serialVersionUID = 3716801438622155017L;

	public void dump()
	{
		for(Condition condition : this)
			Engine.msg(condition.toString());
	}

}
