package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ChatClient {
	
	public static final int SERVER_PORT = 5555;
	public static final String SERVER_HOSTNAME = "localhost";
	public static Sender sender;
	private static GUI window = null;

	public static void main(String[] args) {
		
		try {
			
			Socket socket = new Socket(SERVER_HOSTNAME,SERVER_PORT);
			
			sender = new Sender(socket);
			
			Thread t_sender = new Thread(sender);
			t_sender.setDaemon(true);
			t_sender.setPriority(Thread.NORM_PRIORITY);
			t_sender.start();
			
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window = new GUI();
						window.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			BufferedReader mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String chat_output = "0";
						
			while (chat_output!=null) {
				
				try {
					chat_output = mIn.readLine();
					
					window.getChatArea().append(chat_output+"\n");
					
					//OLD CLI System.out.println(chat_output);
				}
				catch (SocketException e) {
					// TODO Auto-generated catch block
					System.out.println("Erreur Client ChatClient : main() thread - lecture BR socket");
					e.printStackTrace();
				}
				
			}
			
			socket.close();
			window.getChatArea().append("Client déconnecté. Vous pouvez fermer la fenêtre.");
			window.allowExit();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur Client ChatClient : main() thread");
			e.printStackTrace();
		}
		
	}
	
}

class GUI {
		
	private JFrame frame;
	
	private JPanel panelName;
	private JTextField fieldName;
	private JButton buttonName;
	private JLabel lblPanelName;
	private JButton buttonExit;
	
	private JTextArea chatArea;
	private JScrollPane chatScroll;
	
	private JPanel panelInput;
	private JTextField fieldInput;
	private JButton buttonSend;
		
	public GUI() {
		initialize();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JTextArea getChatArea() {
		return chatArea;
	}
	
	public void allowExit () {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		
		// ---------- PARTIE INIT JFRAME
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		buttonSend = new JButton("Envoyer");
		
		// ---------- PARTIE INIT PANEL ENVOI NOM
		panelName = new JPanel();
		frame.getContentPane().add(panelName, BorderLayout.NORTH);
		
		// ----- JLABEL
		lblPanelName = new JLabel("Nom");
		panelName.add(lblPanelName);
		
		// ----- TEXTFIELD NOM
		fieldName = new JTextField();
		panelName.add(fieldName);
		fieldName.setColumns(10);
		
		// ----- JBUTTON NOM
		buttonName = new JButton("Connexion");
		frame.getRootPane().setDefaultButton(buttonName);
		buttonName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.sender.pushMessage(fieldName.getText());
				fieldName.setText("");
				buttonSend.setEnabled(true);
				buttonName.setEnabled(false);
				frame.getRootPane().setDefaultButton(buttonSend);
				fieldInput.requestFocusInWindow();
			}
		});
		panelName.add(buttonName);
		
		// ----- JBUTTON EXIT
		buttonExit = new JButton("Deconnexion");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.sender.pushMessage("/exit");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		panelName.add(buttonExit);
		
		// ---------- PARTIE INIT TEXTAREA CHAT
		chatArea = new JTextArea();
		frame.getContentPane().add(chatArea, BorderLayout.CENTER);
		chatScroll = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(chatScroll, BorderLayout.CENTER);
		
		// ---------- PARTIE INIT PANEL INPUT
		panelInput = new JPanel();
		frame.getContentPane().add(panelInput, BorderLayout.SOUTH);
		
		// ----- TEXTFIELD INPUT
		fieldInput = new JTextField();
		fieldInput.setHorizontalAlignment(SwingConstants.LEFT);
		panelInput.add(fieldInput);
		fieldInput.setColumns(30);
		
		// ----- BUTTON INPUT
		buttonSend.setEnabled(false);
		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatClient.sender.pushMessage(fieldInput.getText());
				fieldInput.setText("");
			}
		});
		panelInput.add(buttonSend);
		
		chatArea.append("INFO > Veuillez choisir votre pseudo et cliquer sur 'Envoyer'.\n");
		
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
	
}
