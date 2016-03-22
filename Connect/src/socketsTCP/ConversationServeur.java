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
    private Socket          socket;
    
    private GestionProtocoleServeur gp;
    private Hashtable clients_co;
    
    private Boolean         stop;
    private Boolean         done;
    private String          entree;
    private String          sortie;
    private String          mailCo;
    private Integer         portClient;
    private String          portMsgr;
    
    // Constructeur pour les echanges client / serveur
    public ConversationServeur(Socket soc, GestionProtocoleServeur gp, Hashtable clients_co) {
System.err.println("#### Construct ConversationServeur..."); 
        this.gp = gp;
        this.clients_co = clients_co;
        this.socket = soc;
        
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
        
System.out.println("++ Conversation Serveur start : OK");
    }
    
    public Boolean getDone(){
        return done;
    }
      
    public void echanger(){
      try {
System.err.println("[CONV_SRV] echanger");
            entree = entreeSocket.readLine();
            
            if (entree != null){
                    portClient = socket.getPort();
System.out.println("++ ENTREE - Port Cli > Srv = " + portClient);
                    clients_co.put(portClient, "anon_" + portClient);                    
System.out.println("++ Creation client connecte");

                    if (entree.startsWith("P2PH")){
                        this.portMsgr = entree.substring(5, entree.indexOf("|", 5));
                        String client = entree.substring(entree.indexOf("|", 5) + 1);
                        if (!client.equalsIgnoreCase("null")) {
                            clients_co.remove(portClient);
                            clients_co.put(portMsgr, client);
                        } else {
                            clients_co.remove(portClient);
                            clients_co.put(portMsgr, "anon_" + portClient);
                        }
                    }
                    
System.out.println("++ ENTREE = " + entree);

                    // Traitement requete
                    sortie = gp.requete(entree);
                    // Envoi au client
                    sortieSocket.println(sortie);
                    
System.out.println("++ SORTIE = " + sortie);

                    if (sortie.contains("MSG|Vous etes bien connecte."))
                    {
                        // récupération temporaire de l'adresse mail du client
                        mailCo = entree.substring(10, entree.indexOf("|", 10));
                        
System.out.println("++! Recuperation mail utilisateur connecté");

                        try {
System.out.println("++! Modification Hashtable clients connectés");
                            String nom = gp.getServeur().consulter(mailCo, "1", null).get(6);
                            String prenom = gp.getServeur().consulter(mailCo, "1", null).get(4);
                            if (clients_co.containsKey(portClient))
                                clients_co.replace(portClient, nom + " " + prenom);
                            else
                                clients_co.replace(portMsgr, nom + " " + prenom);
                        } catch (SQLException ex) 
                        {
                            Logger.getLogger(ConversationServeur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    if (sortie.contains("MSG|Vous vous etes bien deconnecte.") || sortie.contains("QUIT|"))
                    {
                        clients_co.remove(portClient);
                        done = true;
System.out.println("++ Fermeture Conversation : " + portClient);
                    }
            }
            else stop = true;
System.err.println("[CONV_SRV] echanger FIN -----");            
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
