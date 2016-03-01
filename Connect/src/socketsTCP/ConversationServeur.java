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
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class ConversationServeur extends Thread {
    
    private PrintStream     sortieSocket;
    private BufferedReader  entreeSocket;
    
    private GestionProtocoleServeur gp;
    private Hashtable clients_co;
    
    private Boolean         stop;
    private Boolean         done;
    private String          entree;
    private String          sortie;
    private String          mailCo;
    private String          portClient;
    
    // Constructeur pour les echanges client / serveur
    public ConversationServeur(Socket soc, GestionProtocoleServeur gp, Hashtable clients_co) {
        
        this.gp = gp;
        this.clients_co = clients_co;
        
        try 
        {
            sortieSocket = new PrintStream(soc.getOutputStream());
            entreeSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        }
        catch (IOException ex) 
        {
            Logger.getLogger(ConversationServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stop = false;
        done = false;
        
System.out.println("Conversation start : OK");

    }
    
    public Boolean getDone(){
        return done;
    }
      
    public void echanger(){
      try {
            entree = entreeSocket.readLine();
            if (entree != null){
                
                // Si le contenu du message est EXACTEMENT une suite de 5 chiffres
                // alors il s'agit du numéro de port associé au client
                if (entree.matches("[0-9]{5}"))
                {
                    System.out.println("Port client = " + entree);
                    portClient = entree;
                    clients_co.put(portClient, "anon_" + portClient);
                    
System.out.println("Creation client connecte : " + clients_co.get(portClient));

                }
                else {
                    
System.out.println("Reception Socket : " + entree);

                    // Traitement requete
                    sortie = gp.requete(entree);
                    // Envoi au client
                    sortieSocket.println(sortie);
                    
System.out.println("Sortie Socket : " + sortie);

                    if (sortie.contains("MSG|Vous etes bien connecte."))
                    {
                        // récupération temporaire de l'adresse mail du client
                        mailCo = entree.substring(10, entree.indexOf("|", 10));
                        
System.out.println(">>> MailCo SOCKET : " + mailCo);

                        try {
                            clients_co.replace(portClient, gp.getServeur().consulter(mailCo, "1").get(6)
                                                   + " " + gp.getServeur().consulter(mailCo, "1").get(4));
                            
System.out.println("Clients_co - modif " + portClient + " : " + clients_co.get(portClient));

                        } catch (SQLException ex) 
                        {
                            Logger.getLogger(ConversationServeur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    if (sortie.contains("MSG|Vous vous etes bien deconnecte."))
                    {
                        clients_co.remove(portClient);
                        done = true;
                        
System.out.println("Fermeture socket client : " + portClient);
System.out.println("----------------------------------------");

                    }
                }
            }
            else stop = true;
            
        } catch (IOException ex) 
        {
            Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            stop = true;
        }
    }
    
    @Override
    public void run() 
    {
        while(!stop)
        {
            echanger();
        }
    }
}
