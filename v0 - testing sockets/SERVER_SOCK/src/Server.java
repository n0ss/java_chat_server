import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
 
public class Server {
 
    public static void main(String[] args) {

         try {

             ServerSocket server = new ServerSocket(3333);

             System.out.println("Server : waiting on port 3333");

             Socket client = server.accept();
             
             System.out.println("Server : connection successful on port 3333");

             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

             PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);

             String message = in.readLine();

             out.println("Server at \'" + Calendar.getInstance().getTime()+"\' : "+ message + "_PONG");

             in.close(); out.close(); client.close();server.close();

         } catch (Exception e) {System.out.println("Server error");}


     }

 }