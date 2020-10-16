package server;

import java.net.Socket;
import java.util.Vector;

import components.ClientInfo;

public class ServerDispatcher implements Runnable {
	
	private Vector<String> mMessageQueue;
	private Vector<ClientInfo> mClients;
	
	public ServerDispatcher() {
		// TODO Auto-generated constructor stub
		
		mMessageQueue = new Vector<String>();
		mClients = new Vector<ClientInfo>();
	}
	
	public void addClient(Socket client) {
		// TODO Auto-generated method stub
		ClientInfo new_info = new ClientInfo(client,this);
		
		synchronized (mClients) {
			mClients.add(new_info);
		}
		
	}
	
	public void deleteClient(ClientInfo old_client) {
		// TODO Auto-generated method stub
		synchronized (mClients) {
			mClients.removeElement(old_client);
			
			System.out.println("Un client s'est déconnecté : " + old_client.pseudo+".");
		}
		printClients();
		
	}
	
	public void printClients() {
		// TODO Auto-generated method stub
		System.out.println("---------- CLIENTS ACTIFS ----------");
		synchronized (mClients ) {
			for (ClientInfo client: mClients) {
				printClient(client);
			}
		}
		System.out.println("------------------------------------\n");
	}
	
	private void printClient(ClientInfo client) {
		// TODO Auto-generated method stub
		System.out.println("\t\t"+client.pseudo);		
	}
	
	public void dispatchMessage(String message) {
		// TODO Auto-generated method stub
		synchronized (mMessageQueue) {
			mMessageQueue.add(message);
		}
		
	}
	
	public void dispatchPrivateMessage(String message, String dest ) {
		for (ClientInfo client: mClients) {
			if (client.pseudo.equals(dest)) {
				client.mClientSender.sendMessage(message);
			}
		}
	}
	
	private void sendMessageToAllClients() {
		// TODO Auto-generated method stub
		
		String message = nextMessageFromQueue();
		
		for (ClientInfo client: mClients) {
			client.mClientSender.sendMessage(message);
		}
		
		synchronized (mMessageQueue) {			
			mMessageQueue.removeElement(message);
		}
		
	}
	
	private String nextMessageFromQueue() {
		// TODO Auto-generated method stub

		return mMessageQueue.firstElement();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true) {
			
			if (!mMessageQueue.isEmpty()) {
				sendMessageToAllClients();
			}
			else {
				
			}
			
		}
		
	}
	
	

}
