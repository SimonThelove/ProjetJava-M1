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
public class Conversation extends Thread {
    
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
    private String          portClient;
    
    // Constructeur pour les echanges messenger (P2P)
    public Conversation(Socket soc){
System.err.println("#### Construct Conversation pour P2P...");        
        this.socket = soc;
        
        try {
            sortieSocket = new PrintStream(soc.getOutputStream());
            entreeSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop = false;
        done = false;
System.out.println("++ Conversation P2P start : OK");
    }
    
    // Constructeur pour les echanges client / serveur
    public Conversation(Socket soc, GestionProtocoleServeur gp, Hashtable clients_co) {
System.err.println("#### Construct Conversation sur Serveur...");        
        this.gp = gp;
        this.socket = soc;
        this.clients_co = clients_co;
        
        try {
            sortieSocket = new PrintStream(soc.getOutputStream());
            entreeSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
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
System.err.println("@Conversation echanger");
            entree = entreeSocket.readLine();           
            if (entree != null){

                if (entree.matches("[0-9]{5}")){ // Si le contenu du message est EXACTEMENT une suite de 5 chiffres alors il s'agit du numéro de port associé au client
System.out.println("++ ENTREE - Port Cli > Srv = " + entree);
                    portClient = entree;
                    clients_co.put(portClient, "anon_" + portClient);
System.out.println("++ Creation client connecte");
                }
                else {
System.out.println("++ ENTREE = " + entree);
                    // Traitement requete
                    sortie = gp.requete(entree);
                    // Envoi au client
                    sortieSocket.println(sortie);
System.out.println("++ SORTIE = " + sortie);
                    if (sortie.contains("MSG|Vous etes bien connecte.")){
                        mailCo = entree.substring(10, entree.indexOf("|", 10)); // récupération temporaire de l'adresse mail du client
System.out.println("++! Recuperation mail utilisateur connecté");
                        try {
                            clients_co.replace(portClient, gp.getServeur().consulter(mailCo, "1", null).get(6) + " " + gp.getServeur().consulter(mailCo, "1", null).get(4));
System.out.println("++! Modification Hashtable clients connectés");
                        } catch (SQLException ex) {
                            Logger.getLogger(Conversation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (sortie.contains("MSG|Vous vous etes bien deconnecte.")){
                        clients_co.remove(portClient);
                        done = true;
System.out.println("++ Fermeture Conversation : " + portClient);
                    }
                }
            } else
                stop = true;
System.err.println("@echanger FIN -----");            
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
    }
}
