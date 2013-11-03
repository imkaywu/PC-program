package kay;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int SERVER_PORT = 8080;
	private ServerSocket serverSocket;
	private ClientServerThread clientServerThread;
	public volatile boolean exit = false;

	public Server() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		clientServerThread = new ClientServerThread();
		//System.out.println("already listen the port.");
		try {
			while (!exit) {
				Socket socket = serverSocket.accept();
				clientServerThread.setSocket(socket);
				new Thread(clientServerThread).start();// for every client, set
														// up a thread to deal
														// with the data
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}

	}

}
