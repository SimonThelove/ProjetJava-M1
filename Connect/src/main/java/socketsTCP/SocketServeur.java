package socketsTCP;
import java.net.*;
import java.io.*;
import gestionProtocole.GestionProtocoleServeur;
import serveur.Serveur;

public class SocketServeur extends Object {
 
  /** Port par défaut */
  public final static int portEcho = 12314;
  
  // Variable serveur
  private Serveur serveur;
     
  /**
  * @param args the command line arguments
  */
  public void socket () {
	  
    ServerSocket    leServeur = null;
    Socket          connexionCourante;
    BufferedReader  entreeSocket;
    PrintStream     sortieSocket;
    String 			reponse;
    
    GestionProtocoleServeur gp = new GestionProtocoleServeur(serveur);
     
    try {
      leServeur = new ServerSocket(portEcho);
    }
    catch (IOException ex)
    {
      // fin de connexion
      System.err.println("Impossible de créer un socket serveur sur ce port : "+ex);
       
      try {
        // on demande un port anonyme 
        leServeur = new ServerSocket(0);
      }
      catch (IOException ex2)
      {
        // fin de connexion
        System.err.println("Impossible de créer un socket serveur : "+ex);
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
          while (b != -1) {
        	  
              sortieSocket = new PrintStream(connexionCourante.getOutputStream());
              entreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
              
              // R�ception client
              String retour = entreeSocket.readLine();
              // Traitement requ�te
              reponse = gp.requete(retour);
              System.out.println(reponse);
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