package plato.server;

public class PlatoProtocol
{
	public String processInput(String theInput)
	{
		if(theInput == null)
			return "Connected to SocketServer...";
		StringBuffer sb = new StringBuffer(theInput);
		String theOutput = sb.reverse().toString();
		return theOutput;
	}
}
