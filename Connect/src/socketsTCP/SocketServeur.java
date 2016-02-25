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
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Serveur;

/**
 *
 * @author Yohann
 */

public class SocketServeur {
  /** Port par defaut */
  public final static int portDefault = 12314;
  public int port;
  // Variables serveur
  private Serveur         serveur;
  private GestionProtocoleServeur gp;

  // Variable socket
  private ServerSocket    leServeur;
  
  public void socket () {

        serveur = new Serveur();
        gp = new GestionProtocoleServeur(serveur);
        leServeur = null;

        try {
          leServeur = new ServerSocket(portDefault);
        }
        catch (IOException ex)
        {
          // fin de connexion
          System.err.println("Impossible de crer un socket serveur sur ce port : "+ex);

          try {
            // on demande un port anonyme 
            leServeur = new ServerSocket(0);
            port = leServeur.getLocalPort();
            System.out.println("Port serveur : " + port);
          }
          catch (IOException ex2)
          {
            // fin de connexion
            System.err.println("Impossible de creer un socket serveur : "+ex2);
          }
        }

          while (true) {
            System.out.println("En attente de connexion sur le port : " + port);

            try {                                
                new Conversation(leServeur.accept(), gp).start();
                System.out.println("Conversation start : OK");
                
            } catch (IOException ex) {
                Logger.getLogger(SocketServeur.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
    }
}