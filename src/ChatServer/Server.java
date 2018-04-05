/*
 * Tian Luan 1899271
 * based on given code 
 */
package ChatServer;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Protocol.SimpleProtocol;

public class Server {
	
	public static final String WELCOME_MSG = "Welcome to my server";
	
	public static final int USERNAME_MIN_LEN = 5;
	public static final int USERNAME_MAX_LEN = 20;
	public static final int PASSWORD_MIN_LEN = 8;
	public static final int PASSWORD_MAX_LEN = 32;
	private static HashMap<String, String> accounts = new HashMap<>();
	private static ArrayList<Message> msgList = new ArrayList<>();
	
	public static void main(String[] args) {
		System.out.println("In server");
		final int port = 2222;
		final String address = "localhost";
		
//		final String address = args[0];
//		final int port = Integer.parseInt(args[1]);
		
		ServerSocket serverSocket = null;
		try {
			// create a server socket
			// use default value of backlog by setting it to 0
			serverSocket = new ServerSocket(port, 0, InetAddress.getByName(address));
			
			// create thread pool
			ExecutorService pool = Executors.newCachedThreadPool();
			
			SimpleProtocol protocol = new SimpleProtocol();
			
			// wait for connections
			while(true) {
				// got one
				Socket socket = serverSocket.accept();
				PrintWriter toClient = new PrintWriter(socket.getOutputStream());
				toClient.println(protocol.createMessage(Server.WELCOME_MSG,"!!"));
				toClient.flush();
				
				System.out.println("Connection accepted. " + socket);
				
				// create a runnable Handler object and put it into thread pool.
				pool.execute(new Handler(socket, accounts, msgList));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
