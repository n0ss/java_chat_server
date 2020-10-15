package server;

import java.net.Socket;
import java.util.Vector;

import components.ClientInfo;
import components.ClientSender;

public class ServerDispatcher implements Runnable {
	
	private Vector<String> mMessageQueue;
	private Vector<ClientInfo> mClients;
	//private ClientSender mServerDispatcher;
	
	public ServerDispatcher() {
		// TODO Auto-generated constructor stub
		
		mMessageQueue = new Vector<String>();
		mClients = new Vector<ClientInfo>();
		//mServerDispatcher = new ClientSender();
	}
	
	public void addClient(Socket client) {
		// TODO Auto-generated method stub
		ClientInfo new_info = new ClientInfo(client,this);
		
		mClients.add(new_info);		
		
	}
	
	public void deleteClient() {
		// TODO Auto-generated method stub

	}
	
	public void dispatchMessage() {
		// TODO Auto-generated method stub

	}
	
	private void sendMessageToAllClients() {
		// TODO Auto-generated method stub

	}
	
	private String nextMessageFromQueue() {
		// TODO Auto-generated method stub

		return mMessageQueue.firstElement();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
	}
	
	

}
