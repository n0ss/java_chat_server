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
			e.printStackTrace();
		}
		
	}
	
	private void sendMessageToClient() {
		// TODO Auto-generated method stub

	}
	
	public void sendMessage() {
		// TODO Auto-generated method stub

	}
	
	private String nextMessageFromQueue() {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
