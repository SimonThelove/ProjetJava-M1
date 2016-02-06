package socketsTCP;
import java.io.*;
import java.net.*;


public class SocketClient {
	
	 public String socket(String message){
		    Socket          leSocket;
		    PrintStream     fluxSortieSocket;
		    BufferedReader  fluxEntreeSocket;
		    
		    String retour = null;
		    
		    try {
		      leSocket = new Socket("localhost", 7); // socket sur echo
		      System.err.println("Connecté sur : "+leSocket);
		      
		      fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
		      fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
		      
		      fluxSortieSocket.println(message);//Envoit vers serveur
		      retour = fluxEntreeSocket.readLine();// Lecture et reception du flux serveur

		      leSocket.close();
		    }
		    catch (UnknownHostException ex)
		    {
		      System.err.println("Machine inconnue : "+ex);
		      ex.printStackTrace();
		    }
		    catch (IOException ex)
		    {
		      System.err.println("Erreur : "+ex);
		      ex.printStackTrace();
		    }
			return retour;    
		  }
}
