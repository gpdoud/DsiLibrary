package plato.server;

import java.net.*;
import java.io.*;

public class SocketServer
{
	static int port = 55555;
	
	public static void main(String[] args) throws IOException
	{
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Listening on port: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(-1);
        }

        while (listening)
        	new SocketServerThread(serverSocket.accept()).start();

        serverSocket.close();
	}

}
