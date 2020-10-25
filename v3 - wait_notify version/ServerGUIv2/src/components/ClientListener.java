package components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.ServerDispatcher;

public class ClientListener implements Runnable {
	
	private ServerDispatcher mServerDispatcher;
	private ClientInfo mClientInfo;
	private BufferedReader mIn;	
	
	public ClientListener(ClientInfo info, ServerDispatcher dispatcher) {
		// TODO Auto-generated constructor stub
		
		try {
			
			mServerDispatcher = dispatcher;
			mClientInfo = info;
			mIn = new BufferedReader(new InputStreamReader(mClientInfo.mSocket.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Serveur ClientListener() : constructeur");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		String message;
		
		try {
			message = mIn.readLine();
			mClientInfo.pseudo = message;
			mServerDispatcher.printClients();
			mServerDispatcher.dispatchMessage(mClientInfo.getWelcomeMessage());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Serveur ClientListener() : attribution du pseudo");
			e1.printStackTrace();
		}
		
		
		while (!mClientInfo.mSocket.isClosed()) {
			
			try {
				
				message = mIn.readLine();
				
				if (message!=null) {
					
					if (message.charAt(0)=='/') {
						if (message.substring(1).startsWith("exit")) {							
							mClientInfo.mSocket.close();
						}
						else if (message.substring(1).startsWith("pm ")) {
							message = message.substring(4, message.length());
							
							String dest = message.split("\\s+")[0];
							
							message = message.substring(dest.length()+1,message.length());
							
							mServerDispatcher.dispatchPrivateMessage("PM: "+mClientInfo.pseudo+" > "+message,dest);
							
						}
						else if (message.substring(1).startsWith("shout ")) {
							message = message.substring(7, message.length());
							
							message = message.toUpperCase();
							
							mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
							
						}
						else if (message.substring(1).startsWith("list")) {
							
							mServerDispatcher.sendClientList(mClientInfo.pseudo);
						}
						else if (message.substring(1).startsWith("help")) {
							
							mClientInfo.mClientSender.sendMessage("INFO > /pm pseudo message : Envoyer un message privé");
							mClientInfo.mClientSender.sendMessage("INFO > /shout message : Envoyer un message en CAPSLOCK");
							mClientInfo.mClientSender.sendMessage("INFO > /list : Lister les utilisateurs connectés");
							mClientInfo.mClientSender.sendMessage("INFO > /help : Afficher l'aide");
							mClientInfo.mClientSender.sendMessage("INFO > /exit : Déconnecter la session");
							
						}
						else {
							mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
							
						}
					}
					else {
						mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
					}
						
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur Serveur ClientListener() : récupération message - dispatch");
				e.printStackTrace();
			}
		}
		
		mServerDispatcher.deleteClient(mClientInfo);
		
	}

}
