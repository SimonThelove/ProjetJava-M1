/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package socketsTCP;

import gestionProtocole.GestionProtocoleClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;

/**
 *
 * @author Simon
 */
public class ConversationP2P extends Thread{
    private PrintStream     sortieSocket;
    private BufferedReader  entreeSocket;
    
    private GestionProtocoleClient gp;
    private TextArea messages;
    
    private Boolean         done;
    private String          entree;
    private String          sortie;
    
    // Constructeur pour les echanges messenger (P2P)
    public ConversationP2P(Socket soc, GestionProtocoleClient gpMsgr, TextArea msgs){
                
        try 
        {
System.err.println("#### Construct ConversationP2P... "); 
            this.messages = msgs;
            this.gp = gpMsgr;
            sortieSocket = new PrintStream(soc.getOutputStream());
            entreeSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        }
        catch (IOException ex)
        {
            Logger.getLogger(ConversationP2P.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        done = false;
        
System.out.println("++ Conversation P2P start : OK");
    }
    
    public Boolean getDone(){
        return done;
    }
      
    public void echanger(){
        try
        {
System.err.println("@ConversationP2P echanger");            
            entree = entreeSocket.readLine();
            
            if (entree != null)
            {                
System.out.println("## ENTREE = " + entree);
                    gp.receptionP2P(entree, messages);
                    sortie = "P2PN|ok";
                    sortieSocket.println(sortie);
                    done = true;
System.out.println("## SORTIE = " + sortie);
            }
            else done = true;
System.err.println("@echanger FIN -----");            
        }
        catch (IOException ex) 
        {
            Logger.getLogger(SocketMessenger.class.getName()).log(Level.SEVERE, null, ex);
            done = true;
        }
    }
    
    @Override
    public void run() {
        echanger();
    }
}
