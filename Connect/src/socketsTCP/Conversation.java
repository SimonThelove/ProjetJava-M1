/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package socketsTCP;

import gestionProtocole.GestionProtocoleServeur;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class Conversation extends Thread {
    
    private PrintStream     sortieSocket;
    private BufferedReader  entreeSocket;
    private Socket          socket;
    
    private GestionProtocoleServeur gp;
    
    private Boolean         stop;
    private String          entree;
    private String          sortie;
  
    public Conversation(Socket soc, GestionProtocoleServeur gp) {
        
        this.gp = gp;
        this.socket = soc;
        
        try {
            sortieSocket = new PrintStream(soc.getOutputStream());
            entreeSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop = false;
    }
      
    public void echanger(){
      try {
            entree = entreeSocket.readLine();
            if (entree != null){
                // Traitement requete
                sortie = gp.requete(entree);
                // Envoi au client
                sortieSocket.println(sortie);
            } else
                stop = true;
        } catch (IOException ex) {
            Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            stop = true;
        }
    }
    @Override
    public void run() {
        while(!stop){
            echanger();
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
