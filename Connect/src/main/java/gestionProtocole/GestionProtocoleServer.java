package gestionProtocole;
import java.io.*;
import java.net.*;
 
import java.io.BufferedReader;
import java.util.Date;
 
public class GestionProtocoleServer {
/* 
    private String banque;
     
    public GP(BanqueSimple banque) {
        super();
        this.banque = banque;
    }
 
    public String requete(String entreeSocket){
        String[] req = entreeSocket.split(" ");
        switch(req[0]){
        case "CREATION":
            try {
                banque.creerCompte(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "ERREUR FORMAT";
            }
        case "POSITION":
                return "POS " + banque.getSolde(req[1]) + banque.getDerniereOperation(req[1]);
             
        case "AJOUT":
            try {
                banque.ajouter(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "ERREUR FORMAT";
            }
        case "RETRAIT":
            try {
                banque.retirer(req[1], Double.parseDouble(req[2]));
                return "OK";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "ERREUR FORMAT";
            }
        default :
            return "OK";
        }
    }
*/
}