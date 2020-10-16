package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ChatClient {
	
	public static final int SERVER_PORT = 5555;
	public static final String SERVER_HOSTNAME = "localhost";
	

	public static void main(String[] args) {
		
		try {
			
			Socket socket = new Socket(SERVER_HOSTNAME,SERVER_PORT);
			
			Sender sender = new Sender(socket);
			
			Thread t_sender = new Thread(sender);
			t_sender.setDaemon(true);
			t_sender.setPriority(Thread.NORM_PRIORITY);
			t_sender.start();
			
			BufferedReader mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String chat_output = "0";
			
			while (chat_output!=null) {
				
				try {
					chat_output = mIn.readLine();
					
					System.out.println(chat_output);
				}
				catch (SocketException e) {
					// TODO Auto-generated catch block
					System.out.println("Erreur Client ChatClient : main() thread - lecture BR socket");
					e.printStackTrace();
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Client ChatClient : main() thread");
			e.printStackTrace();
		}
		
	}
	
}
