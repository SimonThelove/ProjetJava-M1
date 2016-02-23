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
import serveur.Serveur;

/**
 *
 * @author Yohann
 */

public class SocketServeur extends Object {
  /** Port par defaut */
  public final static int portEcho = 12314;
  // Variables serveur
  private Serveur serveur;
  private String retour = null;
  
  public void socket () {
    ServerSocket    leServeur = null;
    Socket          connexionCourante;
    BufferedReader  entreeSocket;
    PrintStream     sortieSocket;
    String 			reponse;
    
    serveur = new Serveur();
    GestionProtocoleServeur gp = new GestionProtocoleServeur(serveur);
     
    try {
      leServeur = new ServerSocket(portEcho);
    }
    catch (IOException ex)
    {
      // fin de connexion
      System.err.println("Impossible de crer un socket serveur sur ce port : "+ex);
       
      try {
        // on demande un port anonyme 
        leServeur = new ServerSocket(0);
      }
      catch (IOException ex2)
      {
        // fin de connexion
        System.err.println("Impossible de creer un socket serveur : "+ex);
      }
    }
      
    if
      (leServeur != null)
    {
     try {
      System.err.println("En attente de connexion sur le port : "+leServeur.getLocalPort());
      while (true) {
        connexionCourante = leServeur.accept();
        System.err.println("Nouvelle connexion : "+connexionCourante);
         
        try {
          int b = 0;
          sortieSocket = new PrintStream(connexionCourante.getOutputStream());
          entreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
          
          // Reception client
          retour = entreeSocket.readLine();
          // Traitement requete
          reponse = gp.requete(retour);
          // Envoi au client
          sortieSocket.println(reponse);
          
          while (b != -1) {
        	  connexionCourante = leServeur.accept();
              sortieSocket = new PrintStream(connexionCourante.getOutputStream());
              entreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
              
              // Reception client
              retour = entreeSocket.readLine();
              // Traitement requete
              reponse = gp.requete(retour);
              // Envoi au client
              sortieSocket.println(reponse);  
          }
          System.err.println("Fin de connexion");
        }
        catch (IOException ex)
        {
          // fin de connexion
          System.err.println("Fin de connexion : "+ex);
        }
        connexionCourante.close();
      }
    }
    catch (Exception ex)
    {
      // erreur de connexion
      System.err.println("Une erreur est survenue : "+ex);
      ex.printStackTrace();
    }
   } 
  }
}
