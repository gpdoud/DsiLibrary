package com.cincom.plato;

import com.cincom.plato.rule.*;

public class Relation
{
	private Entity entity1;
	public Entity getEntity1() { return entity1; }

	private Entity entity2;
	public Entity getEntity2() { return entity2; }
	
	private IBaseRule rule;
	public IBaseRule getRule() { return rule; }
	public void setRule(IBaseRule rule) { this.rule = rule; }
	
	public Relation(Entity entity1, Entity entity2)
	{
		this.entity1 = entity1;
		this.entity2 = entity2;
	}
}
