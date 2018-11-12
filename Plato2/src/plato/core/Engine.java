package plato.core;

import plato.condition.Condition;

public class Engine
{
	Boolean runEngine = false;
	Facts facts = new Facts();
	Rules rules = new Rules();
	
	public void defineRule(Rule rule)
	{
		Engine.msg("defineRule: Rule[" + rule.getName() + "]");
		this.rules.add(rule);
		resetFiredRules();
	}
	
	public void assertFact(Fact fact)
	{
		Engine.msg("assertFact: Fact[" + fact.getName() + "]");
		this.facts.add(fact);
		resetFiredRules();
	}
	
	public void retractFact(Fact fact)
	{
		Engine.msg("retractFact: Fact[" + fact.getName() + "]");
		this.facts.remove(fact);
		resetFiredRules();
	}
	
	void resetFiredRules()
	{
		for(Rule rule : rules)
		{
			rule.setHasFired(false);
		}
		runEngine = true;
	}
	
	void processRules()
	{
		runEngine = false;
		
		for(Rule rule : rules)
		{
			Boolean fireRule = true;
			// check all the conditions in the rule
			for(Condition condition : rule.requiredConditions)
			{
				// if any condition fails, can't fire the rule so just return
				if(!condition.evaluate())
				{
					// evaluate() returned false; don't fire this rule
					fireRule = false;
					break;
				}
			}
			// if we get here, we might fire the rule
			if(fireRule)
				rule.Fire();
		}
	}
	
	public void Run(Object message)
	{
		msg("Start of engine execution ...");
		
		runEngine = true;
		
		if(message != null)
			msg(message);
		
		while(runEngine)
			processRules();
		
		//this.facts.dump();
		//this.rules.dump();
		
		msg("End of engine execution ...");
	}
	
	public void Run()
	{
		Run(null);
	}
	
	public static void msg(Object message)
	{
		String msgStr = "PLATO: " + message.toString();
		System.out.println(msgStr);
	}
}
