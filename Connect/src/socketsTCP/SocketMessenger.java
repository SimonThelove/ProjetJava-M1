package socketsTCP;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */

/**
 *
 * @author Simon
 */
public class SocketMessenger {
    
    // La classe SocketMessenger permet de gérer à elle seule les échnages P2P
    // Elle gère à la fois la partie réception (mode serveur) et émission (mode client)
    // Le fait de tout gérer depuis la même classe permet de basculer facilement d'une méthode à l'autre sans intermédiaire
    
    // VARIABLES COMMUNES AUX FONCTIONS DE MESSENGER
    // Ces variables sont utilisées par les deux aspects du messenger
    // Comme les deux aspects ne sont jamais simultanés sur un client donné, elles peuvent être partagées
    private BufferedReader entreeSocket = null;
    private PrintStream sortieSocket = null;
    private Socket socket = null;
    private String retour = null;
    
    // VARIABLES ASSOCIEES A L'EMISSION DE MESSAGES P2P
    // Ces variables ne sont utilisées que par la partie émission (mode client)
    
    // VARIABLES ASSOCIEES A LA RECEPTION DE MESSAGES P2P
    // Ces variables ne sont utilisées que par la partie réception (mode serveur)
    public final static int portReceptionDefaut = 12345;
    public int portReception;
    private ServerSocket socketReception = null;
    
    // Constructeur du socket P2P
    // On construit en même temps le socket d'émission et de réception
    public SocketMessenger() {
        
        try // On commence par créer le socket de réception sur le port par défaut : 12345
        {
System.err.println("#### Constructeur Socket reception P2P");            
            socketReception = new ServerSocket(portReceptionDefaut);
            portReception = portReceptionDefaut;
System.out.println("## PORT ECOUTE P2P = " + portReception);
System.out.println("## Creation OK... " + socketReception);
        }
        catch (IOException ex) // Creation impossible sur le port par défaut
        {
            try // On demande un port anonyme
            {
                socketReception = new ServerSocket();
                portReception = socketReception.getLocalPort();
System.err.println("## PORT ECOUTE P2P = " + portReception + " <!> DIFFERENT DU PORT PAR DEFAUT <!>");
            }
            catch (IOException ex2)
            {
                // fin de connexion
                System.err.println("SocketMessenger - Creation impossible " + ex2);
            }
        }
        
        try {
System.err.println("#### Constructeur Socket emission P2P");            
            socket = new Socket("localhost", 12345);
System.out.println("## Creation Buffer/Stream...");

            this.sortieSocket = new PrintStream(socket.getOutputStream());
            this.entreeSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
System.out.println("## Creation OK... " + socket);                        
        } catch (IOException ex) {
            Logger.getLogger(SocketMessenger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void envoyer(String message) {
System.err.println("@SocketMessenger envoyer");
System.out.println("## ENTREE = ");

        sortieSocket.println(message);          // Envoi des données au client distant
            
System.out.println("## SORTIE = ");
System.err.println("@envoyer FIN -----");
    }
    
    public String recevoir() {
        
System.err.println("@SocketMessenger recevoir");
System.out.println("## Attente de connexion sur le port : " + portReception);
        
        do {
            try {

                retour = entreeSocket.readLine();   // Réception des données
            }
            catch (IOException ex) {
                Logger.getLogger(SocketMessenger.class.getName()).log(Level.SEVERE, null, ex);

            }
        } while(retour == null);
System.err.println("@recevoir FIN -----");   
        return retour;
    }
}
