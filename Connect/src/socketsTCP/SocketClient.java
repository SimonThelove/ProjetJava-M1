package socketsTCP;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketClient {
    
        private Socket leSocket;
        private PrintStream fluxSortieSocket;
        private BufferedReader fluxEntreeSocket;
        private Integer portClient;
        private String retour = null;
    
    public SocketClient(){
            try {
System.err.println("#### Construct Socket vers Serveur...");  

                leSocket = new Socket("localhost", 12314);
                System.err.println("Connecte sur : "+leSocket);
                portClient = leSocket.getLocalPort();
                
System.out.println("## PORT CLIENT = " + portClient);

                this.fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
                this.fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
                
                
System.out.println("## Creation socket serveur : OK");                
            } catch (IOException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    
    public Socket getSocket(){
        return leSocket;
    }
	
    public String echangeServeur(String message){

        try {
System.err.println("@SocketClient echangerServeur");
System.out.println("## ENTREE = " + message);

            fluxSortieSocket.println(message);// Envoi vers serveur
            retour = fluxEntreeSocket.readLine();// Lecture et reception du flux serveur
System.out.println("## SORTIE = " + retour);

        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
      }
    
    public void close () {
        try {
System.err.println("@SocketClient close");            
            leSocket.close();
System.out.println("## Fermeture socket num. " + leSocket.getLocalPort());            
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void quit() {
        try {
System.err.println("@SocketClient quit");
            echangeServeur("QUIT|");            
            leSocket.close();
System.out.println("## Fermeture socket num. " + leSocket.getLocalPort());                        
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
