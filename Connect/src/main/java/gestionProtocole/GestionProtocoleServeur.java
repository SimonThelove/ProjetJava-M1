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
 
    public String requete(String entreeSocket){
        String[] req = entreeSocket.split(" ");
        switch(req[0]){
        case "CREA":
            try {
                serveur.creerCompte(req[1], Double.parseDouble(req[2]));
                return "MSG|Votre compte a bien été créé, vous pouvez maintenant vous connecter.";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
        case "CONX":
                return "POS " + serveur.getSolde(req[1]) + serveur.getDerniereOperation(req[1]);
             
        case "MODI":
            try {
                serveur.ajouter(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
        case "CONS":
            try {
                serveur.retirer(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
            case "RECH":
            try {
                serveur.retirer(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
            case "DECO":
            try {
                serveur.retirer(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "MSG| Erreur format";
            }
        default :
            return "MSG|Erreur dans votre choix";
        }
    }

}