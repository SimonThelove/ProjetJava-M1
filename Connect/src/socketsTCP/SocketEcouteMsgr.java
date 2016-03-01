/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package socketsTCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lamoure
 */
public class SocketEcouteMsgr {
    
    public final static int portDefault = 12314; /** Port par defaut */
    public int port; // port d'ecoute pour messenger
    private Boolean done = false; // condition de fermeture du socket d'écoute
    
    private ConversationP2P conversation;
    private ServerSocket socketEcoute; // Variable socket

    public void socket ()
    {
        socketEcoute = null;
        try
        {
            socketEcoute = new ServerSocket(portDefault);
            port = portDefault;
        }
        catch (IOException ex)
        {
            // fin de connexion
            System.err.println("Impossible de crer un socket d'ecoute sur ce port : " + ex);
            try
            {
                // on demande un port anonyme 
                socketEcoute = new ServerSocket();
                port = socketEcoute.getLocalPort();
                System.out.println("Port serveur : " + port);
            }
            catch (IOException ex2)
            {
                // fin de connexion
                System.err.println("Impossible de creer un socket d'ecoute : " + ex2);
            }
        }
        while (!done)
        {
            System.out.println("En attente de connexion sur le port : " + port);
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
    }
    
    public void close(){
        try {
            socketEcoute.accept().close();
        } catch (IOException ex) {
            Logger.getLogger(SocketEcouteMsgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
