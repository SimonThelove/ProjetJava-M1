/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package socketsTCP;

import java.net.*;
import java.io.*;
import gestionProtocole.GestionProtocoleServeur;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Serveur;

/**
 *
 * @author Yohann
 */

public class SocketServeur
{
    public final static int portDefault = 12314; /** Port par defaut */
    public int port; // port serveur associe au client connecte
    private Boolean done = false; // condition de fermeture de la connexion
    
    private ConversationServeur conversation;
    private Serveur serveur; // Variables serveur
    private GestionProtocoleServeur gp;
    private ServerSocket leServeur; // Variable socket
    private Hashtable clients_co;

    public Hashtable getClients_co() {
        return clients_co;
    }
    
    public void socket ()
    {
System.err.println("[SOCK_SRV] socket");

        serveur = new Serveur();
        clients_co = new Hashtable();
        gp = new GestionProtocoleServeur(serveur, clients_co);
        leServeur = null;
        try
        {
            leServeur = new ServerSocket(portDefault);
            port = portDefault;
System.out.println("## PORT SERVEUR = " + port);            
        }
        catch (IOException ex)
        {
            // fin de connexion
            System.err.println("Impossible de crer un socket serveur sur ce port : " + ex);
            try
            {
                // on demande un port anonyme 
                leServeur = new ServerSocket();
                port = leServeur.getLocalPort();
System.err.println("## PORT SERVEUR = " + port + " <!> DIFFERENT DU PORT PAR DEFAUT <!>");
            }
            catch (IOException ex2)
            {
                // fin de connexion
                System.err.println("SocketServeur - Creation impossible " + ex2);
            }
        }
        while (!done)
        {
System.out.println("## Attente de connexion sur le port : " + port);
            try
            {                                
                conversation = new ConversationServeur(leServeur.accept(), gp, clients_co);
                conversation.start();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            done = conversation.getDone();
        }
        close();
System.out.println("[SOCK_SRV] socket FIN -----");
    }
    
    public void close(){
        try {
System.err.println("[SOCK_SRV] SocketServeur close");                        
            leServeur.accept().close();
System.out.println("## Fermeture socket ecoute num. " + leServeur.getLocalPort());                        
        } catch (IOException ex) {
            Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
