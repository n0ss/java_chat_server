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
	public synchronized void run() {
		// TODO Auto-generated method stub
		
		String message;
		
		try {
			message = mIn.readLine();
			mClientInfo.pseudo = message;
			mServerDispatcher.printClients();
			mClientInfo.mClientSender.welcomeMessage();
			
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
							
							synchronized (mServerDispatcher) {
								mServerDispatcher.dispatchPrivateMessage("PM: "+mClientInfo.pseudo+" > "+message,dest);
								mServerDispatcher.notify();
							}
						}
						else if (message.substring(1).startsWith("shout ")) {
							message = message.substring(7, message.length());
							
							message = message.toUpperCase();
							
							synchronized (mServerDispatcher) {
								mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
								mServerDispatcher.notify();
							}
						}
						else if (message.substring(1).startsWith("help")) {
							
							mClientInfo.mClientSender.sendMessage("INFO > /pm pseudo message : Envoyer un message priv�");
							mClientInfo.mClientSender.sendMessage("INFO > /shout message : Envoyer un message en CAPSLOCK");
							mClientInfo.mClientSender.sendMessage("INFO > /help : Afficher l'aide");
							mClientInfo.mClientSender.sendMessage("INFO > /exit : D�connecter la session");
							mClientInfo.mClientSender.notify();
							
						}
						else {
							synchronized (mServerDispatcher) {
								mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
								mServerDispatcher.notify();
							}
						}
					}
					else {
						synchronized (mServerDispatcher) {
							mServerDispatcher.dispatchMessage(mClientInfo.pseudo+" > "+message);
							mServerDispatcher.notify();
						}
					}
						
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erreur Serveur ClientListener() : r�cup�ration message - dispatch");
				e.printStackTrace();
			}
		}
		
		mServerDispatcher.deleteClient(mClientInfo);
		
	}

}