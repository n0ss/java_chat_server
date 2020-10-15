import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;


public class Client {
 
    public static void main(String[] args) {

         try {

             Socket client = new Socket("localhost",3333);

             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);

             out.println("Client_PING");

             String message = in.readLine();

             System.out.println("Client - message received : ["+ message + "]");

             in.close(); out.close(); client.close();

         } catch (Exception e) {System.out.println("Client error");}
 
    }
 
}