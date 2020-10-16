package components;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

public class ClientSender implements Runnable {
	
	private Vector<String> mMessageQueue;
	private ClientInfo mClientInfo;
	private PrintWriter mOut;
	
	
	public ClientSender(ClientInfo info) {
		// TODO Auto-generated constructor stub
		
		try {
			
			mMessageQueue = new Vector<String>();
			mClientInfo = info;
			mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientInfo.mSocket.getOutputStream())),true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Serveur ClientSender() : constructeur");
			e.printStackTrace();
		}
		
	}
	
	private void sendMessageToClient() {
		// TODO Auto-generated method stub
		synchronized (mMessageQueue) {
			mOut.println(nextMessageFromQueue());
			mMessageQueue.removeElementAt(0);
		}
		
	}
	
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		synchronized (mMessageQueue) {
			mMessageQueue.add(message);
		}
		
	}
	
	public void welcomeMessage () {
		synchronized (mMessageQueue) {
			mMessageQueue.add("SERVER > Bienvenue sur le chat "+mClientInfo.pseudo+" !");
		}
	}
	
	private String nextMessageFromQueue() {
		// TODO Auto-generated method stub

		return mMessageQueue.firstElement();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//sendMessage("SERVER > Veuillez choisir votre pseudo et cliquer sur \'Envoyer\'.");
		
		while (!mClientInfo.mSocket.isClosed()) {
			
			if (!mMessageQueue.isEmpty()) {
				sendMessageToClient();
			}
			
			
		}
	}

}
