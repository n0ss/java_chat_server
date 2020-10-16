package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {
	
	private static PrintWriter mOut;
	private static Socket mSocket;
	
	public Sender(Socket sock) {
		// TODO Auto-generated constructor stub
		
		try {
			mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())),true);
			mSocket = sock;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Client - Sender() : constructeur");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Choisissez un pseudo : ");
		mOut.println(scan.nextLine());

		while (!mSocket.isClosed()) {
			if (scan.hasNextLine()) {
				
				mOut.println(scan.nextLine());
				
			}
		}
		
		scan.close();
		
	}

}
