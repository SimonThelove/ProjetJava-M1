/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package gestionProtocole;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
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
    private String mailConnecte = null;
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
System.err.println("#### Construct GPS...");
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
System.err.println("[GPS] requete");
System.out.println("# ENTREE = " + entreeSocket);
        switch(req[0])
        {
            //Requete de creation de compte
            case "CREA":
System.err.println("---- CASE CREA ----");
                reponse = ("MSG|" + serveur.creerCompte(req[2], req[4], req[6], req[8]));
                break;
            //Requete de connexion
            case "CONX":
System.err.println("---- CASE CONX ----");                
                try {
                    this.setMailConnecte(req[2]);
                    setReponse("MSG|" + serveur.seConnecter(this.getMailConnecte(), req[4]));
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            //Requete de modification d'information client
            case "MODI":
System.err.println("---- CASE MODI ----");
                try {
                    setReponse("MSG|" + serveur.modifierInformations(req,req[2]));
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            //Requete de consultation d'un profil
            case "CONS":
System.err.println("---- CASE CONS ----");
                try {
                    temp = serveur.consulter(req[2], req[3], mailConnecte);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, e);
                }
                String resultat = String.join("|",temp);
                setReponse("PROF|" + resultat);
                break;
            //Requete de recherche de personne
            case "RECH":
System.err.println("---- CASE RECH ----");
                temp = (serveur.rechercher(req));
                //Si dans la recherche, on ne trouve qu'une personne, alors on affiche le profil de la personne
                if (temp.get(0).compareTo("1") == 0)
                {
                    requete("CONS|" + temp.get(1) + "|" + temp.get(2) + "|0|");
                }
                //Sinon on renvoit une liste de personne
                else {
                    //Transformation de l'arrayList en String
                    String result = String.join("|",temp);
                    //Concatenation de la requete
                    setReponse("LIST|" + result);
                }
                break;
            case "MSSG":
System.err.println("---- CASE MSSG ----");
                switch(req[1]) {
                    case "ENVOI" :
                        reponse = ("MSG|" + serveur.envoiMessage(req[3], req[5], req[7]));
                        break;
                    case "RECUP" :
                        reponse = ("MSSG|" + serveur.recupererMessage(req[3]));
                        break;
                    case "LECT" :
                        reponse = ("MSG|" + serveur.marquerCommeLu(req[3]));
                        break;
                    default :
                        reponse = ("MSG|Erreur requête Messagerie serveur");
                        break;
                }
                break; 
            case "P2PH":
System.err.println("---- CASE P2PH ----");
                Enumeration keys = clients_co.keys();
                String connectes = "P2PH";
                while (keys.hasMoreElements()){
                   Integer key = Integer.parseInt((String) keys.nextElement());
                   connectes += "|" + key + "|" + clients_co.get(key);
                }
                setReponse(connectes);
                break;
            //Requete de deconnexion
            case "DECO":
System.err.println("---- CASE DECO ----");
                this.setMailConnecte(null);
                setReponse("DECO|" + serveur.seDeconnecter());
                break;
            //Fermeture d'un socket de connexion anonyme
            case "LIKE":
System.err.println("---- CASE LIKE ----");
System.out.println(req[1]);
                //Si on trouve le mot TYPE, alors se sera un ajout ou suppression d'un like
                if(req[1].compareTo("TYPE") == 0)
                {
                    System.out.println("test1"
                            + "");
                    reponse = ("MSG|" + serveur.like(req[2], req[4],req[6]));
                }
                //Sinon si on trouve le mot MAIL, alors se sera un affichage d'une recherche (recuperation des informations des likes)
                else if(req[1].compareTo("CIBLE") == 0)
                {
                    System.out.println("test2");
                    reponse = (serveur.nbLike(req[2], req[4]));
                }
                break;
            case "QUIT":
System.err.println("---- CASE QUIT ----");
                setReponse("QUIT|");
                break;
        }
System.out.println("[SRV] requete FIN -----");
        return reponse;
    }	
}