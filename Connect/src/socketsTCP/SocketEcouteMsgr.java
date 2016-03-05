/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package socketsTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamoure
 */
public class SocketEcouteMsgr {
    
    public final static int portDefault = 12345; /** Port par defaut */
    public int port; // port d'ecoute pour messenger
    private Boolean done = false; // condition de fermeture du socket d'écoute
    
    private ConversationP2P conversation;
    private ServerSocket socketEcoute; // Variable socket d'ecoute
    private Socket leSocket; // Socket d'envoi de messages P2P
    
    private PrintStream fluxSortieSocket;
    private BufferedReader fluxEntreeSocket;
    private String retour = null;

    public void socket ()
    {
        socketEcoute = null;
        try
        {
System.err.println("@SocketEcouteMsgr socket");            
            socketEcoute = new ServerSocket(portDefault);
            port = portDefault;
System.out.println("## PORT ECOUTE P2P = " + port);
        }
        catch (IOException ex)
        {
            // fin de connexion
            try
            {
                // on demande un port anonyme 
                socketEcoute = new ServerSocket();
                port = socketEcoute.getLocalPort();
System.err.println("## PORT ECOUTE P2P = " + port + " <!> DIFFERENT DU PORT PAR DEFAUT <!>");
            }
            catch (IOException ex2)
            {
                // fin de connexion
                System.err.println("SocketEcouteMsgr - Creation impossible " + ex2);
            }
        }
        while (!done)
        {
System.out.println("## Attente de donnexion sur le port : " + port);
            try
            {                                
                conversation = new ConversationP2P(socketEcoute.accept());
                conversation.start();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SocketEcouteMsgr.class.getName()).log(Level.SEVERE, null, ex);
            }
            done = conversation.getDone();
        }
        close();
System.err.println("@socket FIN -----");
    }
    
    public void initEnvoiP2P () {
        try {
System.err.println("@SocketEcouteMsgr initEnvoiP2P");            
            leSocket = new Socket("localhost", 12345);
System.out.println("## Creation Buffer/Stream...");            
            this.fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
            this.fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
System.out.println("## Creation OK..." + leSocket);                        
        } catch (IOException ex) {
            Logger.getLogger(SocketEcouteMsgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public String echangeP2P(String msg){

        try {
System.err.println("@SocketEcouteMsgr echangeP2P");
System.out.println("## ENTREE = " + msg);

            fluxSortieSocket.println(msg);          // Envoi vers client P2P
            retour = fluxEntreeSocket.readLine();   // Lecture et reception du flux P2P
System.out.println("## SORTIE = " + retour);

        } catch (IOException ex) {
            Logger.getLogger(SocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
      }
    
    public void close(){
        try {
System.err.println("@SocketEcouteMsgr close");                        
            socketEcoute.accept().close();
System.out.println("## Fermeture socket ecoute num. " + socketEcoute.getLocalPort());                        
        } catch (IOException ex) {
            Logger.getLogger(SocketEcouteMsgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
