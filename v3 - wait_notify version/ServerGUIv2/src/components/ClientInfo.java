package components;

import java.net.Socket;

import server.ServerDispatcher;

public class ClientInfo {
	
	public Socket mSocket;
	public ClientListener mClientListener;
	public ClientSender mClientSender;
	public String pseudo;
	
	public ClientInfo(Socket client, ServerDispatcher dispatcher) {
		// TODO Auto-generated constructor stub
		
		mSocket = client;
		mClientListener = new ClientListener(this,dispatcher);
		mClientSender = new ClientSender(this);
		
		Thread t_listener = new Thread (mClientListener);
		t_listener.setDaemon(true);
		t_listener.setPriority(Thread.NORM_PRIORITY);
		t_listener.start();
		
		Thread t_sender = new Thread (mClientSender);
		t_sender.setDaemon(true);
		t_listener.setPriority(Thread.NORM_PRIORITY);
		t_sender.start();
	}
	
	public String getWelcomeMessage () {
		return "SERVER > Bienvenue sur le chat "+this.pseudo+" !";
	}

}
