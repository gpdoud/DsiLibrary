package com.cincom.plato;

import java.util.ArrayList;
import com.cincom.plato.rule.*;

public class Relations extends ArrayList<Relation>
{

	/**
	 * Creates a relation between two entities.  The order of entities
	 * is not important.
	 * @param entity1 - the first entity
	 * @param entity2 - the second entity
	 */
	public static Relation createRelation(Entity entity1, Entity entity2, IBaseRule rule)
	{
		Relation r = new Relation(entity1, entity2);
		r.setRule(rule);
		entity1.getRelations().add(r);
		entity2.getRelations().add(r);
		return r;
	}
	
	private static final long serialVersionUID = 8264933416365316351L;
}
