package plato.core;

public class EngineObject
{
	static long nextId;
	long id;
	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; }
	
	String name;
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	Engine engine;
	public Engine getEngine() { return this.engine; }
	public void setEngine(Engine engine) { this.engine = engine; }
	
	public EngineObject(String name)
	{
		this.setName(name);
		this.setId(nextId++);
	}

	public EngineObject() 
	{
		this(null);
	}
	
	static
	{
		nextId = 1;
	}
}
