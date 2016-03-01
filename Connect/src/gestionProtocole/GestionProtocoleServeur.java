/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package gestionProtocole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import serveur.Serveur;

/**
 *
 * @author Yohann
 */
 
public class GestionProtocoleServeur {
    private final Serveur serveur;
    private final Hashtable clients_co;
    
    private String reponse;
    private String mailConnecte;
    private String type;
    private ArrayList<String> temp;

    public Hashtable getClients_co() {
        return clients_co;
    }
    
    public GestionProtocoleServeur(Serveur serveur, Hashtable clients) {
        super();
        this.temp = new ArrayList<>();
        this.serveur = serveur;
        this.clients_co = clients;
    }

    public Serveur getServeur() {
        return serveur;
    }

    public String getMailConnecte() {
            return mailConnecte;
    }

    public void setMailConnecte(String mailConnecte) {
            this.mailConnecte = mailConnecte;
    }

    public String getType() {
            return type;
    }

    public void setType(String type) {
            this.type = type;
    }

    public String getReponse() {
            return reponse;
    }

    public void setReponse(String reponse) {
            this.reponse = reponse;
    }
	
    public String requete(String entreeSocket) {
        String[] req = entreeSocket.split("[|]");

        switch(req[0])
        {
            //Requete de creation de compte
            case "CREA":
                reponse = ("MSG|" + serveur.creerCompte(req[2], req[4], req[6], req[8]));
                break;
            //Requete de connexion
            case "CONX":
                try {
                    this.setMailConnecte(req[2]);
                    setReponse("MSG|" + serveur.seConnecter(this.getMailConnecte(), req[4]));
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            //Requete de modification d'information client
            case "MODI": // AJOUTER LE MAIL DU CLIENT CONNECTE (condition WHERE du SQL UPDATE)
                try {
System.out.println("GPS MODI :" + req[(req.length - 1)]);
                    setReponse("MSG|" + serveur.modifierInformations(req,req[2]));
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            //Requete de consultation d'un profil
            case "CONS":
                try {
System.out.println("GPS CONS fonction 1 - req :" + req[2]);
                    temp = serveur.consulter(req[2], req[3]);
System.out.println("GPS CONS fonction 2 - temp :" + temp);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                String resultat = String.join("|",temp);
                setReponse("PROF|" + resultat);
                break;
            //Requete de recherche de personne
            case "RECH":
                temp = (serveur.rechercher(req));
System.out.println("Niveau GPS temp - " + temp);
                //Si dans la recherche, on ne trouve qu'une personne, alors on affiche le profil de la personne
                if (temp.get(0).compareTo("1") == 0)
                {
System.out.println("Niveau GPS Rech :"+ temp.get(1) + "|" + temp.get(2));
                    requete("CONS|" + temp.get(1) + "|" + temp.get(2) + "|0|");
                }
                //Sinon on renvoit une liste de personne
                else {
System.out.println("Niveau GPS LIST||");
                    //Transformation de l'arrayList en String
                    String result = String.join("|",temp);
                    //Concatenation de la requete
                    setReponse("LIST|" + result);
                }
                break;
            case "MSSG":
                if (req[1].compareTo("ENVOI") == 0)
                {
System.out.println("Niveau GPS MSSG : " + req[3] + " " + req[5]  +" "+ req[7]);
                reponse = ("MSG|" + serveur.envoiMessage(req[3], req[5], req[7]));
                }
                else if (req[1].compareTo("RECUP") == 0)
                {
                    reponse = ("MSSG|" + serveur.recupererMessage(req[3]));
                    System.out.println(reponse);
                }
                break; 
            //Requete de deconnexion
            case "DECO":
                setReponse("MSG|" + serveur.seDeconnecter());
                break;
        }
    
System.out.println("Niveau GPS resultat2 - " + reponse);
        return reponse;
    }	
}