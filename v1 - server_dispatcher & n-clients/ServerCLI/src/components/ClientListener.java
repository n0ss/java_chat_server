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
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
