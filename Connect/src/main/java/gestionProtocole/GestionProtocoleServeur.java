package gestionProtocole;
import java.io.*;
import java.net.*;
import serveur.Serveur;
 
import java.io.BufferedReader;
 
public class GestionProtocoleServeur {
 
    private Serveur serveur;
     
    public GestionProtocoleServeur(Serveur serveur) {
        super();
        this.serveur = serveur;
    }

    //TEST PUSH DEPUIS ECLIPSE
    
    public String requete(String entreeSocket){
        String[] req = entreeSocket.split("|");
        switch(req[0]){
        case "CREA":
            try {
                serveur.creerCompte(req[1], Double.parseDouble(req[2]));
                return "MSG|Votre compte a bien ete cree, vous pouvez maintenant vous connecter.";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG|Erreur lors de la creation de votre compte.";
            }
        case "CONX":
        	try {
                serveur.seConnecter(req[1], Double.parseDouble(req[2]));
                return "MSG|Vous etes bien connecte, que souhaitez-vous faire ?";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG|Erreur dans la combinaison mail / mot de passe, veuillez reessayer.";
            }
             
        case "MODI":
            try {
                serveur.modifierInformations(req[1], Double.parseDouble(req[2]));
                return "MSG|Vos informations ont bien ete mises � jour.";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG|Erreur lors de la mise � jour de vos informations.";
            }
        case "CONS":
            try {
                serveur.rechercherChamps(req[1], Double.parseDouble(req[2]));
                return "--------------------------------";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG|Erreur lors de la recherche.";
            }
            case "RECH":
            	if(req[1]=="MOTSCLES")
            	{
            		 try {
                         serveur.rechercherMotsCles(req[1], Double.parseDouble(req[2]));
                         return "-------------------------------";
                     } catch (NumberFormatException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                         return "MSG|Erreur lors de votre recherche.";
                     }
            	}
            	else if(req[1]=="NOM")
            	{
            		 try {
                         serveur.rechercherChamps(req[1], Double.parseDouble(req[2]));
                         return "-------------------------------";
                     } catch (NumberFormatException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
                         return "MSG|Erreur lors de votre recherche.";
                     }
            	}
            case "DECO":
            try {
                serveur.seDeconnecter(req[1], Double.parseDouble(req[2]));
                return "MSG|Vous etes bien deconnecte.";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG|Erreur lors de la deconnexion";
            }
        default :
            return "MSG|Erreur dans votre choix";
        }
    }

}