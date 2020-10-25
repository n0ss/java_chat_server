package server;

import java.net.Socket;
import java.util.Vector;

import components.ClientInfo;
import components.ClientSender;

public class ServerDispatcher implements Runnable {
	
	private Vector<String> mMessageQueue;
	private Vector<ClientInfo> mClients;
	private ClientSender mServerDispatcher;
	
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
			
			mServerDispatcher = mClients.firstElement().mClientSender;
		}
		
	}
	
	public void deleteClient(ClientInfo old_client) {
		// TODO Auto-generated method stub
		synchronized (mClients) {
			mClients.removeElement(old_client);
			
			if (!mClients.isEmpty()) {mServerDispatcher = mClients.firstElement().mClientSender;}
			else {mServerDispatcher = null;}
			
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
		synchronized (this) {
			mMessageQueue.add(message);
			this.notify();
		}
		
	}
	
	public void dispatchPrivateMessage(String message, String dest ) {
		
		for (ClientInfo client: mClients) {
			if (client.pseudo.equals(dest)) {
				
				synchronized (client.mClientSender) {
					client.mClientSender.sendMessage(message);
					client.mClientSender.notify();
				}
				
			}
		}
	}
	
	public void sendClientList(String dest) {
		for (ClientInfo client: mClients) {
			if (client.pseudo.equals(dest)) {
				client.mClientSender.sendMessage(getClientsList());
			}
		}
	}
	
	private String getClientsList () {
		String list = "";
		
		list += "SERVER > ----------- CLIENTS ACTIFS -----------\n";
		
		synchronized (mClients) {
			for (ClientInfo client: mClients) {
				list = list + ("SERVER > \t\t"+client.pseudo+"\n");
			}
		}
		
		list += "SERVER > ------------------------------------";
		
		return list;
	}
	
	private void sendMessageToAllClients() {
		// TODO Auto-generated method stub
		
		String message;
		
		synchronized (mServerDispatcher) {
			
			message = nextMessageFromQueue();
			mMessageQueue.removeElement(message);
			
			for (ClientInfo client: mClients) {
				client.mClientSender.sendMessage(message);
				
			}
			
			mServerDispatcher.notifyAll();
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
				
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
		}
		
	}
	
	

}
