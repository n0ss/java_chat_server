package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Sender implements Runnable {
	
	private static PrintWriter mOut;
	private static Socket mSocket;
	private static Vector<String> mMessageQueue;
	
	public Sender(Socket sock) {
		// TODO Auto-generated constructor stub
		
		try {
			mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);
			mSocket = sock;
			mMessageQueue = new Vector<String>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Client - Sender() : constructeur");
			e.printStackTrace();
		}
	}
	
	public void pushMessage (String message) {
		synchronized (mMessageQueue) {
			mMessageQueue.add(message);
		}
	}
	
	private String popMessage () {
		return mMessageQueue.remove(0);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//OLD CLI Scanner scan = new Scanner(System.in);
		
		//OLD CLI System.out.print("Choisissez un pseudo : ");
		//OLD CLI mOut.println(scan.nextLine());

		while (!mSocket.isClosed()) {
			
			if (!mMessageQueue.isEmpty()) {
				synchronized (mMessageQueue) {
					mOut.println(popMessage());
				}
			}
		}
		
		//OLD CLI scan.close();
		
	}

}
