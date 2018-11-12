package plato.client;

import java.net.*;
import java.io.*;

public class SocketClient
{
	static final int port = 55555;
	static final String host = "localhost";
	static final String inputPrompt = ">";
	static final String outputPrompt = "#";
	
	public static void main(String[] args) throws IOException
	{
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + host);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        while ((fromServer = in.readLine()) != null) {
            System.out.println(outputPrompt + fromServer);
            if (fromServer.equalsIgnoreCase("quit") || fromServer.equalsIgnoreCase("tiuq"))
                break;
		    System.out.print(inputPrompt);
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                //System.out.println("Client: " + fromUser);
                out.println(fromUser);
            }
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}


