package components;

import java.net.Socket;

import server.ServerDispatcher;

public class ClientInfo {
	
	public Socket mSocket;
	public ClientListener mClientListener;
	public ClientSender mClientSender;
	
	public ClientInfo(Socket client, ServerDispatcher dispatcher) {
		// TODO Auto-generated constructor stub
		
		mSocket = client;
		mClientListener = new ClientListener(this,dispatcher);
		mClientSender = new ClientSender(this);
	}

}
