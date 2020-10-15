package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
	
	public static final int SERVER_PORT = 5555;
	public static final String SERVER_HOSTNAME = "locahost";
	

	public static void main(String[] args) {
		
		try {
			
			Socket client = new Socket(SERVER_HOSTNAME,SERVER_PORT);
			
			Sender sender = new Sender(client);
			
			Thread t_sender = new Thread(sender);
			t_sender.start();
			t_sender.setDaemon(true);
			
			BufferedReader mIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			//Receiver receiver = new Receiver(client.getInputStream());
			
			
			while (true) {
				
				System.out.println(mIn.readLine());
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
