package gestionProtocole;
import java.io.*;
import java.net.*;
import serveur.Serveur;
 
import java.io.BufferedReader;
 
public class GestionProtocoleServeur {
 
    private Serveur serveur;
    private String reponse;
     
    public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
    }
    
    public String requete(String entreeSocket){
        String[] req = entreeSocket.split("|");
        switch(req[0]){
        case "CREA":
                serveur.creerCompte(req[1], Double.parseDouble(req[2]));
          
        case "CONX":
                serveur.seConnecter(req[1], Double.parseDouble(req[2]));
                
        case "MODI":
                serveur.modifierInformations(req[1], Double.parseDouble(req[2]));
               
        case "CONS":
                serveur.rechercherChamps(req[1], Double.parseDouble(req[2]));
             
        case "RECH":
        	serveur.rechercher(req[1], Double.parseDouble(req[2]));

        case "DECO":
        	serveur.seDeconnecter(req[1], Double.parseDouble(req[2]));
               
        default :
            return "MSG|Erreur dans votre choix";
        }
    }

}