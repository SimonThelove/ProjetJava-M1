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
                leSocket = new Socket("localhost", 12314);
                System.err.println("Connecte sur : "+leSocket);
                portClient = leSocket.getLocalPort();
            
                this.fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
                this.fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
                
                // Envoi du port client a serveur
                fluxSortieSocket.println(portClient);
                
            } catch (IOException ex) {
                Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    
    public Socket getSocket(){
        return leSocket;
    }
	
    public String echangeServeur(String message){

        try {

            fluxSortieSocket.println(message);// Envoi vers serveur
            retour = fluxEntreeSocket.readLine();// Lecture et reception du flux serveur

        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
      }
    
    public void close () {
        try {
            echangeServeur("QUIT|"); // Validation serveur de la fermeture du client
            leSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
