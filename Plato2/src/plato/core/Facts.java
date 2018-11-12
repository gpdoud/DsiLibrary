package plato.core;
import java.util.ArrayList;


public class Facts extends ArrayList<Fact>
{
	private static final long serialVersionUID = -6209653957809523694L;

	public void dump()
	{
		for(Fact fact : this)
			Engine.msg(fact.toString());
	}
}
