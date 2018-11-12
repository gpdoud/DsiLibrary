package plato.server;

import java.net.*;
import java.io.*;

public class SocketServerThread extends Thread
{
    private Socket socket = null;

    public SocketServerThread(Socket socket) {
    	super("SocketServerThread");
    	this.socket = socket;
    }

    public void run() {

	try {
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
				    new InputStreamReader(
				    socket.getInputStream()));

	    String inputLine, outputLine;
	    //KnockKnockProtocol kkp = new KnockKnockProtocol();
	    PlatoProtocol protocol = new PlatoProtocol();
	    outputLine = protocol.processInput(null);
	    out.println(outputLine);

	    while ((inputLine = in.readLine()) != null) {
	    	outputLine = protocol.processInput(inputLine);
	    	out.println(outputLine);
	    	if (outputLine.equals("quit"))
	    		break;
	    }
	    out.close();
	    in.close();
	    socket.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
