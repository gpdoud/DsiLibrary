package plato.core;
import plato.action.AssertFactAction;
import plato.action.DisplayMessageAction;
import plato.condition.FactExistsCondition;


public class Program
{
	void Run()
	{
		Engine engine = new Engine();
		
		Fact fact1 = new Fact("Fact-1");
		Fact fact2 = new Fact("Fact-2");
		
		Rule rule1 = new Rule("Rule-1");
		FactExistsCondition condition1 = new FactExistsCondition("FactExistsCondition-1", fact1, engine.facts);
		rule1.requiredConditions.add(condition1);
		DisplayMessageAction action1 = new DisplayMessageAction("Action-1", "Test Action Message...");
		rule1.actions.add(action1);
		engine.defineRule(rule1);
		
		Rule rule2 = new Rule("Rule-2");
		FactExistsCondition condition2 = new FactExistsCondition("Condition-2", fact2, engine.facts);
		rule2.requiredConditions.add(condition2);
		AssertFactAction action2 = new AssertFactAction("Action-2");
		action2.setEngine(engine);
		rule2.actions.add(action2);
		Fact fact3 = new Fact("Fact-3");
		action2.setFact(fact3);
		engine.defineRule(rule2);
		
		Rule rule3 = new Rule("Rule-3");
		FactExistsCondition condition3 = new FactExistsCondition("Condition-3", fact3, engine.facts);
		rule3.requiredConditions.add(condition3);
		DisplayMessageAction action3 = new DisplayMessageAction("Action-3", "It worked!");
		action3.setEngine(engine);
		rule3.actions.add(action3);
		engine.defineRule(rule3);

		Rule rule4 = new Rule("Rule-4");
		FactExistsCondition condition4 = new FactExistsCondition("Condition-4", fact2, engine.facts);
		FactExistsCondition condition5 = new FactExistsCondition("Condition-5", fact3, engine.facts);
		rule4.requiredConditions.add(condition4);
		rule4.requiredConditions.add(condition5);
		DisplayMessageAction action4 = new DisplayMessageAction("Action-4", "This worked also!");
		action4.setEngine(engine);
		rule4.actions.add(action4);
		engine.defineRule(rule4);

		engine.Run("No facts asserted - nothing should happen!");
		
		engine.assertFact(fact1);
		engine.Run("Fact1 asserted - Action1 should fire!");
		
		engine.retractFact(fact1);
		engine.assertFact(fact2);
		engine.Run("Fact1 retracted; Fact2 asserted - Action2 should fire asserting Fact3 which fires Rule3 which displays IT WORKED.  Rule4 should fire and display THIS ALSO WORKED!");
	}

	public static void main(String[] args)
	{
		Program pgm = new Program();
		pgm.Run();
	}

}
