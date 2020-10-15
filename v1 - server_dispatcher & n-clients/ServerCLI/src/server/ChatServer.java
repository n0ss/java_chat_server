package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	
	public static final int LISTENING_PORT = 5555;
	
	

	public static void main(String[] args) {
		
        try {
        	
        	// Démarrage du serveur
			ServerSocket server = new ServerSocket(LISTENING_PORT);
            System.out.println("Server : attente sur le port "+LISTENING_PORT);
            
            ServerDispatcher dispatcher = new ServerDispatcher();
            Thread t_dispatcher = new Thread(dispatcher);
            t_dispatcher.start();
            t_dispatcher.setDaemon(true);
            
            Socket new_client;
            
            while (true) {
                new_client = server.accept();
                
                dispatcher.addClient(new_client);
            }
            

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
