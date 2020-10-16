package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	
	public static final int LISTENING_PORT = 5555;
	
	

	public static void main(String[] args) {
		
        try {
        	
			ServerSocket server = new ServerSocket(LISTENING_PORT);
            System.out.println("Server : attente sur le port "+LISTENING_PORT);
            
            ServerDispatcher dispatcher = new ServerDispatcher();
            Thread t_dispatcher = new Thread(dispatcher);
            t_dispatcher.setDaemon(true);
            t_dispatcher.setPriority(Thread.NORM_PRIORITY);
            t_dispatcher.start();
            
            Socket new_client;
            
            while (true) {
            	
                new_client = server.accept();
                System.out.println("Un client s'est connecté.");
                
                dispatcher.addClient(new_client);
                
            }
                        

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Serveur ChatServer : main() thread");
			e.printStackTrace();
		}
		
	}

}
