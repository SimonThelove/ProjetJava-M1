/*
 *  Projet JAVA M1 STRI - Promotion 2017
 *  Version 2.0 - Interface graphique JAVA FX
 *  Application cliente du projet Connect!
 *  Simon LAMOURE - Yohann LE GOVIC - Josselyn LOULIER
 */
package gestionProtocole;

import socketsTCP.SocketClient;
import client.Client;

/**
 *
 * @author Simon
 */
public class GestionProtocoleClient {
    
    private Client client;
    private String message;//Variable ou est stockee la requete
    private SocketClient soc = new SocketClient();
    private int nbPersonne;//Nombre de profil que renvoit les requetes de recherche
    private int choixProfil;//Le profil que choisira l'utilisateur s'il y a plusieurs profil
    private String mailRechercher[];//Les mails seront stockees pour que l'utilisateur puisse choisir
    private String req[];//Requete de retour du serveur 
        
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    //Methode de concatenation de la requete creerCompte
    public void requeteCrea(String nom, String prenom, String mail, String motDePasse){
	//Creation de la requete
	message = "CREA|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }
	
    //Methode de concatenation de la requete connexion
    public void requeteConx(String mail, String motDePasse){
	//Creation de la requete
	message = "CONX|MAIL|" + mail + "|MOTDEPASSE|" + motDePasse;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }

    //Methode de concatenation de la requete rechercherMotsCles
    public void requeteRechMotsCles(String motCles){
	//Creation de la requete
	message = "RECH|MOTSCLES|" + motCles;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }

    //Methode de concatenation de la requete RechercherAvancee
    public void requeteRechNom(String nom, String prenom, String mail, String diplome, String annee, String competences){
	//Creation de la requete
	message = "RECH|NOM|" + nom + "|PRENOM|" + prenom + "|MAIL|" + mail + "|DIPLOME|" + diplome + "|ANNEE|" + annee + "|COMPETENCES|" + competences;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }

    //Methode de concatenation de la requete consultation
    public void requeteCons(){
	//Creation de la requete
	message = "CONS|" + mailRechercher[choixProfil];
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }
	
    //Methode de concatenation de la requete modification
    public void requeteModi(String modification){
	//Creation de la requete
	message = "MODI|MAIL|" + client.getMail() + "|" + modification;
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
	}

    //Methode de concatenation de la requete deconnexion
    public void requeteDeco(){
	//Creation de la requete
	message = "DECO";
	//Envoit du message a SocketClient
	message = soc.socket(message);
	//Appelle a la methode pour creer un affichage au client
	message = decoupage(message);
    }
	
    //Methode pour creer la reponse a afficher d'une requete
    public String decoupage(String reponse){
	nbPersonne = 1;
        req = reponse.split("[|]");
        switch(req[0]){
        //Recupere le message a afficher au client
        case "MSG":
            try {
                message = req[1];
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        //Affiche la liste simplifier des profils
        case "LIST":
            try {
            	//message = "Resultats :\n\n";
            	for(int i = 1; i <= req.length; i++){
            		System.out.println("message :" + req[i]);
            		message += Integer.toString(i) + "/ " + req[i+4] + " " + req[i+6] + " - " + req[i+1] ;
            		//mailRechercher[i] = req[i+1];
            	}
            	message += "\nQuel profil souhaitez-vous consulter ? :";
            	//choixProfil = sc.nextInt();
            	}
              catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            break;
        //Affiche un profil
        case "PROF":
            try {
            	int i;
            	message = "\n\n----------------------------------------\n";
            	for(i = 1; i < (reponse.length()) ; i = i+2)
            	{
            		switch (req[i])
            		{
            			case "NOM" :
            				message += req[i+1] + " - ";
            				break;
            		    case "PRENOM" :
            		    	message += req[i+1] + " - ";
                		    break;
                		case "MAIL" :
                			message += req[i+1];
                    		break;
                		case "DIPLOME" :
                			message += "\nDiplome obtenu : " + req[i+1] + " (en";
                			break;
                		case "ANNEE" :
                			message += " (en" + req[i+1] + ")";
                			break;
                		case "COMPETENCES" :
                			message += "\nCompetences ; " + req[i+1];
                            break;        
            		  default:
            		    /*Action*/;             
            		}
	            	message = req[2] + " - " + req[4] + " - " + req[6] + "\nDiplomer(e) en " + req[8] + " (" + req[10] + ")\nCompetence(s) : " + req[12];
            	}
            	message += "\n----------------------------------------\n";
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                message = "Erreur format";
            }
            break;
        default :
            message = "Erreur dans votre choix";
        }
        return message;
    }
}