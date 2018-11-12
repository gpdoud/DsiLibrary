package com.cincom.plato;

public class Entity
{
	/**
	 * This holds the fields of the entity
	 */
	private Fields fields = new Fields();

	/**
	 * This holds the rows of data
	 */
	private Rows rows = new Rows(fields);
	
	/**
	 * The name of the entity
	 */
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	/**
	 * The description of the entity
	 */
	private String description;
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	/**
	 * The relations this entity has with another entity
	 */
	private Relations relations = new Relations();
	public Relations getRelations() { return relations; }
	public void setRelations(Relations relations) { this.relations = relations; }
	
	/**
	 * Returns the relations with the associated entity
	 * @param entity The entity related to
	 * @return a Relation instance associated with the entity or null if not related
	 */
	public Relation getRelation(Entity entity)
	{
		for(Relation relation : this.relations) 
		{
			if(relation.getEntity1().equals(entity))
				return relation;
			if(relation.getEntity2().equals(entity))
				return relation;
		}
		return null;
	}

	/**
	 * Creates a row for data
	 */
	public Row createRow()
	{
		Row row = rows.createInstance();
		return row;
	}
	/**
	 * Adds a new field to the entity
	 * 
	 * @param name - the name of the field
	 * @param type - the type of the field
	 */
	public void createField(String name, String type)
	{
		if(!fields.contains(name))
		{
			Field f = new Field(name, type);
			fields.add(f);
		}
	}
	/**
	 * Adds a new field to the entity
	 * 
	 * @param name - the name of the field
	 * Type defaults type to string
	 */
	public void createField(String name)
	{
		createField(name, "String");
	}
	/**
	 * Removes the field from the entity
	 * 
	 * @param name - the name of the field
	 */
	public void removeAttribute(String name)
	{
		if(fields.contains(name))
			fields.remove(name);
	}
	
	/**
	 * Constructor
	 * @param name - the name of the entity
	 * @param description - the description of the entity
	 */
	public Entity(String name, String description) 
	{
		this.setName(name);
		this.setDescription(description);
	}
	
	/**
	 * Constructor - Default
	 *
	 */
	public Entity() {}
	
	/**
	 * Displays the name and description of the entity
	 * This overrides the default toString()
	 */
	public String toString() 
	{
		return String.format("%s:%s", 
				this.getName(), this.getDescription());
	}
	
	/**
	 * This dumps the contents of the entity for debugging
	 *
	 */
	public void dump()
	{
		// dump the entity name
		System.err.format("ENTITY Name:[%s], Description:[%s]\n", this.name, this.description);
		java.util.ArrayList<String> fieldNames = new java.util.ArrayList<String>();
		// dump the fields
		System.err.format("-Fields:");
		for(Field f : fields)
		{
			System.err.format("%s[%s] ", f.getName(), f.getType());
			fieldNames.add(f.getName());
		}
		System.err.println();
		// dump the data
		for(Row r : rows)
		{
			System.err.print("+Row:");
			for(String fieldName : fieldNames)
			{
				System.err.format("%s[%s] ", fieldName, r.get(fieldName));
			}
			System.err.println();
		}
	}
}
